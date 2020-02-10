package com.packtpub.java.coding.problems.common.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkinnyExchange {

    private final String token;
    private final String apiKey;
    private final String endPoint;

    private final JsonNode body;

}
