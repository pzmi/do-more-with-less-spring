package io.github.pzmi.router.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleRouter implements Router, Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRouter.class);

    private final Map<String, Route> routes = new HashMap<>();
    private Out out;

    public SimpleRouter(Out out) {
        this.out = out;
    }

    public void addRoute(Route route) {

        routes.put(route.path(), route);
    }

    public void route(String destination, Message message) {
        LOGGER.info("Message from {} to {}", message.getSender(), message.getReceiver());
        routes.get(destination).forward(message);
    }

    @Override
    public void notify(Message message) {
        out.send(message);
    }
}
