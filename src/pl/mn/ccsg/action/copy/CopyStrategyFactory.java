package pl.mn.ccsg.action.copy;

/**
 * @author minidmnv
 */
public class CopyStrategyFactory {

    private final static String JAVA_FILE_EXTENSION = "java";

    public static CopyStrategy getCopyStrategy(String extension) {
        switch (extension) {
            case JAVA_FILE_EXTENSION:
                return new JavaCopyStrategy();
        }

        throw new RuntimeException("No valid copy startegy for given extension: " + extension);
    }
}
