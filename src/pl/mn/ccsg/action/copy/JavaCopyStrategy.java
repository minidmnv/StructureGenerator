package pl.mn.ccsg.action.copy;

import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author minidmnv
 */
public class JavaCopyStrategy implements CopyStrategy {

    @Override
    public void copy(VirtualFile virtualFile, String patchDirectory) {
        getAllClassFiles(virtualFile)
                .forEach(file -> copyFile(file, patchDirectory));
    }

    private void copyFile(VirtualFile file, String patchDirectory) {
        VirtualFile outputPath = CompilerModuleExtension.getInstance(getModule(file)).getCompilerOutputPath();
        String structure = getFilePackageStructure(file);

        try {
            Files.copy(Paths.get(outputPath.getPath().concat(structure).concat("\\\\").concat(file.getName())),
                    Paths.get(patchDirectory.concat(structure).concat("\\\\").concat(file.getName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Collection<VirtualFile> getAllClassFiles(VirtualFile file) {
        String fileName = cutExtensionFromFile(file);
        Predicate<Path> pathClassesForFile = p -> Pattern.matches(fileName + "\\$\\w.*", p.toFile().getName());
        Predicate<Path> pathClassForFile = p -> fileName.concat(".class").equals(p.toFile().getName());

        VirtualFile outputPath = CompilerModuleExtension.getInstance(getModule(file)).getCompilerOutputPath();
        String structure = getFilePackageStructure(file);
        Set<VirtualFile> classFiles = new HashSet<>();

        try (Stream<Path> files = Files.walk(Paths.get(outputPath.getPath().concat(structure)))) {
            classFiles = files
                    .filter(pathClassesForFile.or(pathClassForFile))
                    .map(Path::toFile)
                    .map(LocalFileSystem.getInstance()::findFileByIoFile)
                    .collect(Collectors.toSet());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return classFiles;
    }

    private String cutExtensionFromFile(VirtualFile file) {
        int indexOfExtension = file.getName().lastIndexOf(".");
        return file.getName().substring(0, indexOfExtension);
    }

}
