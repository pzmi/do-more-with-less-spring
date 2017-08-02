package io.github.pzmi.router.core;

public class SimpleTextMessage implements Message {
    private final String sender;
    private final String receiver;
    private final String body;

    public SimpleTextMessage(String sender, String receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public String getBody() {
        return body;
    }
}
