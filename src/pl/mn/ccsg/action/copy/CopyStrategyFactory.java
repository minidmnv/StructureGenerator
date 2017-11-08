package pl.mn.ccsg.action.copy;

/**
 * @author minidmnv
 */
public class CopyStrategyFactory {

    private final static String JAVA_FILE_EXTENSION = "java";

    public static CopyStrategy getCopyStrategy(String extension) {
        if (extension == null) {
            return null;
        }

        switch (extension) {
            case JAVA_FILE_EXTENSION:
                return new JavaCopyStrategy();
            case "properties":
                return null;
        }

        throw new RuntimeException("No valid copy strategy for given extension: " + extension);
    }
}
