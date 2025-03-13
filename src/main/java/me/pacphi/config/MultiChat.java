package me.pacphi.config;

import io.micrometer.observation.ObservationRegistry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MultiChat {

    @Bean
    public Map<String, ChatClient> chatClients(
            OpenAiConnectionProperties connectionProperties,
            OpenAiChatProperties chatProperties,
            WebClient.Builder webClientBuilder,
            RetryTemplate retryTemplate,
            MultiChatProperties multiChatProperties
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");

        RestClient.Builder restClientBuilder = RestClient.builder()
                .defaultHeaders(h -> h.addAll(headers));

        String apiKey = connectionProperties.getApiKey();
        if (connectionProperties.getApiKey().equalsIgnoreCase("redundant") && StringUtils.isNotBlank(chatProperties.getApiKey())) {
            apiKey = chatProperties.getApiKey();
        }

        OpenAiApi openAiApi = new OpenAiApi(
                chatProperties.getBaseUrl() != null ? chatProperties.getBaseUrl() : connectionProperties.getBaseUrl(),
                new SimpleApiKey(apiKey),
                headers,
                "/v1/chat/completions",
                "/v1/embeddings",
                restClientBuilder,
                webClientBuilder,
                RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER
        );

        ObservationRegistry observationRegistry = ObservationRegistry.NOOP;

        return multiChatProperties.getOptions().getModels().stream().collect(
                Collectors.toMap(
                    model -> model,
                    model -> {
                        OpenAiChatOptions chatOptions = OpenAiChatOptions.fromOptions(chatProperties.getOptions());
                        chatOptions.setModel(model);
                        OpenAiChatModel openAiChatModel = new OpenAiChatModel(
                                openAiApi,
                                chatProperties.getOptions(),
                                DefaultToolCallingManager.builder().observationRegistry(observationRegistry).build(),
                                retryTemplate,
                                observationRegistry
                        );
                        // Create ChatClient with similar configuration to original service
                        return ChatClient.builder(openAiChatModel)
                                .defaultAdvisors(
                                        new SimpleLoggerAdvisor())
                                .build();
                    }
                )
        );
    }

}