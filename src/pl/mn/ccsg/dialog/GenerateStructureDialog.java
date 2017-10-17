package pl.mn.ccsg.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author minidmnv
 */
public class GenerateStructureDialog extends DialogWrapper {

    private GenerateStructurePanel panel;
    private final Project currentProject;

    protected GenerateStructureDialog(@Nullable Project project) {
        super(project);

        this.currentProject = project;
        this.panel = new GenerateStructurePanel(project);
    }


    public static GenerateStructureDialog instance(@Nullable Project project) {
        GenerateStructureDialog dialog = new GenerateStructureDialog(project);
        dialog.init();

        return dialog;
    }

    @Override
    protected void init() {
        setTitle("Generate Structure Dialog");
        panel.init();

        super.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return null;
    }
}