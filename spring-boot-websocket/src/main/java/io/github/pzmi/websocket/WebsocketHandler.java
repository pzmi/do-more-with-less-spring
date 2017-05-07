package io.github.pzmi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pzmi.simplerest.core.Router;
import io.github.pzmi.simplerest.core.SimpleTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public WebsocketHandler(Router router, ObjectMapper mapper, WebsocketOut out) {
        this.router = router;
        this.mapper = mapper;
        this.out = out;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        out.register(session);
        session.sendMessage(new TextMessage("Hello"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        out.deregister(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        Optional<EchoMessage> deserialized = deserialize(message);
        if (deserialized.isPresent()) {
            deserialized.map(m -> of(session, m)).ifPresent(m -> router.route("reverser", m));
        } else {
            out.send(session, new TextMessage("Command not supported"));
        }
    }

    private Optional<EchoMessage> deserialize(TextMessage message) {
        try {
            return Optional.of(mapper.readValue(message.getPayload(), EchoMessage.class));
        } catch (IOException e) {
            LOG.warn("Could not deserialize received message", e);
            return Optional.empty();
        }
    }

    private SimpleTextMessage of(WebSocketSession session, EchoMessage m) {
        return new SimpleTextMessage(Integer.toString(session.hashCode()), "reverser", m.getToEcho());
    }
}
