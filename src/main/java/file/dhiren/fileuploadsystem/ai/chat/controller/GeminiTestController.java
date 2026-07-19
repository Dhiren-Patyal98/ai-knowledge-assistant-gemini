package file.dhiren.fileuploadsystem.ai.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gemini")
@RequiredArgsConstructor
public class GeminiTestController {

    private final ChatClient chatClient;


    @Value("${spring.ai.google.genai.api-key}")
    private String apiKey;

    @GetMapping("/test-key")
    public String testt() {
        return apiKey;
    }

    @GetMapping
    public String test() {

        try {

            return chatClient.prompt()
                    .user("Say Hello from Gemini")
                    .call()
                    .content();

        } catch (Exception e) {

            e.printStackTrace();

            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                System.out.println("CAUSE: " + t.getClass().getName());
                System.out.println("MESSAGE: " + t.getMessage());
            }

            return e.toString();
        }
    }
}