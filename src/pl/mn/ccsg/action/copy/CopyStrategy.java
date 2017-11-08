package pl.mn.ccsg.action.copy;

import com.intellij.conversion.CannotConvertException;
import com.intellij.conversion.impl.ConversionContextImpl;
import com.intellij.conversion.impl.ModuleSettingsImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

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
    void copy(VirtualFile virtualFile, String patchDirectory);

    default Module getModule(VirtualFile file) {
        return ProjectFileIndex.SERVICE
                .getInstance(getProject())
                .getModuleForFile(file, false);
    }

    default Project getProject() {
        return ProjectManager.getInstance().getOpenProjects()[0];
    }

    default String getFilePackageStructure(VirtualFile file) {
        ModuleSettingsImpl moduleSettings = getModuleSettings(file);
        Collection<File> sourceRoots = moduleSettings.getSourceRoots(false);
        sourceRoots.add(
                new File(CompilerModuleExtension.getInstance(getModule(file)).getCompilerOutputPath().getPath()));

        return sourceRoots.stream()
                .map(rootFile -> rootFile.getPath().replaceAll("\\\\", "/"))
                .filter(rootFilePath -> file.getPath().contains(rootFilePath))
                .map(rootFilePath -> file.getParent().getPath().substring((rootFilePath.length())))
                .findFirst().orElse("");
    }

    default ModuleSettingsImpl getModuleSettings(VirtualFile file) {
        Module moduleForFile = getModule(file);

        try {
            return (ModuleSettingsImpl) new ConversionContextImpl(getProject().getBasePath())
                    .getModuleSettings(moduleForFile.getName());
        } catch (CannotConvertException e) {
            e.printStackTrace();
        }

        return null;
    }

    default void copyFile(VirtualFile file, String patchDirectory) {
        VirtualFile outputPath = CompilerModuleExtension.getInstance(getModule(file)).getCompilerOutputPath();
        String structure = getFilePackageStructure(file);

        try {
            Files.copy(Paths.get(outputPath.getPath().concat(structure).concat("\\\\").concat(file.getName())),
                    Paths.get(patchDirectory.concat(structure).concat("\\\\").concat(file.getName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
