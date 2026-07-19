package file.dhiren.fileuploadsystem.document.service;


import com.pgvector.PGvector;
import file.dhiren.fileuploadsystem.ai.embedding.entity.Embedding;
import file.dhiren.fileuploadsystem.ai.embedding.repository.EmbeddingRepository;
import file.dhiren.fileuploadsystem.ai.embedding.service.EmbeddingService;
import file.dhiren.fileuploadsystem.auth.entity.User;
import file.dhiren.fileuploadsystem.common.chunking.ChunkingService;
import file.dhiren.fileuploadsystem.common.parser.DocumentParserService;
import file.dhiren.fileuploadsystem.common.storage.FileStorageService;
import file.dhiren.fileuploadsystem.common.util.FileValidationUtil;
import file.dhiren.fileuploadsystem.document.dto.DocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.DownloadDocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.UploadDocumentResponse;
import file.dhiren.fileuploadsystem.document.entity.Document;
import file.dhiren.fileuploadsystem.document.entity.DocumentChunk;
import file.dhiren.fileuploadsystem.document.entity.DocumentStatus;
import file.dhiren.fileuploadsystem.document.exception.DocumentNotFoundException;
import file.dhiren.fileuploadsystem.document.mapper.DocumentMapper;
import file.dhiren.fileuploadsystem.document.repository.DocumentChunkRepository;
import file.dhiren.fileuploadsystem.document.repository.DocumentRepository;
import file.dhiren.fileuploadsystem.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    @Value("${document.upload-dir}")
    private String uploadDir;

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    private final CurrentUserService currentUserService;

    private final FileValidationUtil validate;

    private final FileStorageService fileStorageService;

    private final DocumentParserService documentParserService;

    private final ChunkingService chunkingService;

    private final DocumentChunkRepository documentChunkRepository;

    private final EmbeddingService embeddingService;

    private final EmbeddingRepository embeddingRepository;



    @Transactional
    @Override
    public UploadDocumentResponse upload(MultipartFile file, String title) throws IOException {

        log.info("Starting upload for file {}", file.getOriginalFilename());

        validate.validate(file);

        log.debug("File validation completed");

        User user = currentUserService.getCurrentUser();

        log.info("Current user: {}", user.getEmail());


        String storedFileName  = fileStorageService.storeFile(file);

        log.info("File stored successfully as {}", storedFileName );


        String extractedText = documentParserService.extractText(file);

        log.info("Extracted Text:\n{}", extractedText);


        List<String> chunks = chunkingService.chunkText(extractedText);

        log.info("Generated {} chunks", chunks.size());


        Document document = Document.builder()
                .user(user)
                .title(title == null || title.isBlank() ? file.getOriginalFilename() : title)
                .fileName(storedFileName )
                .filePath(uploadDir + "/" + storedFileName )
                .fileSize(file.getSize())
                .originalFileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .status(DocumentStatus.UPLOADED)
                .build();


        Document savedDocument = documentRepository.save(document);


        log.info(
                "Document {} uploaded successfully by {}",
                savedDocument.getId(),
                user.getEmail()
        );

        for(int index = 0; index < chunks.size() ;index++)
        {
            String chunkText = chunks.get(index);

            DocumentChunk documentChunk = DocumentChunk.builder()
                    .document(savedDocument)
                    .chunkIndex(index)
                    .chunkText(chunkText)
                    .tokenCount(chunkText.length())
                    .build();

            DocumentChunk savedChunk = documentChunkRepository.save(documentChunk);

            float[] embeddingVector = embeddingService.generateEmbedding(chunkText);

//            String embeddingJson = objectMapper.writeValueAsString(embeddingVector);

            Embedding embedding = Embedding.builder()
                    .chunkId(savedChunk)
                    .embedding(embeddingVector)
                    .build();

            embeddingRepository.save(embedding);

            log.info(
                    "Generated {}-dimension embedding for chunk {}",
                    embeddingVector.length,
                    index
            );

        }

//        List<DocumentChunk> documentChunks = IntStream.range(0, chunks.size())
//                .mapToObj(index -> DocumentChunk.builder()
//                        .chunkIndex(index)
//                        .document(savedDocument)
//                        .chunkText(chunks.get(index))
//                        .tokenCount(chunks.get(index).length())
//                        .build()).toList();
//
//
//        documentChunkRepository.saveAll(documentChunks);

        log.info(
                "Document {} uploaded successfully by {}",
                savedDocument.getId(),
                user.getEmail()
        );

        return UploadDocumentResponse.builder()
                .message("Document uploaded successfully.")
                .document(documentMapper.toResponse(savedDocument))
                .build();
    }

    @Override
    public List<DocumentResponse> getMyDocuments() {


        User user = currentUserService.getCurrentUser();

        log.info("Current user: {}", user.getEmail());

        log.info("Fetching documents for {}", user.getEmail());

        List<Document> documents = documentRepository.findByUser(user);

        log.info("{} documents found", documents.size());

        return documents.stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    @Override
    public DocumentResponse getDocument(Long id) {

        log.info("Fetching document {}", id);

        User user = currentUserService.getCurrentUser();

        log.info(
                "User {} requested download for document {}",
                user.getEmail(),
                id
        );


        Document document = documentRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document not found"));

        log.info("Document {} retrieved successfully", id);

        return documentMapper.toResponse(document);
    }

    @Override
    public DownloadDocumentResponse downloadDocument(Long id) throws IOException {

        log.info("Download requested for document {}", id);

        User user = currentUserService.getCurrentUser();

        log.info("Current user: {}", user.getEmail());

        Document document = documentRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document not found"));

        log.info(
                "Document {} ({}) is ready for download",
                document.getId(),
                document.getOriginalFileName()
        );
        ;

        Resource resource = fileStorageService.loadFile(document.getFileName());

        return DownloadDocumentResponse.builder()
                .resource(resource)
                .originalFileName(document.getOriginalFileName())
                .build();
    }


    @Transactional
    @Override
    public void deleteDocument(Long id) throws IOException {

        log.info("Delete requested for document {}", id);

        User user = currentUserService.getCurrentUser();

        log.info("Current user: {}", user.getEmail());


        Document document = documentRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document not found"));

        fileStorageService.deleteFile(document.getFileName());

        documentRepository.delete(document);

        log.info("Document {} deleted successfully", id);
    }
}
