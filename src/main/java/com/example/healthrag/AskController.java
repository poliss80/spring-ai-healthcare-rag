package com.example.healthrag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskController {

    private final RagService ragService;

    public AskController(RagService ragService) {
        this.ragService = ragService;
    }

    public record AskRequest(String question) {
    }

    public record AskResponse(String answer) {
    }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {
        return new AskResponse(ragService.ask(request.question()));
    }
}
