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
Date: Thu, 05 Dec 2024 17:44:04 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "content": "Sure! Here's a random joke for you:\n\nWhy don't skeletons fight each other?  \nBecause they don’t have the guts! 😄",
        "errorMessage": null,
        "completionTokens": 29,
        "modelName": "openai/gpt-4o-2024-11-20",
        "promptTokens": 12,
        "responseTime": "1s893ms",
        "success": true,
        "totalTokens": 41
    },
    {
        "content": "What do you call a fish wearing a bowtie? So-fish-ticated!",
        "errorMessage": null,
        "completionTokens": 0,
        "modelName": "anthracite-org/magnum-v2-72b",
        "promptTokens": 0,
        "responseTime": "1s49ms",
        "success": true,
        "totalTokens": 0
    },
    {
        "content": "Why don't scientists trust atoms? Because they make up everything.",
        "errorMessage": null,
        "completionTokens": 14,
        "modelName": "qwen/qvq-72b-preview",
        "promptTokens": 41,
        "responseTime": "913ms",
        "success": true,
        "totalTokens": 55
    },
    {
        "content": "Why don't eggs tell jokes? They'd crack each other up!",
        "errorMessage": null,
        "completionTokens": 15,
        "modelName": "x-ai/grok-2-1212",
        "promptTokens": 11,
        "responseTime": "666ms",
        "success": true,
        "totalTokens": 26
    },
    {
        "content": "Here's a random joke for you:\n\nWhy don't scientists trust atoms?\n\nBecause they make up everything!",
        "errorMessage": null,
        "completionTokens": 27,
        "modelName": "anthropic/claude-3.5-haiku-20241022",
        "promptTokens": 12,
        "responseTime": "1s390ms",
        "success": true,
        "totalTokens": 39
    },
    {
        "content": "Here's a random joke for you:\n\n**Why don't sharks eat clowns?**\n\nBecause they taste funny![1]",
        "errorMessage": null,
        "completionTokens": 25,
        "modelName": "perplexity/llama-3.1-sonar-huge-128k-online",
        "promptTokens": 5,
        "responseTime": "3s108ms",
        "success": true,
        "totalTokens": 30
    },
    {
        "content": "Here's one:\n\nWhat do you call a fake noodle?\n\nAn impasta!",
        "errorMessage": null,
        "completionTokens": 17,
        "modelName": "meta-llama/llama-3.3-70b-instruct",
        "promptTokens": 15,
        "responseTime": "1s703ms",
        "success": true,
        "totalTokens": 32
    },
    {
        "content": "What do you call fake spaghetti? An impasta",
        "errorMessage": null,
        "completionTokens": 13,
        "modelName": "mistralai/mistral-large-2411",
        "promptTokens": 8,
        "responseTime": "725ms",
        "success": true,
        "totalTokens": 21
    },
    {
        "content": " Why did the chicken cross the playground? To get to the other slide!",
        "errorMessage": null,
        "completionTokens": 15,
        "modelName": "pygmalionai/mythalion-13b",
        "promptTokens": 30,
        "responseTime": "5s288ms",
        "success": true,
        "totalTokens": 45
    },
    {
        "content": "Sure! Here’s a random joke for you:\n\nWhy don’t skeletons fight each other?  \nBecause they don’t have the guts! 💀😂",
        "errorMessage": null,
        "completionTokens": 34,
        "modelName": "deepseek/deepseek-chat",
        "promptTokens": 8,
        "responseTime": "4s477ms",
        "success": true,
        "totalTokens": 42
    },
    {
        "content": "Okay, here's a random one:\n\nWhy don't scientists trust atoms? \n\nBecause they make up everything!\n",
        "errorMessage": null,
        "completionTokens": 27,
        "modelName": "google/gemini-2.0-flash-exp:free",
        "promptTokens": 6,
        "responseTime": "1s94ms",
        "success": true,
        "totalTokens": 33
    },
    {
        "content": "Of course! Here's a light-hearted joke for you:\n\nWhy did the scarecrow win an award?\n\nBecause he was outstanding in his field! \n\nHope that brought a smile to your face!",
        "errorMessage": null,
        "completionTokens": 41,
        "modelName": "amazon/nova-pro-v1",
        "promptTokens": 5,
        "responseTime": "1s83ms",
        "success": true,
        "totalTokens": 46
    }
]

```
