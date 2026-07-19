package file.dhiren.fileuploadsystem.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    ChatClient chatClient(@Qualifier("googleGenAiChatModel") ChatModel chatModel)
    {
         return ChatClient.create(chatModel);
    }
}
