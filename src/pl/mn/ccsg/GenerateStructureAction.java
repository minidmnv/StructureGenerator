package pl.mn.ccsg;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.actions.VcsContextWrapper;
import com.intellij.openapi.vcs.changes.ChangeList;
import pl.mn.ccsg.configuration.GenerateStructureConfigurationPanel;

import java.util.Arrays;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static com.intellij.openapi.vcs.VcsDataKeys.CHANGE_LISTS;

/**
 * @author minidmnv
 */
public class GenerateStructureAction extends AnAction {

    public GenerateStructureAction() {
        super("Generate Class Structure", "Easily generate class structure from active changelist", null);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        GenerateStructureConfigurationPanel generateStructureConfigurationPanel
                = new GenerateStructureConfigurationPanel(VcsContextWrapper.createCachedInstanceOn(event).getProject());

    }

}
