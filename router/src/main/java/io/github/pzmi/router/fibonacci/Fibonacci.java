package io.github.pzmi.router.fibonacci;

import io.github.pzmi.router.core.Observer;
import io.github.pzmi.router.core.Message;
import io.github.pzmi.router.core.Route;
import io.github.pzmi.router.core.SimpleTextMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class Fibonacci implements Route {
    private List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public String path() {
        return "fibonacci";
    }

    @Override
    public void forward(Message message) {
        int nth = Integer.parseInt(message.getBody());
        BigDecimal result = new FibonacciCommand(nth).execute();
        Message response = responseOf(message, result);
        observers.forEach(o -> o.notify(response));
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    private static Message responseOf(Message message, BigDecimal number) {
        String response = number.toString();
        return new SimpleTextMessage("fibonacci", message.getSender(), response);
    }
}
