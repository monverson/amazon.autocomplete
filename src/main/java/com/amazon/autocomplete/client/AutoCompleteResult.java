package com.amazon.autocomplete.client;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoCompleteResult {

    private static final String PARAM = "q";
    private static final String URL = "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1";

    public List<String> getAmazonAutoCompleteResult(String keyword) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post(URL)
                .queryString(PARAM, keyword)
                .asJson();
        List<String> autoCompleteResult = jsonNodeHttpResponse.getBody().getArray().getJSONArray(1).toList();
        return autoCompleteResult;
    }
}
