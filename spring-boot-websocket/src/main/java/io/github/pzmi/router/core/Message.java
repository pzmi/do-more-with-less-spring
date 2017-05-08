package io.github.pzmi.router.core;

public interface Message {
    String getSender();

    String getReceiver();

    String getBody();
}
