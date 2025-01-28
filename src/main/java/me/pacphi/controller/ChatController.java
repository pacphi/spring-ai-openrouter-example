package me.pacphi.controller;

import jakarta.validation.Valid;
import me.pacphi.domain.chat.ChatRequest;
import me.pacphi.service.chat.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/api/chat")
    public ResponseEntity<String> inquire(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.respond(request.inquiry()));
    }

}
