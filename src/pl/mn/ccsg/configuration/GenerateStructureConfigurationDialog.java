package pl.mn.ccsg.configuration;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author minidmnv
 */
public class GenerateStructureConfigurationDialog extends DialogWrapper {

    public GenerateStructureConfigurationDialog(@Nullable Project project) {
        super(project);

        setTitle("Generate Structure Dialog");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return null;
    }
}
