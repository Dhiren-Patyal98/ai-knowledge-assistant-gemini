package file.dhiren.fileuploadsystem.document.repository;

import file.dhiren.fileuploadsystem.document.entity.Document;
import file.dhiren.fileuploadsystem.document.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentChunkRepository extends JpaRepository<DocumentChunk,Long> {

    List<DocumentChunk> findByDocument(Document document);

}
