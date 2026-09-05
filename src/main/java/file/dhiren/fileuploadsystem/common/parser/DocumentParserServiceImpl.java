package file.dhiren.fileuploadsystem.common.parser;

import file.dhiren.fileuploadsystem.common.exception.DocumentParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class DocumentParserServiceImpl implements DocumentParserService{


    @Override
    public String extractText(InputStream inputStream) {
        try {

            Tika tika = new Tika();

            String text = tika.parseToString(inputStream);

            log.info("Successfully extracted {} characters", text.length());

            return text;

        } catch (
                IOException | TikaException e) {

            log.error("Failed to extract text from {}", e);

            throw new DocumentParsingException(
                    "Unable to extract document text",
                    e
            );
        }
    }

}
