package file.dhiren.fileuploadsystem.kafka.Controller;

import file.dhiren.fileuploadsystem.document.entity.Document;
import file.dhiren.fileuploadsystem.kafka.event.DocumentProcessingEvent;
import file.dhiren.fileuploadsystem.kafka.producer.DocumentProcessingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaTestController {

    private  final DocumentProcessingProducer producer;

    @GetMapping("/test")
    public String testKafka()
    {
        DocumentProcessingEvent event = new DocumentProcessingEvent(1L,100L);

        producer.sendDocumentProcessingEvent(event);

        return "Kafka event sent successfully";
    }

}
