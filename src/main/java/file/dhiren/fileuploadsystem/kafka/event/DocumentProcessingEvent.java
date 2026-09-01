package file.dhiren.fileuploadsystem.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProcessingEvent {

    private Long documentId;
    private Long userId;

}
