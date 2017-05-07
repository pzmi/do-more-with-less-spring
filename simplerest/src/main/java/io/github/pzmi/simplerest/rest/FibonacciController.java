package io.github.pzmi.simplerest.rest;

import io.github.pzmi.simplerest.core.FibonacciCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/fibonacci")
@RestController
public class FibonacciController {
    @GetMapping("/{n}")
    public BigDecimal fibonacci(@PathVariable long n) {
        return new FibonacciCommand(n).execute();
    }
}
