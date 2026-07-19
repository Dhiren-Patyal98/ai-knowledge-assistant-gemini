package file.dhiren.fileuploadsystem.ai.retrieval.service;

import file.dhiren.fileuploadsystem.ai.retrieval.dto.SearchResultDto;

import java.util.List;

public interface RetrievalService {

    List<SearchResultDto> search(String question);
}
