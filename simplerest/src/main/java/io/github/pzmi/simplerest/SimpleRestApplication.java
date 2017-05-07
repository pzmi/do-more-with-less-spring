package io.github.pzmi.simplerest;

import hystrix.HystrixPrometheusMetricsPublisher;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class SimpleRestApplication {

	public static void main(String[] args) {
		HystrixPrometheusMetricsPublisher.register("simplerest");
		SpringApplication.run(SimpleRestApplication.class, args);
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
