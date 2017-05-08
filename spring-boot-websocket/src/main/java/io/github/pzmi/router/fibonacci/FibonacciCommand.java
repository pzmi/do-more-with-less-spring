package io.github.pzmi.router.fibonacci;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;

public class FibonacciCommand extends HystrixCommand<BigDecimal> {
    private static final UriTemplate uri = new UriTemplate("http://localhost:8080/fibonacci/{n}");
    private int nth;

    public FibonacciCommand(int nth) {
        super(HystrixCommandGroupKey.Factory.asKey("Fibonacci"));
        this.nth = nth;
    }

    @Override
    protected BigDecimal run() {
        RestTemplate r = new RestTemplate();
        return r.getForObject(uri.expand(nth), BigDecimal.class);
    }
}
