package io.github.pzmi.simplerest.core;

public interface Router {
    void addRoute(Route route);

    void route(String destination, Message message);
}
