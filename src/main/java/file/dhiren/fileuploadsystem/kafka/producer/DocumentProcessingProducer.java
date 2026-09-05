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
        String key = event.getDocumentId().toString();

        kafkaTemplate.send(TOPIC,key,event).whenComplete((result,exception)->{
            if(exception != null)
            {
                System.out.println( "Failed to send Kafka event: " + exception.getMessage());
            }
            else
            {
                System.out.println("Kafka event sent successfully");
            }
        });
    }
}
