package file.dhiren.fileuploadsystem.common.parser;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface DocumentParserService {

    String extractText(InputStream inputStream);
}
