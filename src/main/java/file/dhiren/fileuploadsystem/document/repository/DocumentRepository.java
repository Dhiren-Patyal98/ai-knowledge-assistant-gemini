package file.dhiren.fileuploadsystem.document.repository;

import file.dhiren.fileuploadsystem.auth.entity.User;
import file.dhiren.fileuploadsystem.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    List<Document> findByUser(User user);

   Optional<Document> findByIdAndUserId(Long Id, Long userId);
}
