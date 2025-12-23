package com.example;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    private int count = 0;
    private static final int maxRetry = 4;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;
            return true;
        }
        return false;
    }
}
