package io.github.pzmi.websocket;

class EchoMessage {
    private final String toEcho;

    public EchoMessage(String toEcho) {
        this.toEcho = toEcho;
    }

    public String getToEcho() {
        return toEcho;
    }
}
