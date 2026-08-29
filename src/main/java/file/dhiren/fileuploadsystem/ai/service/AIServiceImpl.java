package file.dhiren.fileuploadsystem.ai.service;

import file.dhiren.fileuploadsystem.ai.chat.service.ChatService;
import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchResultDto;
import file.dhiren.fileuploadsystem.ai.retrieval.service.RetrievalService;
import file.dhiren.fileuploadsystem.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIServiceImpl implements AIService{

    private final RetrievalService retrievalService;
    private final ChatService chatService;
    private final RedisService redisService;

    @Override
    public String askQuestion(Long userId, String question)
    {

        String cacheKey = "chat:" + userId + ":" + question;
        Object cachedAns = redisService.get(cacheKey);

        if(cachedAns != null)
        {
            log.info("Answer found in Redis cache");
            System.out.println(".....................hit.....................");
            return cachedAns.toString();
        }

        List<SearchResultDto> searchResults = retrievalService.search(question);

        if(searchResults == null || searchResults.isEmpty())
        {
            return "I couldn't find any relevant information in the uploaded documents.";
        }

        String context =  searchResults.stream()
                .map(SearchResultDto::getChunkText)
                .collect(Collectors.joining("\n\n"));

        String ans = chatService.generateAnswer(question,context);

        redisService.save(cacheKey,ans, Duration.ofHours(1));

        return ans;
    }
}
