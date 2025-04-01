package me.pacphi.config;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.time.Duration;

@Configuration
public class Http {

    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout((int) Duration.ofMinutes(10).toMillis());
            factory.setReadTimeout((int) Duration.ofMinutes(10).toMillis());
            restClientBuilder.defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
            restClientBuilder.requestFactory(factory);
        };
    }
}
