package com.example.healthrag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

/**
 * Steps 1 to 3:
 * 1. ChatClient talking to Azure OpenAI.
 * 2. VectorStore loaded from sample docs at startup (see DocumentLoader).
 * 3. QuestionAnswerAdvisor: retrieve top 5 chunks, answer only from context.
 */
@Service
public class RagService {

    private final ChatClient chatClient;

    public RagService(ChatClient.Builder builder, VectorStore vectorStore) {
        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().topK(5).build())
                .build();
        this.chatClient = builder
                .defaultSystem("""
                        Answer only from the retrieved context.
                        If the answer is not in the context, say you do not know.
                        Do not invent policies, dates, or numbers.
                        """)
                .defaultAdvisors(advisor)
                .build();
    }

    public String ask(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
