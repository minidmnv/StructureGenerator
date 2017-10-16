package pl.mn.ccsg;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.ChangeList;

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
        Project project = event.getRequiredData(PROJECT);
        ChangeList[] changeLists = event.getRequiredData(CHANGE_LISTS);

        System.out.println("No i teraz bede generowal struktury klas");
        System.out.println(project);
        Arrays.stream(changeLists).forEach(ChangeList::getName);
    }

}
