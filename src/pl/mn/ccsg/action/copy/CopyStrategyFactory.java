package pl.mn.ccsg.action.copy;

/**
 * @author minidmnv
 */
public class CopyStrategyFactory {

    private final static String JAVA_FILE_EXTENSION = "java";

    public static CopyStrategy getCopyStrategy(String extension) {
        if (extension == null) {
            return new SimpleFileCopyStrategy();
        }

        switch (extension) {
            case JAVA_FILE_EXTENSION:
                return new JavaCopyStrategy();
            default:
                return new SimpleFileCopyStrategy();
        }
    }
}
