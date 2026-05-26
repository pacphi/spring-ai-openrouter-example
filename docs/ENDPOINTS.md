# # Spring AI Open Router Example

## Endpoints

### Chat

Chat with a bot.  Ask a question, get a response.

```python
POST /api/chat
```

**Sample interaction**

```bash
❯ http POST http://localhost:8080/api/chat \
  Content-Type:application/json \
  inquiry="Tell me a random joke"
```

### Multichat

Chat with a bot that can delegate your request to multiple LLMs.  Ask a question, get multiple responses from various pre-configured models.
Consult `spring.ai.openrouter.chat.options.models` in [application.yml](../src/main/resources/application.yml) for models participating in each request.

```python
POST /api/multichat
```
**Sample interaction**

```bash
❯ http POST http://localhost:8080/api/multichat \
  Content-Type:application/json \
  inquiry="Tell me a random joke"
  
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Mon, 26 May 2026 22:30:00 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "content": "Why don’t skeletons fight each other? Because they don’t have the guts!",
        "errorMessage": null,
        "completionTokens": 18,
        "modelName": "qwen/qwen3.7-max",
        "promptTokens": 12,
        "responseTime": "1s210ms",
        "success": true,
        "totalTokens": 30
    },
    {
        "content": "Here’s a random joke for you:\n\nWhy don’t scientists trust atoms?\n\nBecause they make up everything!",
        "errorMessage": null,
        "completionTokens": 27,
        "modelName": "anthropic/claude-haiku-latest",
        "promptTokens": 12,
        "responseTime": "1s390ms",
        "success": true,
        "totalTokens": 39
    },
    {
        "content": "Here’s one:\n\nWhat do you call a fake noodle?\n\nAn impasta!",
        "errorMessage": null,
        "completionTokens": 17,
        "modelName": "meta-llama/llama-3.3-70b-instruct",
        "promptTokens": 15,
        "responseTime": "1s703ms",
        "success": true,
        "totalTokens": 32
    },
    {
        "content": "Sure! Here’s a random joke:\n\nWhy don’t skeletons fight each other? Because they don’t have the guts!",
        "errorMessage": null,
        "completionTokens": 24,
        "modelName": "deepseek/deepseek-chat",
        "promptTokens": 8,
        "responseTime": "4s477ms",
        "success": true,
        "totalTokens": 32
    },
    {
        "content": "Sure! Here’s a random joke for you:\n\nWhy don’t skeletons fight each other?  \nBecause they don’t have the guts!",
        "errorMessage": null,
        "completionTokens": 29,
        "modelName": "openai/gpt-4o-2024-11-20",
        "promptTokens": 12,
        "responseTime": "1s893ms",
        "success": true,
        "totalTokens": 41
    },
    {
        "content": "Of course! Here’s a light-hearted joke for you:\n\nWhy did the scarecrow win an award?\n\nBecause he was outstanding in his field!",
        "errorMessage": null,
        "completionTokens": 34,
        "modelName": "amazon/nova-pro-v1",
        "promptTokens": 5,
        "responseTime": "1s83ms",
        "success": true,
        "totalTokens": 39
    },
    {
        "content": "Okay, here’s a random one:\n\nWhy don’t scientists trust atoms?\n\nBecause they make up everything!",
        "errorMessage": null,
        "completionTokens": 22,
        "modelName": "google/gemini-flash-latest",
        "promptTokens": 6,
        "responseTime": "988ms",
        "success": true,
        "totalTokens": 28
    },
    {
        "content": "What do you call fake spaghetti? An impasta!",
        "errorMessage": null,
        "completionTokens": 11,
        "modelName": "mistralai/ministral-3b-2512",
        "promptTokens": 8,
        "responseTime": "612ms",
        "success": true,
        "totalTokens": 19
    }
]

```
