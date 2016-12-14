package com.ignitionone.datastorm.datorama.util;

/**
 * MultiTest failure
 */
public class TestFailureException extends Exception {

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new TestFailureException object.
     *
     * @param message in value
     */
    public TestFailureException(String message) {
        super(message);
    }
}
