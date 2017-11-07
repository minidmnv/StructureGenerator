package pl.mn.ccsg.action.copy;

import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author minidmnv
 */
@FunctionalInterface
public interface CopyStrategy {

    /**
     * Copy given file according to current implementation.
     *
     * @param virtualFile
     */
    void copy(VirtualFile virtualFile);

}
