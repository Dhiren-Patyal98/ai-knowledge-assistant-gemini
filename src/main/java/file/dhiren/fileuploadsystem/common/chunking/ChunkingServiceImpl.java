package file.dhiren.fileuploadsystem.common.chunking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChunkingServiceImpl implements ChunkingService {


    @Value("${chunk.size}")
    private int chunkSize;

    @Value("${chunk.overlap}")
    private int chunkOverlap;


    private static final List<String> SEPARATORS = List.of(
            "\n\n",
            "\n",
            ". ",
            " ",
            ""
    );

    @Override
    public List<String> chunkText(String text)
    {



        if(text == null || text.isBlank())
        {
            return List.of();
        }

        List<String>  chunks  = applyOverlap(recursiveSplit(text,0));

        log.info(
                "Generated {} chunks from document of {} characters",
                chunks.size(),
                text.length()
        );


        return chunks;
    }


    private List<String>  recursiveSplit(String text, int separatorIndex){

        if(text.length() < chunkSize)
        {
            return List.of(text.trim());
        }

        if(separatorIndex >= SEPARATORS.size())
        {
            return characterSplit(text);
        }

        String separator = SEPARATORS.get(separatorIndex);

        if(separator.isEmpty())
        {
            return characterSplit(text);
        }

        String[] parts = text.split(java.util.regex.Pattern.quote(separator));

        List<String> results = new ArrayList<>();

        for(String part :  parts)
        {
            if (part.isBlank()) {
                continue;
            }

            if(part.length() < chunkSize)
            {
                results.add(part.trim());
            }
            else{
                results.addAll(recursiveSplit(part,separatorIndex+1));
            }
        }

        return mergeChunks(results,separator);
    }

    private List<String>  mergeChunks(List<String> pieces, String separator){

        List<String> merged = new ArrayList<>();

        StringBuilder current  =  new StringBuilder();

        for(String piece : pieces)
        {
            if(current.length() + piece.length() + separator.length() <= chunkSize)
            {
                if(!current.isEmpty())
                {
                    current.append(separator);
                }

                current.append(piece);
            }
            else{

                if(!current.isEmpty())
                {
                    merged.add(current.toString().trim());
                }
                current = new StringBuilder(piece);
            }
        }

        if(!current.isEmpty())
        {
            merged.add(current.toString().trim());
        }

        return merged;
    }

    private List<String>  characterSplit(String text){

        List<String> chunks = new ArrayList<>();

        int start  =  0;

        while(start < text.length())
        {
            int end=Math.min(start + chunkSize, text.length());


            if(end < text.length())
            {
                while(end > start && !Character.isWhitespace(text.charAt(end))){
                    end--;
                }

            }

            String chunk = text.substring(start,end).trim();

            chunks.add(chunk);

            if(end == text.length())
            {
                break;
            }

            start =Math.max( 0 ,end - chunkOverlap);
        }


        return chunks;
    }

    private List<String> applyOverlap(List<String> chunks){
        if(chunks.size() <= 1)
        {
            return chunks;
        }

        List<String> overlapped =  new ArrayList<>();

        overlapped.add( chunks.get(0));

        for(int i  = 1 ; i < chunks.size() ; i++)
        {
            String previous = chunks.get(i -1);
            String current  =  chunks.get(i);

            int overlapStart =  Math.max(0, previous.length() - chunkOverlap);

            int originalPosition = overlapStart;


            while(overlapStart > 0 && !Character.isWhitespace(previous.charAt(overlapStart -1)))
            {
                overlapStart--;
            }

            if(overlapStart == 0 && !Character.isWhitespace(previous.charAt(0)))
            {
                overlapStart = originalPosition;

                while(overlapStart < previous.length() && !Character.isWhitespace(previous.charAt(overlapStart)))
                {
                    overlapStart++;
                }

                if(overlapStart == previous.length())
                {
                    overlapStart = originalPosition;
                }
            }

            String overlap = previous.substring(overlapStart).trim();

            String mergedChunk =  overlap +  System.lineSeparator() + current.trim();

            overlapped.add(mergedChunk);
        }

        return  overlapped;

    }

}
