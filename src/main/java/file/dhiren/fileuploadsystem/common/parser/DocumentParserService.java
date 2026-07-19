package file.dhiren.fileuploadsystem.common.parser;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentParserService {

    String extractText(MultipartFile file);
}
