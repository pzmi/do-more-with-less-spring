package io.github.pzmi.fibonacci.rest;

import io.github.pzmi.fibonacci.core.Fibonacci;
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
        return Fibonacci.calculateNth(n);
    }
}
