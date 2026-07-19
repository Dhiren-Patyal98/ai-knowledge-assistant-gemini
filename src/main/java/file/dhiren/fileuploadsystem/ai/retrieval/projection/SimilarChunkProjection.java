package file.dhiren.fileuploadsystem.ai.retrieval.projection;

public interface SimilarChunkProjection {

    Long getChunkId();

    String getChunkText();

    Double getDistance();
}
