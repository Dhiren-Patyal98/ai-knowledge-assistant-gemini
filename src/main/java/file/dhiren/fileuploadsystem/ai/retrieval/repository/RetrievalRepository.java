package file.dhiren.fileuploadsystem.ai.retrieval.repository;

import file.dhiren.fileuploadsystem.ai.embedding.entity.Embedding;
import file.dhiren.fileuploadsystem.ai.retrieval.projection.SimilarChunkProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetrievalRepository extends Repository<Embedding,Long> {

    @Query(value = """
        SELECT
            dc.id AS chunkId,
            dc.chunk_text AS chunkText,
            e.embedding <=> CAST(:queryVector AS vector) AS distance
        FROM embedding e
        JOIN document_chunk dc
            ON e.chunk_id = dc.id
        JOIN document d
            ON dc.document_id = d.id
        WHERE d.user_id = :userId
        ORDER BY e.embedding <=> CAST(:queryVector AS vector)
        LIMIT :limit
        """, nativeQuery = true)
    List<SimilarChunkProjection> findSimilarChunks(
            @Param("queryVector") String queryVector,
            @Param("userId") Long userId,
            @Param("limit") int limit
    );
}
