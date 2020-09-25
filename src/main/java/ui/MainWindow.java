package ui;

import dukemain.DukeMain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for MainWindow. Provides the layout for the other controls.
 * Credits: CS2103 JavaFX Tutorial.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private DukeMain duke;

    private final Image userImage = new Image(this.getClass()
            .getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage = new Image(this.getClass()
            .getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(Ui.chatStartMessage(),
                dukeImage));
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(DukeMain d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to the dialog container. Clears the
     * user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}