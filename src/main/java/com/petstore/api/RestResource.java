package com.petstore.api;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.petstore.constants.FrameworkConstants;
import com.petstore.reports.ExtentLogger;
import com.petstore.utils.ConfigLoader;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.PrintStream;
import java.io.StringWriter;

import static com.petstore.api.SpecBuilder.getRequestSpec;
import static com.petstore.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String path, Object payLoad) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(getRequestSpec()).
                        body(payLoad).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        post(path).
                        then().
                        spec(getResponseSpec()).
                        extract().response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }

    public static Response get(String path) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(getRequestSpec()).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        get(path).
                        then().
                        spec(getResponseSpec()).
                        extract().
                        response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }

    public static Response put(String path, Object payLoad) {
        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(getRequestSpec()).
                        body(payLoad).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        put(path).
                        then().
                        extract().response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }
    public static Response delete(String path) {
        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(getRequestSpec()).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        delete(path).
                        then().
                        spec(getResponseSpec()).
                        extract().
                        response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }

    private static void printDetailsInExtentReport(StringWriter writer, Response response) {
        if (ConfigLoader.getInstance().getRequestDetailsInReports().equalsIgnoreCase(FrameworkConstants.getYes())) {
            ExtentLogger.info("<details><summary><i><font color=black> Request details: </font></i>" + "</summary>"
                    + "<pre>" + writer.toString() + "</pre>" + "</details> \n");
            ExtentLogger.info("<details><summary><i><font color=black> Response details: </font></i>" + "</summary>"
                    + "<pre>" + response.asString() + "</pre>" + "</details> \n");
            ExtentLogger.info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        }
    }

}

