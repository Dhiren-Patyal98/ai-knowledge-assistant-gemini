package file.dhiren.fileuploadsystem.document.service;

import file.dhiren.fileuploadsystem.document.dto.DocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.DownloadDocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.UploadDocumentResponse;
import file.dhiren.fileuploadsystem.document.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;

public interface DocumentService {

    UploadDocumentResponse upload(MultipartFile file, String title) throws IOException;

    List<DocumentResponse> getMyDocuments();

    DocumentResponse getDocument(Long id);

    DownloadDocumentResponse downloadDocument(Long id) throws IOException;

    void deleteDocument(Long id) throws IOException;
}
