package pl.mn.ccsg;

import java.io.File;

/**
 * @author minidmnv
 */
public class PathQualityAssurance {
    public static boolean accept(String chosenDirectory) {
        File file = new File(chosenDirectory);

        return file.isDirectory() && file.exists() && file.canWrite();
    }
}
