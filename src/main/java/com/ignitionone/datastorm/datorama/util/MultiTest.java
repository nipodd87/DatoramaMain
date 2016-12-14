package com.ignitionone.datastorm.datorama.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * Allow a multi-test test method to continue, failing at the end if any failure messages were collected.
 *
 * <p/>Restrictions: startTest must be called first, endTest must be called last, and neither method can be called twice
 * in a row.
 *
 * <p/>Usage:
 *
 * <pre>
 * &#64;Test
 * public void runScripts() throws Exception {
 *     try {
 *         MultiTest.startTest();
 *         // Execute tests here. Call `MultiTest.addFailure(new -estFailureException("Test failed"));` to fail a test (won't stop test).
 *     } finally {
 *         MultiTest.endTest(); // Fails the test if `addFailure` was called.
 *     }
 * }
 * </pre>
 */
public class MultiTest {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    private static final String TEST_FAILED_MESSAGE =
        "Multi-test method \"%s#%s\" failed with the following errors:\n%s";
    private static final String END_TEST_NOT_CALLED_MESSAGE =
        "MultiTest.endTest() has not been called!"
            + " This error can happen if a previous test executed on this thread didn't call MultiTest.endTest()";
    private static final String START_TEST_NOT_CALLED_MESSAGE = "MultiTest.startTest() has not been called!";
    private static ThreadLocal<MultiTest> THREAD_LOCAL = new ThreadLocal<>();
    private static int MAX_ERROR_MESSAGES = 5;

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private List<TestFailureException> failures = new ArrayList<>();

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Add failure.
     *
     * @param exception the exception
     */
    public static void addFailure(TestFailureException exception) {
        MultiTest multiTest = THREAD_LOCAL.get();

        if (multiTest != null) {
            multiTest.failures.add(exception);
        }
    }

    /**
     * End test.
     */
    public static void endTest() {
        assertTestStarted();

        try {
            if (!isSuccessful()) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                String errorList = getFailureOutput();
                String message = String.format(TEST_FAILED_MESSAGE, className, methodName, errorList);

                fail(message);
            }
        } finally {
            THREAD_LOCAL.remove();
        }
    }

    /**
     * Is successful boolean.
     */
    public static void exitOnMaxErrors() {
        MultiTest multiTest = THREAD_LOCAL.get();
        if (multiTest.failures.size() > MAX_ERROR_MESSAGES) {
            Assert.fail("Ending the test as there are More than " + MAX_ERROR_MESSAGES + " errors");
        }
    }

    /**
     * Is successful boolean.
     *
     * @return the boolean
     */
    public static boolean isSuccessful() {
        assertTestStarted();

        return THREAD_LOCAL.get().failures.size() == 0;
    }

    /**
     * Start test.
     */
    public static void startTest() {
        if (THREAD_LOCAL.get() != null) {
            throw new RuntimeException(END_TEST_NOT_CALLED_MESSAGE);
        }

        THREAD_LOCAL.set(new MultiTest());
    }

    /**
     * Start test.
     *
     * @param maxErrorMessage in value
     */
    public static void startTest(int maxErrorMessage) {
        MAX_ERROR_MESSAGES = maxErrorMessage;
        startTest();
    }

    /**
     * Assert test started.
     */
    private static void assertTestStarted() {
        if (THREAD_LOCAL.get() == null) {
            throw new RuntimeException(START_TEST_NOT_CALLED_MESSAGE);
        }
    }

    /**
     * Gets failure output.
     *
     * @return the failure output
     */
    private static String getFailureOutput() {
        StringBuilder result = new StringBuilder();

        for (TestFailureException exception : THREAD_LOCAL.get().failures) {
            result.append(ExceptionUtils.getStackTrace(exception));
        }

        return result.toString();
    }
}
