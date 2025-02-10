package com.petstore.api;

import static com.petstore.api.Route.BASE_PATH;

import com.petstore.utils.ConfigLoader;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getBaseUriAPI()).
                setBasePath(BASE_PATH).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL).
                build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL).
                build();
    }
}
