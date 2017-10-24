package pl.mn.ccsg.dialog;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author minidmnv
 */
public class GenerateStructurePanel extends JPanel {

    private final Project project;
    private final GenerateStructureDialog dialog;

    private JButton directoryChooserButton;
    private JTextField directoryTextArea;
    private JFileChooser pathChooser;
    private JLabel pathLabel;

    public GenerateStructurePanel(Project project, GenerateStructureDialog dialog) {
        this.project = project;
        this.dialog = dialog;
    }

    public void init() {
        initComponents();

        add(pathLabel);
        add(directoryTextArea);
        add(directoryChooserButton);
    }

    private void initComponents() {
        pathLabel = new JLabel("Path:");
        directoryTextArea = new JTextField(project.getBasePath(), 20);
        directoryChooserButton = new JButton("...");
        directoryChooserButton.addActionListener(directoryChooserAction);
    }

    private ActionListener directoryChooserAction = e -> openPathChooser();

    private void openPathChooser() {
        pathChooser = new JFileChooser(project.getBasePath());
        pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (pathChooser.showDialog(this, "Choose") == JFileChooser.APPROVE_OPTION) {
            directoryTextArea.setText(pathChooser.getSelectedFile().getAbsolutePath());
        }
    }



    public String getChosenPath() {
        return directoryTextArea.getText();
    }

}
