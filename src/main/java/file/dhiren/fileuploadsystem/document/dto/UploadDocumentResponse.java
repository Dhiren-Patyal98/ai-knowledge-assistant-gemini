package file.dhiren.fileuploadsystem.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadDocumentResponse {

    private String message;

    private DocumentResponse document;
}
