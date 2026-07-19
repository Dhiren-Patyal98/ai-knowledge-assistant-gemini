package file.dhiren.fileuploadsystem.ai.embedding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debug")
public class DebugController {

    private final ApplicationContext context;

    @GetMapping("/embeddings")
    public String embeddings() {

        StringBuilder sb = new StringBuilder();

        String[] beans = context.getBeanNamesForType(EmbeddingModel.class);

        for (String bean : beans) {

            sb.append(bean)
                    .append(" -> ")
                    .append(context.getBean(bean).getClass().getName())
                    .append("\n");
        }

        return sb.toString();
    }



    @GetMapping("/chat")
    public String chatModels() {

        StringBuilder sb = new StringBuilder();

        for (String bean : context.getBeanNamesForType(org.springframework.ai.chat.model.ChatModel.class)) {
            sb.append(bean)
                    .append(" -> ")
                    .append(context.getBean(bean).getClass().getName())
                    .append("\n");
        }

        return sb.toString();
    }
}