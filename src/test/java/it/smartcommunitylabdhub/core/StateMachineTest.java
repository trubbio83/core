package it.smartcommunitylabdhub.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.smartcommunitylabdhub.core.components.fsm.*;
import it.smartcommunitylabdhub.core.components.fsm.StateMachine.Builder;

@SpringBootTest
public class StateMachineTest {

        // some method definition

        @Test
        public void fsm() {

                // Create the state machine

                State<String, String, Map<String, Object>> state1 = new State<>();
                State<String, String, Map<String, Object>> state2 = new State<>();
                State<String, String, Map<String, Object>> state3 = new State<>();
                State<String, String, Map<String, Object>> state4 = new State<>();
                State<String, String, Map<String, Object>> errorState = new State<>(); // Error state

                // Create the initial state and context
                String initialState = "State1";
                Map<String, Object> initialContext = new HashMap<>();

                // Create the state machine using the builder
                Builder<String, String, Map<String, Object>> builder = StateMachine
                                .<String, String, Map<String, Object>>builder(initialState, initialContext)
                                .withState("State1", state1)
                                .withState("State2", state2)
                                .withState("State3", state3)
                                .withState("State4", state4)
                                .withErrorState("ErrorState", errorState)
                                .withStateChangeListener((newState, context) -> System.out.println(
                                                "State Change Listener: " + newState + ", context: " + context));

                // Define transactions for state 1
                state1.addTransaction(
                                new Transaction<>("Event1", "State2", (input, context) -> input.isPresent(), false));

                // Define transactions for state 2
                state2.addTransaction(
                                new Transaction<>("Event2", "State3", (input, context) -> input.isPresent(), false));

                // Define transactions for state 3
                state3.addTransaction(
                                new Transaction<>("Event3", "State4", (input, context) -> input.isPresent(), false));

                // Define transactions for state 4
                state4.addTransaction(
                                new Transaction<>("Event4", "State1", (input, context) -> input.isPresent(), false));

                // Set internal logic for state 1
                state1.setInternalLogic((input, context, stateMachine) -> {
                        System.out.println("Executing internal logic of State1 with input: " + input + ", context: "
                                        + context);
                        context.put("value", 1);
                        return Optional.of("State1 Result");
                });
                state1.setExitAction(context -> {
                        System.out.println("exit action for state 1");
                });

                // Set internal logic for state 2
                state2.setInternalLogic((input, context, stateMachine) -> {
                        System.out.println("Executing internal logic of State2 with input: " + input + ", context: "
                                        + context);
                        context.put("value", 2);
                        return Optional.of("State2 Result");
                });

                // Set internal logic for state 3
                state3.setInternalLogic((input, context, stateMachine) -> {
                        System.out.println("Executing internal logic of State3 with input: " + input + ", context: "
                                        + context);
                        context.put("value", 3);
                        return Optional.of("State3 Result");
                });

                // Set internal logic for state 4
                state4.setInternalLogic((input, context, stateMachine) -> {
                        System.out.println("Executing internal logic of State4 with input: " + input + ", context: "
                                        + context);
                        context.put("value", 4);
                        return Optional.of("State4 Result");
                });

                // Set internal logic for the error state
                errorState.setInternalLogic((input, context, stateMachine) -> {
                        System.out.println("Error state reached. Input: " + input + ", context: " + context);
                        // Handle error logic here
                        return Optional.empty(); // No result for error state
                });

                // Add event listeners
                builder.withEventListener("Event1", (input, context) -> System.out
                                .println("Event1 Listener: " + input + ", context: " + context));
                builder.withEventListener("Event2", (input, context) -> System.out
                                .println("Event2 Listener: " + input + ", context: " + context));
                builder.withEventListener("Event3", (input, context) -> System.out
                                .println("Event3 Listener: " + input + ", context: " + context));
                builder.withEventListener("Event4", (input, context) -> System.out
                                .println("Event4 Listener: " + input + ", context: " + context));

                // Build the state machine
                StateMachine<String, String, Map<String, Object>> stateMachine = builder.build();

                // Trigger events to test the state machine
                stateMachine.processEvent("Event1", Optional.of("Input1"));
                stateMachine.processEvent("Event2", Optional.of("Input2"));

                // try {
                // String ser = stateMachine.serialize();

                // System.out.println(ser);

                // StateMachine<String, String, Map<String, Object>> stateMachine2 =
                // StateMachine.deserialize(ser);
                // System.out.println("Deserialize");
                // } catch (Exception e) {
                // System.out.println(e.getMessage());
                // }

        }
}
