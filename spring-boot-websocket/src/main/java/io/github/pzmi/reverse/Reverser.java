package io.github.pzmi.reverse;

import io.github.pzmi.simplerest.core.Message;
import io.github.pzmi.simplerest.core.Observer;
import io.github.pzmi.simplerest.core.Route;
import io.github.pzmi.simplerest.core.SimpleTextMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Reverser implements Route {
    private List<Observer> observers = new ArrayList<>();

    private static Message responseOf(Message message) {
        String response = new StringBuilder(message.getBody()).reverse().toString();
        return new SimpleTextMessage("reverser", message.getSender(), response);
    }

    @Override
    public String path() {
        return "reverse";
    }

    @Override
    public void forward(Message message) {
        Message m = responseOf(message);
        observers.forEach(o -> o.notify(m));
    }

    @Override
    public void register(Observer observer) {

    }
}
