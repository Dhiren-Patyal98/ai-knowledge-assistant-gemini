package file.dhiren.fileuploadsystem.ai.embedding.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService{

    private final EmbeddingModel embeddingModel;

    @Override
    public float[] generateEmbedding(String text)
    {



       float[] embedding = embeddingModel.embed(text);

       log.info("Generated embedding with {} dimensions",embedding.length);

        return embedding;


    }
}
