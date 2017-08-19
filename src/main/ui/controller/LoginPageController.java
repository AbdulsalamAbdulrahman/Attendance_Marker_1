package main.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.database.LecturerDB;
import main.model.Lecturer;

public class LoginPageController {

    @FXML
    private TextField staffIdTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private CheckBox adminCheck;

    public void setUserNameAndPassword(String staffId, String password) {
        staffIdTextField.setText(staffId);
        passwordTextField.setText(password);
    }

    @FXML
    void adminCheckBoxClicked(ActionEvent event) {

    }

    @FXML
    void closeWindow(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) {

        String staffId = staffIdTextField.getText().trim();
        String password = passwordTextField.getText();

        LecturerDB lecturerDB = LecturerDB.getInstance();
        Lecturer lecturer = lecturerDB.getLecturer(staffId, password);

        if(lecturer == null)
            System.out.println("Not login");
        else
            System.out.println("login");

    }

    @FXML
    void maximizeWindow(ActionEvent event) {

    }

    @FXML
    void minimizeWindow(ActionEvent event) {

    }

    @FXML
    void signup(ActionEvent event) {

    }

}
