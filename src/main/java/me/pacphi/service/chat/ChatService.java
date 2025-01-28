package me.pacphi.service.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatModel model) {
        this.chatClient = ChatClient.builder(model)
        		.defaultAdvisors(
        				new SimpleLoggerAdvisor())
        		.build();
    }

    public String respond(String inquiry) {
        return chatClient
                .prompt()
                .user(inquiry)
                .call()
                .content();
    }
}
