package io.github.pzmi.reverse;

import io.github.pzmi.fibonacci.FibonacciCommand;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FibonacciCommandTest {
    @Test
    public void run() throws Exception {
        FibonacciCommand f = new FibonacciCommand(10);
        BigDecimal result = f.run();
        assertEquals(new BigDecimal(55), result);
    }

}