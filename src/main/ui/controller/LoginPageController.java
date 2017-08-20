package main.ui.controller;

import com.jfoenix.controls.JFXSpinner;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.database.LecturerDB;
import main.model.Lecturer;
import main.ui.utils.AlertDialogs;
import main.ui.utils.Navigation;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private JFXSpinner loginSpinner;

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
        loginSpinner.setVisible(true);

        String staffId = staffIdTextField.getText().trim();
        String password = passwordTextField.getText();

        LecturerDB lecturerDB = LecturerDB.getInstance();
        Lecturer lecturer = lecturerDB.getLecturer(staffId, password);

        if (lecturer == null) {
            loginSpinner.setVisible(true);
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.seconds(5));
            pauseTransition.setOnFinished(ev -> {
                loginSpinner.setVisible(false);
                AlertDialogs.showErrorMessage("Error signing in\nThe user name or password is incorrect");

            });
            pauseTransition.play();


        } else {
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.seconds(5));
            pauseTransition.setOnFinished(ev -> {
                loginSpinner.setVisible(false);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.USER_HOME_PAGE));

                Parent root = null;
                try {
                    root = loader.load();
                    stage.setScene(new Scene(root));
                    UserHomePageController controller = loader.getController();
                    controller.setUser(lecturer);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            pauseTransition.play();


        }
    }


    @FXML
    void signup(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.CREATE_ACCOUNT_PAGE));

        Parent root = null;
        try {
            root = loader.load();

            stage.setScene(new Scene(root));
            // stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
