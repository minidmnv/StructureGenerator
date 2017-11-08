package pl.mn.ccsg.action;

import com.intellij.conversion.CannotConvertException;
import com.intellij.conversion.impl.ConversionContextImpl;
import com.intellij.conversion.impl.ModuleSettingsImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vcs.actions.VcsContextWrapper;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import pl.mn.ccsg.PathQualityAssurance;
import pl.mn.ccsg.action.copy.CopyStrategy;
import pl.mn.ccsg.action.copy.CopyStrategyFactory;
import pl.mn.ccsg.dialog.GenerateStructureDialog;

import java.io.File;
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

import static com.intellij.openapi.ui.DialogWrapper.OK_EXIT_CODE;

/**
 * @author minidmnv
 */
public class GenerateStructureAction extends AnAction {

    private static final String JAVA_FILE_EXTENSION = "java";
    private Project project;
    private String patchDirectory;

    public GenerateStructureAction() {
        super("Generate Class Structure", "Easily generate class structure from active changelist", null);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        project = VcsContextWrapper.createCachedInstanceOn(event).getProject();
        GenerateStructureDialog dialog =
                GenerateStructureDialog.instance(project);
        dialog.show();

        handleDialogAction(dialog);
    }

    private void handleDialogAction(GenerateStructureDialog dialog) {
        if (dialog.getExitCode() == OK_EXIT_CODE) {
            String chosenDirectory = dialog.getChosenDirectory();

            if (PathQualityAssurance.accept(chosenDirectory)) {
                generateClassStructure(chosenDirectory);
            }
        }
    }

    private void generateClassStructure(String patchDirectory) {
        this.patchDirectory = patchDirectory;

        ChangeListManager changeListManager = ChangeListManager.getInstance(project);
        LocalChangeList activeChangeList = changeListManager.getDefaultChangeList();

        activeChangeList.getChanges()
                .stream()
                .filter(change -> !change.getType().equals(Change.Type.DELETED))
                .map(Change::getVirtualFile)
                .forEach(this::processFile);

    }

    private void processFile(VirtualFile file) {
        CopyStrategy copyStrategy = CopyStrategyFactory.getCopyStrategy(file.getExtension());

        if (copyStrategy != null) {
            generateStructure(file);
            copyStrategy.copy(file, patchDirectory);
        } else {
            copyFileToGeneratedStructure(file);
        }

    }

    private void copyFileToGeneratedStructure(VirtualFile file) {
        VirtualFile outputPath = CompilerModuleExtension.getInstance(getModule(file)).getCompilerOutputPath();
        String structure = generateStructure(file);

        try {
            Files.copy(Paths.get(outputPath.getPath().concat(structure).concat("\\\\").concat(file.getName())),
                    Paths.get(patchDirectory.concat(structure).concat("\\\\").concat(file.getName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ModuleSettingsImpl getModuleSettings(VirtualFile file) {
        Module moduleForFile = getModule(file);

        try {
            return (ModuleSettingsImpl) new ConversionContextImpl(project.getBasePath())
                    .getModuleSettings(moduleForFile.getName());
        } catch (CannotConvertException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Module getModule(VirtualFile file) {
        return ProjectFileIndex.SERVICE
                    .getInstance(project)
                    .getModuleForFile(file, false);
    }

    private String generateStructure(VirtualFile file) {
        String path = getFilePackageStructure(file);

        new File(patchDirectory.concat(path)).mkdirs();

        return path;
    }

    private String getFilePackageStructure(VirtualFile file) {
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

}