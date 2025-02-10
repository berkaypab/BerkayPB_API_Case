package com.petstore.requests.pojo.lombok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.Map;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("name")
    String name;
    @JsonIgnore
    Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}