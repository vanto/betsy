package betsy.data

import betsy.data.assertions.XpathTestAssertion
import betsy.data.assertions.NotDeployableAssertion


class TestStep {

    /**
     * The input value which is send using the <code>operation</code> to the system under test.
     */
    String input

    /**
     * The WSDL operation which is invoked in this test step
     */
    WsdlOperation operation

    /**
     * Time to wait/delay further test execution after processing this step in milliseconds.
     */
    Integer timeToWaitAfterwards

    /**
     * List of assertions which are evaluated after the test step has been executed/the messages have been sent.
     */
    List<TestAssertion> assertions = []

    /**
     * just for documentation purposes
     */
    String description

    boolean isOneWay() {
        WsdlOperation.ASYNC == operation
    }

    public void setOutput(String output) {
        assertions << new XpathTestAssertion(expectedOutput: "true", xpathExpression: "declare namespace test='http://dsg.wiai.uniba.de/bpel-engine-comparison/activities/wsdl/testinterface';//test:testElementSyncResponse = ${output}", output: "${output}")
    }

    public void setOutputAsLeast(String output) {
        assertions << new XpathTestAssertion(expectedOutput: "true", xpathExpression: "declare namespace test='http://dsg.wiai.uniba.de/bpel-engine-comparison/activities/wsdl/testinterface';//test:testElementSyncResponse >= ${output}", output: "${output}")
    }

    void setAssertions(List<TestAssertion> assertions) {
        this.assertions.addAll(assertions)
    }

    boolean isNotDeployable() {
        assertions.any {it instanceof NotDeployableAssertion }
    }

    @Override
    public String toString() {
        return "TestStep{" +
                "input='" + input + '\'' +
                ", operation=" + operation +
                ", assertions=" + assertions +
                '}';
    }
}
