package pl.mn.ccsg.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vcs.actions.VcsContextWrapper;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import pl.mn.ccsg.PathQualityAssurance;
import pl.mn.ccsg.dialog.GenerateStructureDialog;

import static com.intellij.openapi.ui.DialogWrapper.OK_EXIT_CODE;

/**
 * @author minidmnv
 */
public class GenerateStructureAction extends AnAction {

    private Project project;

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
        ChangeListManager changeListManager = ChangeListManager.getInstance(project);
        LocalChangeList activeChangeList = changeListManager.getDefaultChangeList();

        activeChangeList.getChanges()
                .stream()
                .filter(change -> !change.getType().equals(Change.Type.DELETED))
                .map(Change::getVirtualFile)
                .map(file -> ProjectFileIndex.SERVICE.getInstance(project).getModuleForFile(file, false))
                .map(CompilerModuleExtension::getInstance)
                .map(CompilerModuleExtension::getCompilerOutputPath)
                .forEach(System.out::println);

    }



}
