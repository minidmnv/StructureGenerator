package pl.mn.ccsg.action.copy;

import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author minidmnv
 */
public class SimpleFileCopyStrategy implements CopyStrategy {

    @Override
    public void copy(VirtualFile virtualFile, String patchDirectory) {
        copyFile(virtualFile, patchDirectory);
    }

}
