package file.dhiren.fileuploadsystem.common.util;

public final class VectorUtil {

    private VectorUtil(){

    }

    public static String toPGVector(float[] vector)
    {
        StringBuilder builder  = new StringBuilder("[");
        for(int i  = 0  ; i < vector.length ; i++)
        {
            builder.append(vector[i]);

            if(i < vector.length -1)
            {
                builder.append(",");
            }
        }
        builder.append("]");

        return builder.toString();
    }
}
