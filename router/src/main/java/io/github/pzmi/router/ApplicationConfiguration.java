package io.github.pzmi.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.soundcloud.prometheus.hystrix.HystrixPrometheusMetricsPublisher;
import io.github.pzmi.router.core.Observer;
import io.github.pzmi.router.core.Out;
import io.github.pzmi.router.core.Route;
import io.github.pzmi.router.core.Router;
import io.github.pzmi.router.core.SimpleRouter;
import io.github.pzmi.router.websocket.WebsocketHandler;
import io.github.pzmi.router.websocket.WebsocketOut;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Collection;

@Configuration
@EnableWebSocket
public class ApplicationConfiguration implements WebSocketConfigurer {
    private Collection<Route> routes;

    // automatic discovery of multiple implementations
    public ApplicationConfiguration(Collection<Route> routes) {
        this.routes = routes;
    }

    @Bean
    public Router router(Out out) {
        Router r = new SimpleRouter(out);
        routes.forEach(r::addRoute); // using discovered implementations
        routes.forEach(route -> route.register((Observer) r));
        return r;
    }

    // simple websocket
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler(router(wsOut()), wsOut()), "/").setAllowedOrigins("*");
    }


    @Bean
    public WebsocketHandler wsHandler(Router router, WebsocketOut out) {
        return new WebsocketHandler(router, objectMapper(), out);
    }

    @Bean
    public WebsocketOut wsOut() {
        return new WebsocketOut(objectMapper());
    }


    // jackson configuration
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }

    // automagical Hystrix's metrics publication for Prometheus
    @Bean
    @ConditionalOnMissingBean
    public CollectorRegistry metricRegistry() {
        HystrixPrometheusMetricsPublisher.register("router");
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    public ServletRegistrationBean registerPrometheusExporterServlet(CollectorRegistry metricRegistry) {
        return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/prometheus");
    }
}
