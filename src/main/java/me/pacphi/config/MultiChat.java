package me.pacphi.config;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientAsync;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.client.okhttp.OpenAIOkHttpClientAsync;
import io.micrometer.observation.ObservationRegistry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAiCommonProperties;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MultiChat {

    @Bean
    public Map<String, ChatClient> chatClients(
            OpenAiCommonProperties connectionProperties,
            OpenAiChatProperties chatProperties,
            MultiChatProperties multiChatProperties
    ) {
        String baseUrl = chatProperties.getBaseUrl() != null ? chatProperties.getBaseUrl() : connectionProperties.getBaseUrl();

        String apiKey = connectionProperties.getApiKey();
        if (connectionProperties.getApiKey().equalsIgnoreCase("redundant") && StringUtils.isNotBlank(chatProperties.getApiKey())) {
            apiKey = chatProperties.getApiKey();
        }

        // Both sync and async clients must be provided; if only sync is given, OpenAiChatModel
        // internally calls OpenAiSetup.setupAsyncClient() which requires credentials from
        // Spring properties — bypassing the programmatic key we set here.
        OpenAIClient openAiClient = OpenAIOkHttpClient.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .putHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate")
                .timeout(Duration.ofMinutes(10))
                .maxRetries(connectionProperties.getMaxRetries())
                .build();

        OpenAIClientAsync openAiClientAsync = OpenAIOkHttpClientAsync.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .putHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate")
                .timeout(Duration.ofMinutes(10))
                .maxRetries(connectionProperties.getMaxRetries())
                .build();

        ObservationRegistry observationRegistry = ObservationRegistry.NOOP;

        return multiChatProperties.getOptions().getModels().stream().collect(
                Collectors.toMap(
                        model -> model,
                        model -> {
                            OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                                    .openAiClient(openAiClient)
                                    .openAiClientAsync(openAiClientAsync)
                                    .options(OpenAiChatOptions.builder().model(model).build())
                                    .observationRegistry(observationRegistry)
                                    .build();
                            return ChatClient.builder(openAiChatModel)
                                    .defaultAdvisors(new SimpleLoggerAdvisor())
                                    .build();
                        }
                )
        );
    }

}
