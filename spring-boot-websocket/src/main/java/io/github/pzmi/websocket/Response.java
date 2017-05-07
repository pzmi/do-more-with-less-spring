package io.github.pzmi.websocket;

class Response {
    private final String response;

    Response(String toThank) {
        this.response = "Thank you for " + toThank;
    }

    public String getResponse() {
        return response;
    }
}
