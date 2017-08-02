package io.github.pzmi.router.websocket;

class WsMessage {
    private String payload;
    private String service;

    public WsMessage() {
    }

    public WsMessage(String payload, String service) {
        this.payload = payload;
        this.service = service;
    }

    public String getPayload() {
        return payload;
    }

    public String getService() {
        return service;
    }
}
