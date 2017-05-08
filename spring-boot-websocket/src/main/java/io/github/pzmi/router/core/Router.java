package io.github.pzmi.router.core;

public interface Router {
    void addRoute(Route route);

    void route(String destination, Message message);
}
