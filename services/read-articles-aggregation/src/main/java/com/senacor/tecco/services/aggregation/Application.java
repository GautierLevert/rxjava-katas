package com.senacor.tecco.services.aggregation;

import com.senacor.tecco.reactive.services.CountService;
import com.senacor.tecco.reactive.services.RatingService;
import com.senacor.tecco.reactive.services.WikiService;
import com.senacor.tecco.reactive.util.DelayFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by Daniel Heinrich on 06/03/2017.
 */
@SpringBootApplication
public class Application {

    @Bean
    public WikiLoaderService wikiLoaderService(@Value("${services.wikiloader}") String uri) {
        return new WikiLoaderService(WebClient.create(uri));
    }

    @Bean
    public MetricsService metricsService(@Value("${services.metrics}") String uri) {
        return new MetricsService(WebClient.create(uri));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
