package main.ui.controller;

import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.Lecturer;
import main.ui.utils.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserHomePageController implements Initializable{

    @FXML
    private JFXListView<String> courseListView;
    @FXML
    private Button userButton;

    private Lecturer lecturer;

    @FXML
    void addNewCourse(ActionEvent event) {
        Stage stage = new Stage();//(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.REGISTER_COURSE_PAGE));

        Parent root = null;
        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void editCourseDetails(ActionEvent event) {

        Stage stage = new Stage();//(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.REGISTER_COURSE_PAGE));

        Parent root = null;
        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void logout(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.LOGIN_PAGE));

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
    void markAttendance(ActionEvent event) {
        Stage stage = new Stage();//(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.MARK_ATTENDANCE_PAGE));

        Parent root = null;
        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void maximizeWindow(ActionEvent event) {

    }

    @FXML
    void minimizeWindow(ActionEvent event) {

    }

    @FXML
    void removeCourse(ActionEvent event) {

    }

    @FXML
    void user(ActionEvent event) {

    }

    @FXML
    void viewStudentRecords(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.VIEW_STUDENT_RECORDS));

        Parent root = null;
        try {
            root = loader.load();

            stage.setScene(new Scene(root));
            // stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUser(Lecturer lecturer) {
        this.lecturer = lecturer;
        userButton.setText(lecturer.getStaffId());
    }
}
