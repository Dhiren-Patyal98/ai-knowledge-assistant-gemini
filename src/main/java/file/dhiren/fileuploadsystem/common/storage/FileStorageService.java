package file.dhiren.fileuploadsystem.common.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String storeFile(MultipartFile file) throws IOException;

    Resource loadFile(String fileName) throws IOException;

    void deleteFile(String fileName) throws IOException;
}
