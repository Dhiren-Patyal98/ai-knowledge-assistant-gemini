package file.dhiren.fileuploadsystem.ai.service;

import file.dhiren.fileuploadsystem.ai.chat.service.ChatService;
import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchResultDto;
import file.dhiren.fileuploadsystem.ai.retrieval.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService{

    private final RetrievalService retrievalService;
    private final ChatService chatService;

    @Override
    public String askQuestion(Long userId, String question)
    {
        List<SearchResultDto> searchResults = retrievalService.search(question);

        if(searchResults == null || searchResults.isEmpty())
        {
            return "I couldn't find any relevant information in the uploaded documents.";
        }

        String context =  searchResults.stream()
                .map(SearchResultDto::getChunkText)
                .collect(Collectors.joining("\n\n"));

        return chatService.generateAnswer(question,context);
    }
}
