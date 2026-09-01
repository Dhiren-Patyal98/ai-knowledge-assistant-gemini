package file.dhiren.fileuploadsystem.kafka.consumer;

import file.dhiren.fileuploadsystem.kafka.event.DocumentProcessingEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class DocumentProcessingConsumer {

    @KafkaListener(topics = "document-processing", groupId = "file-upload-group")
    public void consume(DocumentProcessingEvent event)
    {
        System.out.println("Received document processing event" + event);
    }
}
