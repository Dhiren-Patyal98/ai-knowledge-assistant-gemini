package file.dhiren.fileuploadsystem.ai.retrieval.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {

    @NotBlank
    private String question;
}
