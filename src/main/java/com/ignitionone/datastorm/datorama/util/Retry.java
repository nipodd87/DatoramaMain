package com.ignitionone.datastorm.datorama.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    //~ Instance fields ------------------------------------------------------------------------------------------------

    // set maxcounter value this will execute our test 3 times
    int maxretryCount = 1;

    // set counter to 0
    int minretryCount = 1;

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * override retry Method
     *
     * @param result in value
     *
     * @return out value
     */
    public boolean retry(ITestResult result) {
        // this will run until max count completes if test pass within this frame it will come out of for loop
        if (minretryCount <= maxretryCount) {
            // print the test name for log purpose
            System.out.println("Following test is failing====" + result.getName());

            // print the counter value
            System.out.println("Retrying the test Count is=== " + (minretryCount));

            // increment counter each time by 1
            minretryCount++;
            return true;
        }
        return false;
    }
}
