package com.petstore.requests.pojo.lombok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("type")
    private String type;
    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}