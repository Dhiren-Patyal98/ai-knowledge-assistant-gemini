package file.dhiren.fileuploadsystem.ai.retrieval.controller;

import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchRequest;
import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchResultDto;
import file.dhiren.fileuploadsystem.ai.retrieval.service.RetrievalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retrieval")
@RequiredArgsConstructor
public class RetrievalController {

    private final RetrievalService retrievalService;

//    @PostMapping("/search")
//    public ResponseEntity<List<SearchResultDto>> search(@Valid @RequestBody SearchRequest request)
//    {
//        List<SearchResultDto> results = retrievalService.search(request.getQuestion());
//
//        return ResponseEntity.ok(results);
//    }



}

