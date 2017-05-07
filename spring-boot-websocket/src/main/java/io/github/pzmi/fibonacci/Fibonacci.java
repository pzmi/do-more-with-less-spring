package io.github.pzmi.fibonacci;

import io.github.pzmi.simplerest.core.Message;
import io.github.pzmi.simplerest.core.Observer;
import io.github.pzmi.simplerest.core.Route;
import org.springframework.stereotype.Service;

@Service
public class Fibonacci implements Route {

    @Override
    public String path() {
        return "fibonacci";
    }

    @Override
    public void forward(Message message) {

    }

    @Override
    public void register(Observer observer) {

    }
}
