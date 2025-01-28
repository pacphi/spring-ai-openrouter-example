package me.pacphi.domain.chat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ChatRequest(
        @NotNull(message = "Inquiry cannot be null")
        @NotEmpty(message = "Inquiry cannot be empty")
        String inquiry) {
}
