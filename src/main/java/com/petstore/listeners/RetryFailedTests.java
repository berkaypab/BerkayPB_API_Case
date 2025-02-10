package com.petstore.listeners;

import com.petstore.constants.FrameworkConstants;
import com.petstore.utils.ConfigLoader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer {

    private int count = 0;
    private int retries = 1;

    @Override
    public boolean retry(ITestResult result) {

        boolean value = false;
        if (ConfigLoader.getInstance().getRetryFailedTests().equalsIgnoreCase(FrameworkConstants.getYes())) {
            if (count < retries) {
                count++;
                return true;
            }
        }
        return value;
    }
}
