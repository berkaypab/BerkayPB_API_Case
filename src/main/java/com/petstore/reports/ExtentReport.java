package com.petstore.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.petstore.api.enums.CategoryType;
import com.petstore.constants.FrameworkConstants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReport {

    private ExtentReport() {
    }

    private static ExtentReports extent;

    public static void initReports() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());

            extent.attachReporter(spark);

            // spark.config().setEncoding("utf-8");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(FrameworkConstants.getProjectName() + " - ALL");
            spark.config().setReportName(FrameworkConstants.getProjectName() + " - ALL");

        }
    }

    public static void flushReports() {

        if (Objects.nonNull(extent)) {
            extent.flush();
        }

        ExtentManager.unload();
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilePath()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testCaseName) {
        ExtentManager.setExtentTest(extent.createTest(testCaseName));
    }


    synchronized public static void addCategories(CategoryType[] categories) {
        for (CategoryType category : categories) {
            ExtentManager.getExtentTest().assignCategory(category.toString());
        }
    }

}
