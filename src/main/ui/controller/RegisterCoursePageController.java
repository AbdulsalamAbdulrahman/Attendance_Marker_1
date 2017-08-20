package main.ui.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterCoursePageController implements Initializable {

    @FXML
    private JFXTextField courseCodeTextField;

    @FXML
    private JFXTextField courseTitleTextField;

    @FXML
    private JFXTextField sessionTextField;

    @FXML
    private JFXTextField creditUnitTextField;

    @FXML
    private JFXComboBox<String> semesterCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> semester = FXCollections.observableArrayList("First", "Second");
        semesterCombo.setItems(semester);

    }

    @FXML
    void addCourse(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();

    }



}
