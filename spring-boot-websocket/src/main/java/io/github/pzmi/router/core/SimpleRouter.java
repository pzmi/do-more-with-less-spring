package io.github.pzmi.router.core;

import java.util.HashMap;
import java.util.Map;

public class SimpleRouter implements Router, Observer {
    private final Map<String, Route> routes = new HashMap<>();
    private Out out;

    public SimpleRouter(Out out) {
        this.out = out;
    }

    public void addRoute(Route route) {
        routes.put(route.path(), route);
    }

    public void route(String destination, Message message) {
        routes.get(destination).forward(message);
    }

    @Override
    public void notify(Message message) {
        out.send(message);
    }
}
