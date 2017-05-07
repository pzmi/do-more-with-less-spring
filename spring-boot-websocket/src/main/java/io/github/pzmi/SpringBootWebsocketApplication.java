package io.github.pzmi;

import com.soundcloud.prometheus.hystrix.HystrixPrometheusMetricsPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) {
        HystrixPrometheusMetricsPublisher.register("router");
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

}
