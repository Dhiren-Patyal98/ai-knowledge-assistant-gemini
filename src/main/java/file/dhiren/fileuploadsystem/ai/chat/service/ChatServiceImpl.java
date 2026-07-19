package file.dhiren.fileuploadsystem.ai.chat.service;

import file.dhiren.fileuploadsystem.ai.prompt.PromptBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService{

    private final ChatClient chatClient;
    private final PromptBuilder promptBuilder;

    @Override
    public String generateAnswer(String question, String context)
    {
        log.info("Generating AI answer");

        String prompt = promptBuilder.buildPrompt(question,context);

        log.debug("Generated Prompt: \n{}",prompt);

        String answer = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        log.info("AIs answer generated successfully");

        return answer;
    }


}
