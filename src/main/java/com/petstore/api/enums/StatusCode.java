package com.petstore.api.enums;

public enum StatusCode {
    // Success responses
    CODE_200(200, "success", "ok", "successful operation"),
    CODE_201(201, "success", "ok", "successful operation"),

    // Client error responses
    CODE_400(400, "error", "error", "Invalid ID supplied"),
    CODE_404(404, "error", "error", "Pet not found"),
    CODE_405(405, "error", "error", "Invalid input"),

    // Pet specific responses
    CODE_PET_INVALID_STATUS(400, "error", "error", "Invalid pet status value"),
    CODE_PET_INVALID_ID(400, "error", "error", "Invalid pet value"),
    CODE_PET_VALIDATION(405, "error", "error", "Validation exception");

    public final int code;
    public final String type;
    public final String status;
    public final String msg;

    StatusCode(int code, String type, String status, String msg) {
        this.code = code;
        this.type = type;
        this.status = status;
        this.msg = msg;
    }
    public static StatusCode valueOf(int statusCode) {
        for (StatusCode code : values()) {
            if (code.code == statusCode) {
                return code;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + statusCode);
    }
    /**
     * Returns true if the status code is in the 2xx range
     */
    public boolean isSuccess() {
        return this.code >= 200 && this.code < 300;
    }

    /**
     * Returns true if the status code is in the 4xx range
     */
    public boolean isClientError() {
        return this.code >= 400 && this.code < 500;
    }

    /**
     * Returns the general category of response
     */
    public String getCategory() {
        if (isSuccess()) return "Success";
        if (isClientError()) return "Client Error";
        return "Unknown";
    }
}
