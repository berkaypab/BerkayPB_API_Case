package com.petstore.requests.pojo.lombok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("category")
    Category category;
    @JsonProperty("name")
    String name;
    @JsonProperty("photoUrls")
    List<String> photoUrls;
    @JsonProperty("tags")
    List<Tag> tags;
    @JsonProperty("status")
    String status;
    @JsonIgnore
    Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
