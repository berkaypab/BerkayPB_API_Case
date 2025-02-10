
package com.petstore.utils;

import java.util.Properties;

public class DataLoader {

    private static final String GET_PET_ID = "get_pet_id";

    private static final String UPDATE_PET_ID = "update_pet_id";
    private static final String DELETE_PET_ID = "delete_pet_id";

    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyUtils.propertyLoader(RESOURCES_PATH + "data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }


    public String get_GetPetID() {
        String prop = properties.getProperty(GET_PET_ID);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException(
                    "Property " + GET_PET_ID + " is not specified in the data.properties file");
        }
    }


    public String get_UpdatePetID() {
        String prop = properties.getProperty(UPDATE_PET_ID);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException(
                    "Property " + UPDATE_PET_ID + " is not specified in the data.properties file");
        }
    }

    public String get_DeletePetID() {
        String prop = properties.getProperty(DELETE_PET_ID);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException(
                    "Property " + DELETE_PET_ID + " is not specified in the data.properties file");
        }
    }
}