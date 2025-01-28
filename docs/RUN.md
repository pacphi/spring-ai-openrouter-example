# Spring AI Open Router Example

## How to Run with Gradle

Runs a version of this application that is compatible for use with [OpenRouter](https://openrouter.ai/docs/quick-start).  You will need to [obtain an API key](https://openrouter.ai/settings/keys).

Before launching the app:

* Create a `config` folder which would be a sibling of the `build` folder.  Create a file named `creds.yml` inside that folder.  Add your own API key into that file.

```yaml
spring:
  ai:
    openai:
      api-key: {REDACTED-1}
```
> Replace `{REDACTED-1}` above with your OpenRouter API key.

Open a terminal shell and execute

```bash
❯ ./gradlew bootRun
```

