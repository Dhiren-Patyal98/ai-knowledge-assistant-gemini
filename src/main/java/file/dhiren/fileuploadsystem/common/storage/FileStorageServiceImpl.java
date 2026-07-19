package file.dhiren.fileuploadsystem.common.storage;

import file.dhiren.fileuploadsystem.common.exception.FileNotFoundException;
import file.dhiren.fileuploadsystem.document.exception.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{

    @Value("${document.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) throws IOException
    {

        Path uploadPath = Paths.get(uploadDir);


        if(!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);

            log.info("Creating upload directory {}", uploadPath);
        }

        String originalName = file.getOriginalFilename();

        log.info("Storing file {}", originalName);

        String extension = "";

        if(originalName != null  && originalName.contains("."))
        {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }

        String uniqueFileName = UUID.randomUUID() + extension;

        log.info("Generated unique filename {}", uniqueFileName);

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(uniqueFileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        log.info("File stored successfully as {}", uniqueFileName);

        return uniqueFileName;
    }

    @Override
    public Resource loadFile(String fileName) throws IOException{

        log.info("Loading file {} from storage", fileName);

        Path filePath =  Paths.get(uploadDir).resolve(fileName).normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if(resource.exists() && resource.isReadable())
        {
            return resource;
        }

        throw new FileNotFoundException("File not found");
    }

    @Override
    public void deleteFile(String fileName) throws IOException {

        log.info("Deleting file {}", fileName);

        Path filePath = Paths.get(uploadDir).resolve(fileName);

        Files.deleteIfExists(filePath);

        log.info("Deleted file {}", fileName);
    }
}
