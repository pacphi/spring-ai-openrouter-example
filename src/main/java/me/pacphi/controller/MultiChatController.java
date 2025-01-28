package me.pacphi.controller;

import jakarta.validation.Valid;
import me.pacphi.domain.chat.ChatRequest;
import me.pacphi.domain.chat.MultiChatResponse;
import me.pacphi.service.chat.MultiChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MultiChatController {

    private final MultiChatService multiChatService;

    public MultiChatController(MultiChatService multiChatService) {
        this.multiChatService = multiChatService;
    }

    @PostMapping("/api/multichat")
    public ResponseEntity<List<MultiChatResponse>> inquire(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(multiChatService.respond(request.inquiry()));
    }
}
