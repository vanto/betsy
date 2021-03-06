package configuration.processes

import betsy.data.Process
import betsy.data.TestCase
import betsy.data.TestStep
import betsy.data.WsdlOperation
import betsy.data.assertions.NotDeployableAssertion
import betsy.data.assertions.SoapFaultTestAssertion

class StructuredActivityProcesses {

    ProcessBuilder builder = new ProcessBuilder()

    public final Process SEQUENCE = builder.buildStructuredActivityProcess(
            "Sequence", "A receive-reply pair enclosed in a sequence.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", output: "5", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW = builder.buildStructuredActivityProcess(
            "Flow", "A receive-reply pair with an intermediate flow that contains two assigns.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", output: "7", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_RECEIVE_CREATING_INSTANCES = builder.buildStructuredActivityProcess(
            "Flow-ReceiveCreatingInstances",  "A flow with a starting activity (receive with createInstance set to yes) and non-starting activities (assign) that run in parallel. Such a process definition must be rejected by an engine.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", assertions: [new NotDeployableAssertion()], operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_LINKS_RECEIVE_CREATING_INSTANCES = builder.buildStructuredActivityProcess(
            "Flow-Links-ReceiveCreatingInstances", "A flow with a starting activity (receive with createInstance set to yes) and a non-starting activity (assign), where a precedence relationship is defined using links.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", output: "6", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_LINKS = builder.buildStructuredActivityProcess(
            "Flow-Links",  "A receive-reply pair with an intermediate flow that contains two assigns which have a precedence relationship between each other using links.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "2", operation: WsdlOperation.SYNC)]),
            ]
    )

    public final Process FLOW_LINKS_TRANSITION_CONDITION = builder.buildStructuredActivityProcess(
            "Flow-Links-TransitionCondition",  "A receive-reply pair with an intermediate flow that contains three assigns, two of which point to the third using links. Both links have transitionConditions that do fire only if the input is greater than two.",
            [
                    new TestCase(testSteps: [new TestStep(input: "2", output: "4", operation: WsdlOperation.SYNC)]),
                    new TestCase(testSteps: [new TestStep(input: "3", output: "6", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_BOUNDARY_LINKS = builder.buildStructuredActivityProcess(
            "Flow-BoundaryLinks", "A receive-reply pair with an intermediate flow that contains an assign and a sequence with an assign, as well as a link pointing from the former to the later assign. That way the links crosses the boundary of a structured activity, the sequence.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "2", operation: WsdlOperation.SYNC)])
            ]
    )

    //Permutate all allowed combinations of calls here
    public final Process FLOW_GRAPH_EXAMPLE = builder.buildStructuredActivityProcess(
            "Flow-GraphExample", "An implementation of the flow graph process defined in Sec. 11.6.4.",
            [
                    new TestCase(testSteps: [
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                    ]),
                    new TestCase(testSteps: [
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                    ]),
                    new TestCase(testSteps: [
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                    ]),
                    new TestCase(testSteps: [
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC)
                    ])
            ]
    )

    public final Process FLOW_LINKS_JOIN_CONDITION = builder.buildStructuredActivityProcess(
            "Flow-Links-JoinCondition", "A receive-reply pair with an intermediate flow that contains three assigns, two of which point to the third using links. Both links have transitionConditions and their target a joinCondition defined upon them. A joinFailure should result, given not both of the links are activated.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", assertions: [new SoapFaultTestAssertion(faultString: "joinFailure")], operation: WsdlOperation.SYNC)]),
                    new TestCase(testSteps: [new TestStep(input: "3", output: "6", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_LINKS_SUPPRESS_JOIN_FAILURE = builder.buildStructuredActivityProcess(
            "Flow-Links-SuppressJoinFailure", "A receive-reply pair with an intermediate flow that contains three assigns, two of which point to the third using links. Both links have transitionConditions and their target a joinCondition defined upon them. The transitionConditions do never evaluate to true, resulting in a joinFailure on each invocation. However, this joinFailure is suppressed.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "3", operation: WsdlOperation.SYNC)]),
                    new TestCase(testSteps: [new TestStep(input: "3", output: "5", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FLOW_LINKS_JOIN_FAILURE = builder.buildStructuredActivityProcess(
            "Flow-Links-JoinFailure",  "A receive-reply pair with an intermediate flow that contains three assigns, two of which point to the third using links. Both links have transitionConditions and their target a joinCondition defined upon them. The transitionConditions do never evaluate to true, resulting in a joinFailure on each invocation.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", assertions: [new SoapFaultTestAssertion(faultString: "joinFailure")], operation: WsdlOperation.SYNC)]),
                    new TestCase(testSteps: [new TestStep(input: "3", assertions: [new SoapFaultTestAssertion(faultString: "joinFailure")], operation: WsdlOperation.SYNC)])
            ]
    )

    public final List<Process> STRUCTURED_ACTIVITIES_FLOW = [
            FLOW,
            FLOW_LINKS,
            FLOW_BOUNDARY_LINKS,
            FLOW_LINKS_JOIN_CONDITION,
            FLOW_LINKS_JOIN_FAILURE,
            FLOW_LINKS_SUPPRESS_JOIN_FAILURE,
            FLOW_LINKS_TRANSITION_CONDITION,
            FLOW_GRAPH_EXAMPLE,
            FLOW_RECEIVE_CREATING_INSTANCES,
            FLOW_LINKS_RECEIVE_CREATING_INSTANCES
    ].flatten() as List<Process>

    public final Process IF = builder.buildStructuredActivityProcess(
            "If", "A receive-reply pair with an intermediate if that checks whether the input is even.",
            [
                    new TestCase(name: "Not-If-Case", testSteps: [new TestStep(input: "1", output: "0", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "If-Case", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process IF_ELSE = builder.buildStructuredActivityProcess(
            "If-Else",  "A receive-reply pair with an intermediate if-else that checks whether the input is even.",
            [
                    new TestCase(name: "Else-Case", testSteps: [new TestStep(input: "1", output: "0", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "If-Case", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process IF_ELSE_IF = builder.buildStructuredActivityProcess(
            "If-ElseIf", "A receive-reply pair with an intermediate if-elseif that checks whether the input is even or divisible by three.",
            [
                    new TestCase(name: "Not-If-Or-ElseIf-Case", testSteps: [new TestStep(input: "1", output: "0", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "If-Case", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "ElseIf-Case", testSteps: [new TestStep(input: "3", output: "2", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process IF_ELSE_IF_ELSE = builder.buildStructuredActivityProcess(
            "If-ElseIf-Else", "A receive-reply pair with an intermediate if-elseif-else that checks whether the input is even or divisible by three.",
            [
                    new TestCase(name: "Else-Case", testSteps: [new TestStep(input: "1", output: "0", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "If-Case", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "ElseIf-Case", testSteps: [new TestStep(input: "3", output: "2", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process IF_INVALID_EXPRESSION_VALUE = builder.buildStructuredActivityProcess(
            "If-InvalidExpressionValue", "A receive-reply pair with an intermediate if that should throw an invalidExpressionValue fault because of an invalid condition.",
            [
                    new TestCase(name: "SelectionFailure", testSteps: [new TestStep(input: "1", assertions: [new SoapFaultTestAssertion(faultString: "invalidExpressionValue")], operation: WsdlOperation.SYNC)])
            ]
    )

    public final List<Process> STRUCTURED_ACTIVITIES_IF = [
            IF, IF_ELSE, IF_ELSE_IF, IF_ELSE_IF_ELSE, IF_INVALID_EXPRESSION_VALUE
    ]

    public final Process WHILE = builder.buildStructuredActivityProcess(
            "While", "A receive-reply pair with an intermediate while that loops for n times, where n is equal to the input.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", output: "5", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process REPEAT_UNTIL = builder.buildStructuredActivityProcess(
            "RepeatUntil", "A receive-reply pair with an intermediate while that loops for n+1 times, where n is equal to the input.",
            [
                    new TestCase(testSteps: [new TestStep(input: "2", output: "3", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process REPEAT_UNTIL_EQUALITY = builder.buildStructuredActivityProcess(
            "RepeatUntilEquality", "A receive-reply pair with an intermediate while that loops for n times, where n is equal to the input.",
            [
                    new TestCase(testSteps: [new TestStep(input: "2", output: "2", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FOR_EACH = builder.buildStructuredActivityProcess(
            "ForEach",  "A receive-reply pair with an intermediate forEach that loops for n times, where n is equal to the input. Each iteration the current loop number is added to the final result.",
            [
                    new TestCase(name: "0-equals-0", testSteps: [new TestStep(input: "0", output: "0", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "0plus1-equals-0", testSteps: [new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "0plus1plus2-equals-3", testSteps: [new TestStep(input: "2", output: "3", operation: WsdlOperation.SYNC)]),
            ]
    )

    public final Process FOR_EACH_NEGATIVE_STOP_COUNTER = builder.buildStructuredActivityProcess(
            "ForEach-NegativeStopCounter", "A receive-reply pair with an intermediate forEach that should always fail with an invalidExpressionValue fault as finalCounterValue is negative.",
            [
                    new TestCase(name: "NegativeStopCounter", testSteps: [new TestStep(input: "1", assertions: [new SoapFaultTestAssertion(faultString: "invalidExpressionValue")], operation: WsdlOperation.SYNC)])
                    // NaN or large numbers cannot be sent due to type constraint to xsd:int
            ]
    )

    public final Process FOR_EACH_DUPLICATE_COUNTER_VARIABLE = builder.buildStructuredActivityProcess(
            "ForEach-DuplicateCounterVariable", "A receive-reply pair with an intermediate forEach that contains a variable of the same name as the counter variable in its child scope. The process definition must be rejected.",
            [
                    new TestCase(name: "SA00076", testSteps: [new TestStep(input: "2", assertions: [new NotDeployableAssertion()], operation: WsdlOperation.SYNC)]),
            ]
    )

    public final Process FOR_EACH_NEGATIVE_START_COUNTER = builder.buildStructuredActivityProcess(
            "ForEach-NegativeStartCounter",  "A receive-reply pair with an intermediate forEach that should always fail with an invalidExpressionValue fault as startCounterValue is negative.",
            [
                    new TestCase(name: "Iterate-Twice", testSteps: [new TestStep(input: "2", assertions: [new SoapFaultTestAssertion(faultString: "invalidExpressionValue")], operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FOR_EACH_COMPLETION_CONDITION_NEGATIVE_BRANCHES = builder.buildStructuredActivityProcess(
            "ForEach-CompletionCondition-NegativeBranches",  "A receive-reply pair with an intermediate forEach that should always fail with an invalidExpressionValue fault as branches is initialized with a negative value.",
            [
                    new TestCase(name: "Iterate-Twice", testSteps: [new TestStep(input: "2", assertions: [new SoapFaultTestAssertion(faultString: "invalidExpressionValue")], operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FOR_EACH_TOO_LARGE_START_COUNTER = builder.buildStructuredActivityProcess(
            "ForEach-TooLargeStartCounter", "A receive-reply pair with an intermediate forEach that should always fail with an invalidExpressionValue fault as startCounterValue is initialized with a value that exceeds xs:unsignedInt.",
            [
                    new TestCase(name: "Iterate-Twice", testSteps: [new TestStep(input: "2", assertions: [new SoapFaultTestAssertion(faultString: "invalidExpressionValue")], operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FOR_EACH_PARALLEL = builder.buildStructuredActivityProcess(
            "ForEach-Parallel", "A receive-reply pair with an intermediate forEach that executes its children in parallel.",
            [
                    new TestCase(name: "0plus1plus2-equals-3", testSteps: [new TestStep(input: "2", output: "3", operation: WsdlOperation.SYNC)]),
            ]
    )

    public final Process FOR_EACH_COMPLETION_CONDITION = builder.buildStructuredActivityProcess(
            "ForEach-CompletionCondition",   "A receive-reply pair with an intermediate forEach that should terminate given two of its children have terminated. N+1 children are scheduled for execution, where n is equal to the input. If N+1 is less than two, an invalidBranchConditionFault should be thrown.",
            [
                    new TestCase(name: "Skipping the third iteration", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "Cannot meet completion condition", testSteps: [new TestStep(input: "0", operation: WsdlOperation.SYNC, assertions: [new SoapFaultTestAssertion(faultString: "invalidBranchCondition")])])
            ]
    )

    public final Process FOR_EACH_COMPLETION_CONDITION_PARALLEL = builder.buildStructuredActivityProcess(
            "ForEach-CompletionCondition-Parallel", "A receive-reply pair with an intermediate forEach that should terminate given two of its children have terminated. N+1 children are scheduled for execution in parallel, where n is equal to the input. If N+1 is less than two, an invalidBranchConditionFault should be thrown.",
            [
                    new TestCase(name: "Skipping the third iteration", testSteps: [new TestStep(input: "2", output: "1", operation: WsdlOperation.SYNC)]),
                    new TestCase(name: "Cannot meet completion condition", testSteps: [new TestStep(input: "0", operation: WsdlOperation.SYNC, assertions: [new SoapFaultTestAssertion(faultString: "invalidBranchCondition")])])
            ]
    )

    public final Process FOR_EACH_COMPLETION_CONDITION_SUCCESSFUL_BRANCHES_ONLY = builder.buildStructuredActivityProcess(
            "ForEach-CompletionCondition-SuccessfulBranchesOnly", "A receive-reply pair with an intermediate forEach that should terminate given two of its children have terminated successfully. Each child throws a fault, given the current counter value is even. N children are scheduled for execution, where n is equal to the input.",
            [
                    new TestCase(testSteps: [new TestStep(input: "5", output: "6", operation: WsdlOperation.SYNC)]),
                    new TestCase(testSteps: [new TestStep(input: "10", output: "6", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process FOR_EACH_COMPLETION_CONDITION_FAILURE = builder.buildStructuredActivityProcess(
            "ForEach-CompletionConditionFailure", "A receive-reply pair with an intermediate forEach that should terminate given two of its children have terminated. N+1 children are scheduled for execution in parallel, where n is equal to the input. If N+1 is less than two, an invalidBranchConditionFault should be thrown. This is a seperate test case that tests only for the failure.",
            [
                    new TestCase(name: "Expect completionConditionFailure", testSteps: [new TestStep(input: "1", operation: WsdlOperation.SYNC, assertions: [new SoapFaultTestAssertion(faultString: "completionConditionFailure")])])
            ]
    )

    public final List<Process> STRUCTURED_ACTIVITIES_FOR_EACH = [
            FOR_EACH,
            FOR_EACH_NEGATIVE_STOP_COUNTER,
            FOR_EACH_COMPLETION_CONDITION,
            FOR_EACH_COMPLETION_CONDITION_PARALLEL,
            FOR_EACH_COMPLETION_CONDITION_SUCCESSFUL_BRANCHES_ONLY,
            FOR_EACH_COMPLETION_CONDITION_FAILURE,
            FOR_EACH_PARALLEL,
            FOR_EACH_NEGATIVE_START_COUNTER,
            FOR_EACH_TOO_LARGE_START_COUNTER,
            FOR_EACH_DUPLICATE_COUNTER_VARIABLE,
            FOR_EACH_COMPLETION_CONDITION_NEGATIVE_BRANCHES
    ].flatten() as List<Process>


    public final Process PICK_CORRELATIONS_INIT_ASYNC = builder.buildStructuredActivityProcess(
            "Pick-Correlations-InitAsync", "An asynchronous receive that initiates a correlationSet, followed by a pick with a synchronous onMessage that correlates on this set.",
            [
                    new TestCase(testSteps: [
                            new TestStep(input: "1", operation: WsdlOperation.ASYNC),
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC)
                    ])
            ]
    )

    public final Process PICK_CORRELATIONS_INIT_SYNC = builder.buildStructuredActivityProcess(
            "Pick-Correlations-InitSync", "A receive-reply pair that initiates a correlationSet, followed by a pick with a synchronous onMessage that correlates on this set.",
            [
                    new TestCase(testSteps: [
                            new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC),
                            new TestStep(input: "1", output: "2", operation: WsdlOperation.SYNC)
                    ])
            ]
    )

    public final Process PICK_CREATE_INSTANCE = builder.buildStructuredActivityProcess(
            "Pick-CreateInstance", "A pick with a synchronous onMessage that has createInstance set to yes.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "1", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process PICK_ON_ALARM_UNTIL = builder.buildStructuredActivityProcess(
            "Pick-OnAlarm-Until", "A receive-reply pair that initiates a correlationSet and an intermediate pick that contains an onMessage and an onAlarm with an until element. The onAlarm should fire immediately.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "-1", operation: WsdlOperation.SYNC)])
            ]
    )

    public final Process PICK_ON_ALARM_FOR = builder.buildStructuredActivityProcess(
            "Pick-OnAlarm-For", "An onAlarm with for test case. The test contains a receive-reply pair that initiates a correlationSet and an intermediate pick that contains an onMessage and an onAlarm with an for element. The onAlarm should fire after two seconds and the process should reply with a default value.",
            [
                    new TestCase(testSteps: [new TestStep(input: "1", output: "-1", operation: WsdlOperation.SYNC)])
            ]
    )

    public final List<Process> STRUCTURED_ACTIVITIES_PICK = [
            PICK_CORRELATIONS_INIT_ASYNC,
            PICK_CORRELATIONS_INIT_SYNC,
            PICK_CREATE_INSTANCE,
            PICK_ON_ALARM_FOR,
            PICK_ON_ALARM_UNTIL
    ]

    public final List<Process> STRUCTURED_ACTIVITIES = [
            SEQUENCE,
            WHILE,
            REPEAT_UNTIL,
            REPEAT_UNTIL_EQUALITY,
            STRUCTURED_ACTIVITIES_FLOW,
            STRUCTURED_ACTIVITIES_IF,
            STRUCTURED_ACTIVITIES_FOR_EACH,
            STRUCTURED_ACTIVITIES_PICK
    ].flatten() as List<Process>

}
