package file.dhiren.fileuploadsystem.kafka.producer;

import file.dhiren.fileuploadsystem.kafka.event.DocumentProcessingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentProcessingProducer {

    private final KafkaTemplate<String,DocumentProcessingEvent> kafkaTemplate;

    private static final String TOPIC = "document-processing";

    public void sendDocumentProcessingEvent(DocumentProcessingEvent event)
    {
        kafkaTemplate.send(TOPIC,event.getDocumentId().toString(),event);
    }
}
