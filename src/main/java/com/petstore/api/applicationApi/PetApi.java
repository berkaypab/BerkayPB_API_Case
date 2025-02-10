package com.petstore.api.applicationApi;

import com.petstore.api.RestResource;
import com.petstore.requests.pojo.lombok.Pet;
import io.restassured.response.Response;

import static com.petstore.api.Route.PET;

public class PetApi {

    public static Response post(Object payLoad) {
        return RestResource.post(PET, payLoad);
    }

    public static Response get(String petId) {
        return RestResource.get(PET + "/" + petId);
    }

    public static Response update(Pet requestPet) {
        return RestResource.put(PET, requestPet);
    }

    public static Response delete(String petId) {
        return RestResource.delete(PET + "/" + petId);
    }
}
