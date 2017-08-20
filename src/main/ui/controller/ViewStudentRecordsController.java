package main.ui.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.ui.utils.Navigation;

import java.io.IOException;

public class ViewStudentRecordsController {

    @FXML
    private TableView<?> table;

    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private JFXTextField firstNameTextView;

    @FXML
    private JFXTextField lastNameTextView;

    @FXML
    private JFXTextField otherNameTextView;

    @FXML
    private JFXTextField matricNumberTextView;

    @FXML
    private JFXTextField sexTextView;

    @FXML
    private JFXTextField numberOfDaysTextField;

    @FXML
    private Label courseCodeLabel;

    @FXML
    void backButton(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.USER_HOME_PAGE));

        Parent root = null;
        try {
            root = loader.load();

            stage.setScene(new Scene(root));
            // stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void removeStudent(ActionEvent event) {

    }

}
