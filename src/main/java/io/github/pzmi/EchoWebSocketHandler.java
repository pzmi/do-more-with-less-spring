package io.github.pzmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


public class EchoWebSocketHandler extends TextWebSocketHandler {

    private ObjectMapper mapper;

    public EchoWebSocketHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        EchoMessage echoMessage = mapper.readValue(message.getPayload(), EchoMessage.class);
        String response = mapper.writeValueAsString(new Response(echoMessage.getToEcho()));
        session.sendMessage(new TextMessage(response));
    }

}
