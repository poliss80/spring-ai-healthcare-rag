# Spring AI Healthcare FAQ RAG (starter, steps 1 to 3)

A small Spring Boot project to learn Spring AI honestly: ChatClient,
vector retrieval, and QuestionAnswerAdvisor. Sample docs only, no real PHI.

## What it does

1. ChatClient talks to Azure OpenAI.
2. Sample docs in `src/main/resources/docs/` are loaded into a VectorStore at startup.
3. `QuestionAnswerAdvisor` retrieves the top 5 chunks and the model answers
   only from that context. If it is not in context, it says it does not know.

## Run it

Set these environment variables, then run with Maven:

```
AZURE_OPENAI_ENDPOINT=...
AZURE_OPENAI_API_KEY=...
AZURE_OPENAI_DEPLOYMENT=...
AZURE_OPENAI_EMBEDDING_DEPLOYMENT=...
./mvnw spring-boot:run
```

Ask a question:

```
curl -X POST http://localhost:8080/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "How can a patient reschedule an appointment?"}'
```

## Files

- `RagService.java` — ChatClient + QuestionAnswerAdvisor, top 5 retrieval
- `DocumentLoader.java` — loads sample docs into the VectorStore
- `AskController.java` — POST /ask
- `application.properties` — Azure OpenAI settings from environment variables only

## Next steps (not in this starter)

- Step 4: one tool calling method, for example document lookup by ID.
- Step 5: log tokens, latency, and retrieved chunk IDs per answer.
- Swap the simple vector store for pgvector or Azure AI Search for a
  production ready version.

## Interview line this earns

Built a Spring AI RAG service with ChatClient, QuestionAnswerAdvisor,
vector retrieval, and tool calling, served through Spring Boot REST APIs.
