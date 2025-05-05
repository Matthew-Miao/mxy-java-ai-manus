package com.mxy.ai.manus.config.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * @Author：mxy
 * @Date：2025-05-02-22:39
 * @Version：1.0
 * @Description：
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SseParameters {
    @JsonProperty("base_uri")
    private String baseUri;

    @JsonProperty("headers")
    private Map<String, String> headers;

    @JsonProperty("uri_variables")
    private Map<String, String> uriVariables;
}
