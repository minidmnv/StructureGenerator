package pl.mn.ccsg;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

/**
 * @author minidmnv
 */
public class GenerateStructureAction extends DumbAwareAction {

    public GenerateStructureAction() {
        super("Generate Class Structure", "Easily generate class structure from active changelist", null);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

    }

}
