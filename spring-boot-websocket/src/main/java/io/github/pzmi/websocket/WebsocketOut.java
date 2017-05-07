package io.github.pzmi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pzmi.simplerest.core.Message;
import io.github.pzmi.simplerest.core.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketOut implements Out {
    private static final Logger LOG = LoggerFactory.getLogger(WebsocketOut.class);
    private Map<Integer, WebSocketSession> clients = new ConcurrentHashMap<>();
    private ObjectMapper mapper;

    public WebsocketOut(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void send(Message message) {
        WebSocketSession client = clients.get(Integer.parseInt(message.getReceiver()));
        if (client != null) {
            try {
                String response = mapper.writeValueAsString(new Response(message.getBody()));
                send(client, new TextMessage(response));
            } catch (JsonProcessingException e) {
                LOG.warn("Could not deserialize outgoing message", e);
                send(client, new TextMessage("Service error"));
            }
        }
    }

    void send(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOG.warn("Could not send message to client", e);
        }
    }

    void register(WebSocketSession session) {
        clients.put(session.hashCode(), session);
    }

    void deregister(WebSocketSession session) {
        clients.remove(session.hashCode());
    }
}
