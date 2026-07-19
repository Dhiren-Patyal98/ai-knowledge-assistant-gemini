package file.dhiren.fileuploadsystem.document.controller;


import file.dhiren.fileuploadsystem.document.dto.DocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.DownloadDocumentResponse;
import file.dhiren.fileuploadsystem.document.dto.UploadDocumentResponse;
import file.dhiren.fileuploadsystem.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Documents", description = "Document Management APIs")
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;


    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Upload a document")
    @PostMapping("/upload")
    public ResponseEntity<UploadDocumentResponse> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "title", required = false) String title) throws IOException {
        System.out.println("CONTROLLER REACHED");
        return ResponseEntity.ok(documentService.upload(file,title));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "To get all the documents for the logged in user")
    @GetMapping("/getmydoc")
    public ResponseEntity<List<DocumentResponse>> getMyDocuments(){
        return ResponseEntity.ok(documentService.getMyDocuments());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Download document")
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id ) throws IOException {
        DownloadDocumentResponse response = documentService.downloadDocument(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                response.getOriginalFileName() +
                                "\"")
                .body(response.getResource());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long id)
    {
        return ResponseEntity.ok(documentService.getDocument(id));
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Delete document")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) throws IOException {
          documentService.deleteDocument(id);

          return ResponseEntity.noContent().build();
    }
}
