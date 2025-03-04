package me.pacphi.domain.chat;

import org.springframework.ai.chat.metadata.Usage;

import java.time.Duration;

public record MultiChatResponse(
        String modelName,
        String content,
        boolean success,
        String errorMessage,
        Integer promptTokens,
        Integer totalTokens,
        String responseTime
) {
    private static String getFormattedResponseTime(long responseTimeMillis) {
        Duration duration = Duration.ofMillis(responseTimeMillis);

        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();

        StringBuilder formatted = new StringBuilder();

        if (hours > 0) formatted.append(hours).append("h");
        if (minutes > 0) formatted.append(minutes).append("m");
        if (seconds > 0) formatted.append(seconds).append("s");
        if (millis > 0) formatted.append(millis).append("ms");

        return !formatted.isEmpty() ? formatted.toString() : "0ms";
    }

    public static MultiChatResponse success(String model, String response, Usage usage, long responseTime) {
        return new MultiChatResponse(model, response, true, null, usage.getPromptTokens(), usage.getTotalTokens(), getFormattedResponseTime(responseTime));
    }

    public static MultiChatResponse failure(String model, String error) {
        return new MultiChatResponse(model, null, false, error, null, null, null);
    }
}