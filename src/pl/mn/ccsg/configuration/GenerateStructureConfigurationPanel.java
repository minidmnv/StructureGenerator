package pl.mn.ccsg.configuration;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.patch.CreatePatchConfigurationPanel;
import org.jetbrains.annotations.NotNull;

/**
 * @author minidmnv
 */
public class GenerateStructureConfigurationPanel extends CreatePatchConfigurationPanel {

    public GenerateStructureConfigurationPanel(@NotNull Project project) {
        super(project);
    }
}
