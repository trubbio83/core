package it.smartcommunitylabdhub.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import it.smartcommunitylabdhub.core.annotations.ApiVersion;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This class is responsable to register and get the correct controller based on
 * the @ApiVersion number
 * annoted on the ProjectController. For now is checking for @ApiVersion with
 * the passed 'api-version'
 * header parameter.
 * 
 * In the future the path should be /api/{version}/..... and the controller for
 * instance a controller named
 * ProjectControllerV2 will be invoked base on the {version} parameter passed.
 * In this case it could be 'v2'
 */
public class VersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final Map<String, Map<String, List<HandlerMethod>>> handlerMethodsMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        ApplicationContext context = obtainApplicationContext();
        context.getBeansWithAnnotation(RestController.class).values().forEach(this::registerController);
    }

    /**
     * Registers controller based on @ApiVersion notation
     * 
     * @param controller
     */
    private void registerController(Object controller) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(controller.getClass(), ApiVersion.class);
        if (apiVersion != null) {
            String path = getPath(controller.getClass());
            String version = apiVersion.value();
            List<HandlerMethod> handlerMethods = getHandlerMethods(controller);
            handlerMethodsMap.computeIfAbsent(path, k -> new ConcurrentHashMap<>())
                    .computeIfAbsent(version, k -> new CopyOnWriteArrayList<>())
                    .addAll(handlerMethods);
        }
    }

    private String getPath(Class<?> controllerClass) {
        RequestMapping annotation = AnnotationUtils.findAnnotation(controllerClass, RequestMapping.class);
        if (annotation != null) {
            return annotation.value()[0];
        }
        return "";
    }

    /**
     * Get the controller handler methods
     * 
     * @param controller
     * @return
     */
    private List<HandlerMethod> getHandlerMethods(Object controller) {
        List<HandlerMethod> handlerMethods = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> map = super.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            HandlerMethod handlerMethod = map.get(info);
            if (handlerMethod.getBeanType().equals(controller.getClass())) {
                handlerMethods.add(handlerMethod);
            }
        }
        return handlerMethods;
    }

    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {

        Map<String, String> pathInfo = extractApiInformation(lookupPath);
        String version = request.getHeader("api-version") != null ? request.getHeader("api-version")
                : pathInfo.getOrDefault("version", "v1");

        // The fact that handlerMethods it return a list is only in case to be able to
        // have multiple version of same @ApiVersion("v1")
        // This mean that for version v1 for instance we can have multiple handler for
        // the action /projects. Not sure we need it.
        List<HandlerMethod> handlerMethods = handlerMethodsMap
                .getOrDefault(pathInfo.getOrDefault("action", ""), new HashMap<>()).get(version);

        if (handlerMethods != null && !handlerMethods.isEmpty()) {

            return handlerMethods.get(0);
            // for (HandlerMethod handlerMethod : handlerMethods) {

            // // Retrieve application context
            // context.getBeansWithAnnotation(RestController.class).values();

            // RequestMappingInfo info = getRequestMappingInfo(handlerMethod);

            // // if (info != null) {
            // // Optional<PathPatternsRequestCondition> patternsCondition = Optional
            // // .of(info.getPathPatternsCondition());
            // // Set<PathPattern> patterns =
            // // patternsCondition.map(PathPatternsRequestCondition::getPatterns)
            // // .orElse(Collections.emptySet());

            // // for (PathPattern pattern : patterns) {
            // // Map<String, String> patternPathInfo =
            // // extractApiInformation(pattern.getPatternString());

            // // if (getPathMatcher().match(patternPathInfo.getOrDefault("action", ""),
            // // pathInfo.getOrDefault("action", ""))) {
            // // return handlerMethod;
            // // }
            // // }
            // // }
            // }
        }
        throw new HttpRequestMethodNotSupportedException(request.getMethod());
    }

    // private RequestMappingInfo getRequestMappingInfo(HandlerMethod handlerMethod)
    // {
    // Map<RequestMappingInfo, HandlerMethod> map = super.getHandlerMethods();
    // for (RequestMappingInfo info : map.keySet()) {
    // if (map.get(info).equals(handlerMethod)) {
    // return info;
    // }
    // }
    // return null;
    // }

    /**
     * Given a path try to get api version and controller action
     * 
     * @param path
     * @return
     */
    private Map<String, String> extractApiInformation(String path) {

        Map<String, String> matches = new HashMap<>();
        Pattern pattern = Pattern.compile("^/api/(\\w+)/(.*)$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            String version = matcher.group(1); // Extract the version from the first group
            String pathAfterVersion = "/" + matcher.group(2); // Extract the path after the version and add "/"

            matches.put("version", version);
            matches.put("action", pathAfterVersion);
        }
        return matches;

    }
}
