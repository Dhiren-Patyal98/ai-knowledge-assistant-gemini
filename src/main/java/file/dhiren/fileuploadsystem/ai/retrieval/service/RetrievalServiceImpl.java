package file.dhiren.fileuploadsystem.ai.retrieval.service;

import file.dhiren.fileuploadsystem.ai.embedding.service.EmbeddingService;
import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchResultDto;
import file.dhiren.fileuploadsystem.ai.retrieval.projection.SimilarChunkProjection;
import file.dhiren.fileuploadsystem.ai.retrieval.repository.RetrievalRepository;
import file.dhiren.fileuploadsystem.auth.entity.User;
import file.dhiren.fileuploadsystem.common.util.VectorUtil;
import file.dhiren.fileuploadsystem.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl implements RetrievalService {

    @Value("${retrieval.top-k}")
    private int topK;

    private final EmbeddingService embeddingService;
    private final RetrievalRepository retrievalRepository;
    private final CurrentUserService currentUserService;

    @Override
    public List<SearchResultDto> search(String question){

        log.info("Searching similar chunks for question: {}", question);

        User user = currentUserService.getCurrentUser();

        log.info("Searching documents for user {}", user.getEmail());


        float[] queryEmbedding = embeddingService.generateEmbedding(question);

        log.info("Query embedding dimension: {}", queryEmbedding.length);
        String queryVector  = VectorUtil.toPGVector(queryEmbedding);

        List<SimilarChunkProjection> chunks = retrievalRepository.findSimilarChunks(queryVector, user.getId(),topK);

        log.info("Retrieved {} similar chunks", chunks.size());

        return chunks.stream()
                .map(chunk -> SearchResultDto.builder()
                        .chunkId(chunk.getChunkId())
                        .distance(chunk.getDistance())
                        .chunkText(
                                chunk.getChunkText()
                                        .replaceAll("\\s+", " ")
                                        .trim())
                        .build())
                .toList();

    }
}
