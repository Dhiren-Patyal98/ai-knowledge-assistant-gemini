package file.dhiren.fileuploadsystem.ai.embedding.controller;

import file.dhiren.fileuploadsystem.ai.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/embedding")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @GetMapping("/test")
    public List<Float> testEmbedding() {

        return null;

    }

}
