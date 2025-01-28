package me.pacphi.config;

import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.model.function.FunctionCallbackResolver;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.ResponseErrorHandler;
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
            FunctionCallbackResolver functionCallbackResolver,
            ResponseErrorHandler responseErrorHandler,
            MultiChatProperties multiChatProperties
    ) {
        RestClient.Builder restClientBuilder = RestClient.builder()
                .defaultHeaders(headers -> headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));

        OpenAiApi openAiApi = new OpenAiApi(
                chatProperties.getBaseUrl() != null ? chatProperties.getBaseUrl() : connectionProperties.getBaseUrl(),
                chatProperties.getApiKey() != null ? chatProperties.getApiKey() : connectionProperties.getApiKey(),
                restClientBuilder,
                webClientBuilder,
                responseErrorHandler
        );

        return multiChatProperties.getOptions().getModels().stream().collect(
                Collectors.toMap(
                    model -> model,
                    model -> {
                        OpenAiChatOptions chatOptions = OpenAiChatOptions.fromOptions(chatProperties.getOptions());
                        chatOptions.setModel(model);
                        OpenAiChatModel openAiChatModel = new OpenAiChatModel(
                                openAiApi,
                                chatOptions,
                                functionCallbackResolver,
                                retryTemplate
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