package io.github.pzmi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private String response;

    public Response(String toThank) {
        this.response = "Thank you for " + toThank;
    }

    @JsonProperty
    public String getResponse() {
        return response;
    }
}
