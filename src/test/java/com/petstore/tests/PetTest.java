package com.petstore.tests;

import com.petstore.annotations.FrameworkAnnotation;
import com.petstore.api.applicationApi.PetApi;
import com.petstore.api.enums.CategoryType;
import com.petstore.api.enums.StatusCode;
import com.petstore.constants.FrameworkConstants;
import com.petstore.requests.pojo.lombok.ApiResponse;
import com.petstore.requests.pojo.lombok.Category;
import com.petstore.requests.pojo.lombok.Pet;
import com.petstore.requests.pojo.lombok.Tag;
import com.petstore.utils.VerificationManager;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.petstore.utils.FakerUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("PetStoreV2")
@Feature("PetStore API")
public class PetTest extends _BaseTest {
    @Story("Get a pet story")
    @Link("https://petstore.swagger.io/")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description - From ")
    @FrameworkAnnotation(category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get pet data")
    public void shouldBeAbleToGetAPet() {
        Pet requestPet = Pet.builder()
                .id(generateNumber())
                .name(generateAnimalName())
                .status("available")
                .category(categoryBuilder(generateNumber(), generateCategoryName()))
                .photoUrls(Arrays.asList("http://example.com/photo1.jpg"))
                .tags(Arrays.asList(
                        Tag.builder().id(1).name("friendly").build(),
                        Tag.builder().id(2).name("vaccinated").build()
                ))
                .build();

        Response createResponse = PetApi.post(requestPet);
        assertStatusCode(createResponse.statusCode(), StatusCode.CODE_200, "User should be able to create a Pet");


        Response getResponse = PetApi.get(String.valueOf(requestPet.getId()));
        assertStatusCode(getResponse.statusCode(), StatusCode.CODE_200, "User should be able to get the Pet details");


        Pet responsePet = getResponse.as(Pet.class);
        assertPetEqual(responsePet, requestPet);

    }

    @Story("Create a pet story")
    @Link("https://petstore.swagger.io/")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description - From ")
    @FrameworkAnnotation(category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to create a pet")
    public void shouldBeAbleToCreateAPet() {
        Pet requestPet = Pet.builder()
                .id(generateNumber())
                .name(generateAnimalName())
                .status("available")
                .category(categoryBuilder(generateNumber(), generateCategoryName()))
                .photoUrls(Arrays.asList("http://example.com/photo1.jpg"))
                .tags(Arrays.asList(
                        Tag.builder().id(1).name("friendly").build(),
                        Tag.builder().id(2).name("vaccinated").build()
                ))
                .build();

        Response response = PetApi.post(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should be able to create a Pet");
        assertPetEqual(response.as(Pet.class), requestPet);

        Response getResponse = PetApi.get(String.valueOf(requestPet.getId()));
        assertStatusCode(getResponse.statusCode(), StatusCode.CODE_200, "Created pet should be retrievable");
        assertPetEqual(getResponse.as(Pet.class), requestPet);
    }

    @FrameworkAnnotation(category = {CategoryType.BVT, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"BVT", "SANITY", "REGRESSION"})
    public void shouldBeAbleToUpdateAPet() {
        Pet originalPet = petBuilder(
                String.valueOf(generateNumber()),
                generateAnimalName(),
                generateStatus(),
                categoryBuilder(generateNumber(), generateCategoryName()));
        Response createResponse = PetApi.post(originalPet);
        assertStatusCode(createResponse.statusCode(), StatusCode.CODE_200, "User should be able to create a Pet");

        Pet updatePet = petBuilder(
                String.valueOf(originalPet.getId()),
                generateAnimalName(),
                "sold",
                categoryBuilder(generateNumber(), generateCategoryName())); // yeni kategori
        Response updateResponse = PetApi.update(updatePet);
        assertStatusCode(updateResponse.statusCode(), StatusCode.CODE_200, "User should be able to update a Pet");
        Pet responsePet = updateResponse.as(Pet.class);
        assertPetEqual(responsePet, updatePet);


    }

    @FrameworkAnnotation(category = {CategoryType.BVT, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"BVT", "SANITY", "REGRESSION"})
    public void shouldBeAbleToDeleteAPet() {
        Pet requestPet = petBuilder(
                String.valueOf(generateNumber()),
                generateAnimalName(),
                generateStatus(),
                categoryBuilder(generateNumber(), generateCategoryName()));
        Response createResponse = PetApi.post(requestPet);
        assertStatusCode(createResponse.statusCode(), StatusCode.CODE_200, "User should be able to create a Pet");
        String petIdToDelete = String.valueOf(requestPet.getId());

        Response deleteResponse = PetApi.delete(petIdToDelete);

        ApiResponse apiResponse = deleteResponse.as(ApiResponse.class);

        assertStatusCode(deleteResponse.statusCode(), StatusCode.CODE_200, "User should be able to delete a Pet");
        assertDeleteResponse(apiResponse, petIdToDelete);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should return 404 when trying to get a non-existent pet")
    public void shouldReturn404WhenGettingNonExistentPet() {
        String nonExistentPetId = "9000000";
        Response response = PetApi.get(nonExistentPetId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_404, "Should return 404 when trying to get a non-existent pet");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_404);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE})
    @Test(groups = {"NEGATIVE"}, description = "should return 400 when trying to create a pet with invalid status")
    public void shouldReturn400WhenCreatingPetWithInvalidStatus() {
        Pet requestPet = petBuilder(
                String.valueOf(generateNumber()),
                generateAnimalName(),
                "invalid_status", // geçerli değerler: available, pending, sold
                categoryBuilder(generateNumber(), generateCategoryName()));

        Response response = PetApi.post(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_PET_INVALID_STATUS,
                "Should return 400 when trying to create a pet with invalid status");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_PET_INVALID_STATUS);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE})
    @Test(groups = {"NEGATIVE"}, description = "should return 400 when updating pet with invalid ID")
    public void shouldReturn400WhenUpdatingPetWithInvalidId() {
        Pet requestPet = petBuilder(
                "777777",
                generateAnimalName(),
                generateStatus(),
                categoryBuilder(generateNumber(), generateCategoryName()));

        Response response = PetApi.update(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400,
                "Should return 400 when trying to update a pet with invalid ID");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_400);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE})
    @Test(groups = {"NEGATIVE"}, description = "should return 400 when creating pet with empty name")
    public void shouldReturn400WhenCreatingPetWithEmptyName() {
        Pet requestPet = petBuilder(
                String.valueOf(generateNumber()),
                "", // boş isim
                generateStatus(),
                categoryBuilder(generateNumber(), generateCategoryName()));

        Response response = PetApi.post(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400,
                "Should return 400 when trying to create a pet with empty name");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_400);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE})
    @Test(groups = {"NEGATIVE"}, description = "should return 400 when creating pet with invalid ID")
    public void shouldReturn400WhenCreatingPetWithInvalidId() {
        Pet requestPet = petBuilder(
                "-1",
                generateAnimalName(),
                generateStatus(),
                categoryBuilder(generateNumber(), generateCategoryName()));

        Response response = PetApi.post(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_PET_INVALID_ID,
                "Should return 400 when trying to create a pet with invalid ID");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_PET_INVALID_ID);
    }

    @FrameworkAnnotation(category = {CategoryType.NEGATIVE})
    @Test(groups = {"NEGATIVE"}, description = "should return 405 when validation fails")
    public void shouldReturn405WhenValidationFails() {
        Pet requestPet = Pet.builder()
                .id(generateNumber())
                // name alanı eksik - zorunlu alan
                .status(generateStatus())
                .category(Category.builder().id(generateNumber()).name(generateCategoryName()).build())
                .build();

        Response response = PetApi.post(requestPet);
        assertStatusCode(response.statusCode(), StatusCode.CODE_PET_VALIDATION,
                "Should return 405 when validation fails");
        assertError(response.as(ApiResponse.class), StatusCode.CODE_PET_VALIDATION);
    }

    @Step
    public Category categoryBuilder(int id, String name) {
        return Category.builder().name(name).id(id).build();
    }


    @Step
    public Pet petBuilder(String id, String name, String status, Category category) {
        return Pet.builder().id(Integer.valueOf(id)).name(name).status(status).category(category).build();
    }


    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode, String message) {
        //assertThat(actualStatusCode, equalTo(statusCode.code));
        VerificationManager.validateResponse(actualStatusCode, statusCode.code,
                FrameworkConstants.ASSERTION_FOR_RESPONSE_STATUS_CODE +
                        " - <b> <u> " + message + " </u> </b> " +
                        "(" + statusCode.getCategory() + ")");
    }

    @Step
    public void assertPetEqual(Pet responsePet, Pet requestPet) {
        assertThat(responsePet.getId(), notNullValue());
        assertThat(responsePet.getName(), notNullValue());
        assertThat(responsePet.getStatus(), isIn(Arrays.asList("available", "pending", "sold")));

        VerificationManager.validateResponse(responsePet.getName(), requestPet.getName(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> NAME </u> </b>");
        VerificationManager.validateResponse(responsePet.getCategory().getId(), requestPet.getCategory().getId(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> CATEGORY ID</u> </b>");
        VerificationManager.validateResponse(responsePet.getCategory().getName(), requestPet.getCategory().getName(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> CATEGORY NAME</u> </b>");
        VerificationManager.validateResponse(responsePet.getId(), requestPet.getId(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> ID </u> </b>");
        VerificationManager.validateResponse(responsePet.getStatus(), requestPet.getStatus(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> STATUS </u> </b>");
    }

    @Step
    public void assertError(ApiResponse responseErr, StatusCode statusCode) {
        assertThat(responseErr.getMessage(), equalTo(statusCode.msg));
        VerificationManager.validateResponse(responseErr.getMessage(), statusCode.msg,
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> MSG </u> </b>");
    }

    @Step
    public void assertDeleteResponse(ApiResponse response, String expectedPetId) {
        VerificationManager.validateResponse(response.getCode(), 200,
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> CODE </u> </b>");
        VerificationManager.validateResponse(response.getType(), "unknown",
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> TYPE </u> </b>");
        VerificationManager.validateResponse(response.getMessage(), expectedPetId,
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> MESSAGE (PET ID) </u> </b>");
    }
}
