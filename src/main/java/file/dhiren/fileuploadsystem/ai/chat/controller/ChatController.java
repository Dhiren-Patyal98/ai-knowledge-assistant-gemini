package file.dhiren.fileuploadsystem.ai.chat.controller;

import file.dhiren.fileuploadsystem.ai.chat.dto.AskQuestionRequest;
import file.dhiren.fileuploadsystem.ai.chat.dto.AskQuestionResponse;
import file.dhiren.fileuploadsystem.ai.service.AIService;
import file.dhiren.fileuploadsystem.auth.entity.User;
import file.dhiren.fileuploadsystem.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor

public class ChatController {

    private final AIService aiService;

    private final CurrentUserService currentUserService;

    @PostMapping("/ask")
    public ResponseEntity<AskQuestionResponse> askQuestion(@RequestBody AskQuestionRequest request, Authentication authentication)
    {
        User user = currentUserService.getCurrentUser();

        String answer = aiService.askQuestion(user.getId(), request.getQuestion());

        return ResponseEntity.ok(AskQuestionResponse.builder().answer(answer).build());
    }
}
