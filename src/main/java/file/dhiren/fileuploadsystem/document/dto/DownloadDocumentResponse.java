package file.dhiren.fileuploadsystem.document.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Builder
@Getter
public class DownloadDocumentResponse {

    private Resource resource;

    private String originalFileName;
}