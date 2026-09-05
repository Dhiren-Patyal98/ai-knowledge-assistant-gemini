package file.dhiren.fileuploadsystem.document.service;

import file.dhiren.fileuploadsystem.ai.embedding.entity.Embedding;
import file.dhiren.fileuploadsystem.ai.embedding.repository.EmbeddingRepository;
import file.dhiren.fileuploadsystem.ai.embedding.service.EmbeddingService;
import file.dhiren.fileuploadsystem.common.chunking.ChunkingService;
import file.dhiren.fileuploadsystem.common.parser.DocumentParserService;
import file.dhiren.fileuploadsystem.common.storage.FileStorageService;
import file.dhiren.fileuploadsystem.document.entity.Document;
import file.dhiren.fileuploadsystem.document.entity.DocumentChunk;
import file.dhiren.fileuploadsystem.document.entity.DocumentProcessingStatus;
import file.dhiren.fileuploadsystem.document.repository.DocumentChunkRepository;
import file.dhiren.fileuploadsystem.document.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService{

    private final DocumentRepository documentRepository;

    private final FileStorageService fileStorageService;

    private final DocumentParserService documentParserService;

    private final ChunkingService chunkingService;

    private final DocumentChunkRepository documentChunkRepository;

    private final EmbeddingService embeddingService;

    private final EmbeddingRepository embeddingRepository;


    @Override
    @Transactional
    public void processDocument(Long documentId)
    {
        log.info("Starting the processing of the document {}",documentId);

        Document document = documentRepository.findById(documentId).
                orElseThrow(()-> new RuntimeException("Document not found " + documentId));

        try{


            document.setProcessingStatus(DocumentProcessingStatus.PROCESSING);

            documentRepository.save(document);

            Resource resource = fileStorageService.loadFile(document.getFileName());

            String extractText;

            try(InputStream inputStream = resource.getInputStream()) {

                extractText = documentParserService.extractText(inputStream);

            }

            log.info("Extracted {} characters from Document {}",extractText.length(),documentId);

            List<String> chunks = chunkingService.chunkText(extractText);

            log.info("Generated {} chunks for document {}",chunks.size(),documentId);

            for(int index = 0 ; index < chunks.size() ; index++ )
            {
                String chunkText = chunks.get(index);

                DocumentChunk documentChunk = DocumentChunk.builder()
                        .chunkIndex(index)
                        .document(document)
                        .chunkText(chunkText)
                        .tokenCount(chunkText.length())
                        .build();

                DocumentChunk savedChunk = documentChunkRepository.save(documentChunk);

                float[] embeddingVector = embeddingService.generateEmbedding(chunkText);

                Embedding embedding = Embedding.builder()
                        .chunkId(savedChunk)
                        .embedding(embeddingVector)
                        .build();

                embeddingRepository.save(embedding);

                log.info("Generated {} - dimension embedding for the chunk {}", embeddingVector.length,index);


            }

            document.setProcessingStatus(DocumentProcessingStatus.COMPLETED);

            documentRepository.save(document);

            log.info("Document {} processing completed successfully.", documentId);

        }catch (Exception e)
        {
            log.error("Document {} processing failed",documentId,e);

            document.setProcessingStatus(DocumentProcessingStatus.FAILED);

            document.setErrorMessage(e.getMessage());

            documentRepository.save(document);

            throw new RuntimeException("Document processing failed",e);

        }
    }


}
