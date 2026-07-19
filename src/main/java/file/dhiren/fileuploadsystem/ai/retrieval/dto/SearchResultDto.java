package file.dhiren.fileuploadsystem.ai.retrieval.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResultDto {

    private Long chunkId;

    private String chunkText;

    private Double distance;


}
