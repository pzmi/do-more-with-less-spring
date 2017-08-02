package io.github.pzmi.router.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pzmi.router.core.Router;
import io.github.pzmi.router.core.SimpleTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Optional;

public class WebsocketHandler extends TextWebSocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(WebsocketHandler.class);

    private final Router router;
    private final ObjectMapper mapper;
    private WebsocketOut out;

    // injected property from property file. when missing defaults to Hello
    @Value("${websocket.welcomemessage:Hello}")
    private String welcomeMessage;

    public WebsocketHandler(Router router, ObjectMapper mapper, WebsocketOut out) {
        this.router = router;
        this.mapper = mapper;
        this.out = out;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        out.register(session);
        session.sendMessage(new TextMessage(welcomeMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        out.deregister(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        Optional<WsMessage> deserialized = deserialize(message);
        if (deserialized.isPresent()) {
            WsMessage wsMessage = deserialized.get();
            router.route(wsMessage.getService(), of(session, wsMessage));
        } else {
            out.send(session, new TextMessage("Command not supported"));
        }
    }

    private Optional<WsMessage> deserialize(TextMessage message) {
        try {
            return Optional.of(mapper.readValue(message.getPayload(), WsMessage.class));
        } catch (IOException e) {
            LOG.warn("Could not deserialize received message", e);
            return Optional.empty();
        }
    }

    private SimpleTextMessage of(WebSocketSession session, WsMessage m) {
        return new SimpleTextMessage(Integer.toString(session.hashCode()), m.getService(), m.getPayload());
    }
}
