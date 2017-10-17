package pl.mn.ccsg.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vcs.actions.VcsContextWrapper;
import com.intellij.openapi.vcs.changes.patch.AutoMatchIterator;
import pl.mn.ccsg.dialog.GenerateStructureDialog;

import static com.intellij.openapi.ui.DialogWrapper.OK_EXIT_CODE;

/**
 * @author minidmnv
 */
public class GenerateStructureAction extends AnAction {

    public GenerateStructureAction() {
        super("Generate Class Structure", "Easily generate class structure from active changelist", null);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        GenerateStructureDialog dialog =
                GenerateStructureDialog.instance(VcsContextWrapper.createCachedInstanceOn(event).getProject());
        dialog.show();

        handleDialogAction(dialog);
    }

    private void handleDialogAction(GenerateStructureDialog dialog) {
        if (dialog.getExitCode() == OK_EXIT_CODE) {

            generateClassStructure("");
        }
    }

    private void generateClassStructure(String patchName) {

    }

}
