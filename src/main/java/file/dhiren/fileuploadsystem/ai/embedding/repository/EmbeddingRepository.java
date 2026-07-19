package file.dhiren.fileuploadsystem.ai.embedding.repository;

import file.dhiren.fileuploadsystem.document.entity.DocumentChunk;
import file.dhiren.fileuploadsystem.ai.embedding.entity.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding,Long> {

    List<Embedding> findByChunkId(DocumentChunk documentChunk);

}
