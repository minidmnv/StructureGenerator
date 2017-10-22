package pl.mn.ccsg.dialog;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * @author minidmnv
 */
public class GenerateStructurePanel extends JPanel {

    private JButton directoryChooserButton;
    private JTextField directoryTextArea;
    private JFileChooser pathChooser;
    private final Project project;
    private JLabel pathLabel;

    public GenerateStructurePanel(Project project) {
        this.project = project;
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

    }

}
