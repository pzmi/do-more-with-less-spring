package io.github.pzmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.github.pzmi.simplerest.core.Out;
import io.github.pzmi.simplerest.core.Route;
import io.github.pzmi.simplerest.core.Router;
import io.github.pzmi.simplerest.core.SimpleRouter;
import io.github.pzmi.websocket.WebsocketHandler;
import io.github.pzmi.websocket.WebsocketOut;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Collection;

@Configuration
public class ApplicationConfiguration implements WebSocketConfigurer {
    private Collection<Route> routes;

    public ApplicationConfiguration(Collection<Route> routes) {
        this.routes = routes;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler(router(wsOut()), wsOut()), "/reverse").setAllowedOrigins("*");
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }

    @Bean
    public Router router(Out out) {
        Router r = new SimpleRouter(out);
        routes.forEach(r::addRoute);
        return r;
    }

    @Bean
    public WebsocketOut wsOut() {
        return new WebsocketOut(objectMapper());
    }


    @Bean
    public WebsocketHandler wsHandler(Router router, WebsocketOut out) {
        return new WebsocketHandler(router, objectMapper(), out);
    }

    @Bean
    @ConditionalOnMissingBean
    CollectorRegistry metricRegistry() {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    ServletRegistrationBean registerPrometheusExporterServlet(CollectorRegistry metricRegistry) {
        return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/prometheus");
    }
}
