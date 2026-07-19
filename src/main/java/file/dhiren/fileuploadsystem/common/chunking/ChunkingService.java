package file.dhiren.fileuploadsystem.common.chunking;

import java.util.List;

public interface ChunkingService {

    List<String> chunkText(String text);
}
