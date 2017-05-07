package io.github.pzmi.simplerest.core;

public interface Message {
    String getSender();

    String getReceiver();

    String getBody();
}
