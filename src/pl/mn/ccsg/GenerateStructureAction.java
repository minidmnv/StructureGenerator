package pl.mn.ccsg;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.actions.VcsContextWrapper;
import pl.mn.ccsg.configuration.GenerateStructureConfigurationDialog;

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

        GenerateStructureConfigurationDialog dialog =
                new GenerateStructureConfigurationDialog(VcsContextWrapper.createCachedInstanceOn(event).getProject());
        dialog.show();

        handleDialogAction(dialog);
    }

    private void handleDialogAction(GenerateStructureConfigurationDialog dialog) {
        if (dialog.getExitCode() == OK_EXIT_CODE) {

            generateClassStructure("");
        }
    }

    private void generateClassStructure(String patchName) {

    }

}
