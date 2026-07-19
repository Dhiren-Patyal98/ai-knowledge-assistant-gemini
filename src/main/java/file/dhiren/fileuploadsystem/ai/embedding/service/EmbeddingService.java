package file.dhiren.fileuploadsystem.ai.embedding.service;

import com.pgvector.PGvector;

import java.util.List;



public interface EmbeddingService {

    float[] generateEmbedding(String Text);
}
