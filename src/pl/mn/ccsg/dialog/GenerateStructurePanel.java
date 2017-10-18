package pl.mn.ccsg.dialog;

import com.intellij.openapi.project.Project;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author minidmnv
 */
public class GenerateStructurePanel extends JFXPanel {

    private Button directoryChooserButton;
    private TextArea directoryTextArea;
    private DirectoryChooser pathChooser;
    private final Project project;

    public GenerateStructurePanel(Project project) {
        Platform.setImplicitExit(false);
        this.project = project;
    }

    public void init() {
        initComponents();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(directoryTextArea, directoryChooserButton);

        StackPane pane = new StackPane();
        pane.getChildren().add(vBox);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.show();
        });

    }

    private void initComponents() {
        directoryTextArea = new TextArea();
        directoryChooserButton = new Button("...");
        directoryChooserButton.setOnAction(directoryChooserAction);
    }

    private EventHandler<ActionEvent> directoryChooserAction = event -> {
        pathChooser = new DirectoryChooser();
        File selectedDirectory = pathChooser.showDialog(null);
        if (selectedDirectory != null) {
            directoryTextArea.setText(selectedDirectory.getAbsolutePath());
        }
    };

}
