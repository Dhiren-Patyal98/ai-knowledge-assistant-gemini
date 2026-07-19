package file.dhiren.fileuploadsystem.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPrompt(String question, String context)
    {
         return """
                You are a helpful AI assistant.

                Answer the user's question ONLY using the provided context.

                If the answer is not present in the context, respond with:
                "I don't have enough information in the uploaded documents."

                ------------------------
                Context:
                %s
                ------------------------

                Question:
                %s

                Answer:
                """.formatted(context, question);
    }
}
