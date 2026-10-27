package com.example.healthrag;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Step 2: load every sample doc in classpath:docs/ into the VectorStore
 * at startup. Sample docs only, no real PHI.
 */
@Component
public class DocumentLoader implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        Resource[] docs = new PathMatchingResourcePatternResolver()
                .getResources("classpath:docs/*.md");
        List<Document> documents = java.util.Arrays.stream(docs)
                .map(resource -> {
                    try {
                        String text = new String(resource.getInputStream().readAllBytes(),
                                StandardCharsets.UTF_8);
                        return new Document(text);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to read " + resource.getFilename(), e);
                    }
                })
                .toList();
        vectorStore.add(documents);
        System.out.println("Loaded " + documents.size() + " sample docs into the vector store.");
    }
}
