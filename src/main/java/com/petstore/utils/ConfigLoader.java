package com.petstore.utils;

import java.util.Properties;

import com.petstore.api.enums.EnvType;

public class ConfigLoader {


    private static final String BASE_URI_API = "base_uri_api";
    private static final String BASE_URI_ACCOUNTS = "base_uri_accounts";

    private static final String OVERRIDE_REPORTS = "override_reports";
    private static final String REQUEST_RESPONSE_DETAILS_IN_REPORTS = "request_response_details_in_reports";
    private static final String RETRY_FAILED_TESTS = "retry_failed_tests";

    private static final String ENV = "env";
    private static final String CONFIG_PROPERTIES = "_config.properties";

    /* Default config file is stg_config.properties */
    private static final String STG_CONFIG_PROPERTIES = "stg" + CONFIG_PROPERTIES;
    private static final String PROD_CONFIG_PROPERTIES = "prod" + CONFIG_PROPERTIES;
    private static final String QA_CONFIG_PROPERTIES = "qa" + CONFIG_PROPERTIES;
    private static final String TEST_CONFIG_PROPERTIES = "test" + CONFIG_PROPERTIES;
    private static final String INT_CONFIG_PROPERTIES = "int" + CONFIG_PROPERTIES;


    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {

        /* Setting EnvType.STAGE as default environment */
        /*
         * This will check for the env value from Jenkins/Maven first. If it does not
         * get any input from Jenkins/mvn cmd line, then, will take
         * stg_config.properties file as default
         */
        String env = System.getProperty(ENV, EnvType.STAGE.toString());

        switch (EnvType.valueOf(env)) {

            /* Only STAGE is working, Rest are taken for example */
            case STAGE: {
                properties = getConfigPropertyFile(STG_CONFIG_PROPERTIES);
                break;
            }
            case INT: {
                properties = getConfigPropertyFile(INT_CONFIG_PROPERTIES);
                break;
            }
            case QA: {
                properties = getConfigPropertyFile(QA_CONFIG_PROPERTIES);
                break;
            }
            case TEST: {
                properties = getConfigPropertyFile(TEST_CONFIG_PROPERTIES);
                break;
            }
            case PRODUCTION: {
                properties = getConfigPropertyFile(PROD_CONFIG_PROPERTIES);
                break;
            }
            default: {
                throw new IllegalStateException("Invalid EnvType: " + env);
            }

        }
    }

    private Properties getConfigPropertyFile(String configFile) {
        return PropertyUtils.propertyLoader(RESOURCES_PATH + configFile);
    }

    private String getPropertyValue(String propertyKey) {
        String prop = properties.getProperty(propertyKey);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException("Property " + propertyKey + " is not specified in the config.properties file");
        }
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }


    public String getBaseUriAPI() {
        return getPropertyValue(BASE_URI_API);
    }

    public String getBaseUriAccounts() {
        return getPropertyValue(BASE_URI_ACCOUNTS);
    }

    public String getOverrideReports() {
        return getPropertyValue(OVERRIDE_REPORTS);
    }

    public String getRequestDetailsInReports() {
        return getPropertyValue(REQUEST_RESPONSE_DETAILS_IN_REPORTS);
    }


    public String getRetryFailedTests() {
        System.out.println("==============================================================");
        System.out.println("RETRY_FAILED_TESTS" + RETRY_FAILED_TESTS);
        System.out.println("==============================================================");
        return getPropertyValue(RETRY_FAILED_TESTS);
    }

}
