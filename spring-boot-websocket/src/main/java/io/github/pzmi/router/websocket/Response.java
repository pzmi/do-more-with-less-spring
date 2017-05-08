package io.github.pzmi.router.websocket;

class Response {
    private final String response;

    Response(String response) {
        this.response = response;
    }

    public String getResponse() {
         return response;
    }
}
