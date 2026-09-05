package file.dhiren.fileuploadsystem.kafka.consumer;

import file.dhiren.fileuploadsystem.document.service.DocumentProcessingService;
import file.dhiren.fileuploadsystem.kafka.event.DocumentProcessingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentProcessingConsumer {

    private final DocumentProcessingService documentProcessingService;

    @KafkaListener(topics = "document-processing", groupId = "file-upload-group")
    public void consume(DocumentProcessingEvent event)
    {
        System.out.println("Received document processing event" + event.getDocumentId());

        documentProcessingService.processDocument(event.getDocumentId());
    }
}
