package it.smartcommunitylabdhub.core.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VersioningHandlerInterceptor implements HandlerInterceptor {

    private static final String API_VERSION_HEADER = "api-version";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String apiVersionHeader = request.getHeader(API_VERSION_HEADER);
        if (apiVersionHeader != null && !apiVersionHeader.isEmpty()) {
            request.setAttribute("apiVersion", apiVersionHeader);
        }

        return true;
    }
}
