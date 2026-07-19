package file.dhiren.fileuploadsystem.document.mapper;


import file.dhiren.fileuploadsystem.document.dto.DocumentResponse;
import file.dhiren.fileuploadsystem.document.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponse toResponse(Document document)
    {
        return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .fileName(document.getFileName())
                .fileSize(document.getFileSize())
                .contentType(document.getContentType())
                .status(document.getStatus().name())
                .uploadedAt(document.getUploadedAt())
                .build();
    }

}
