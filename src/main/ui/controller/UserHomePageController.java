package main.ui.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import main.database.CourseDB;
import main.model.Course;
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

    public static Lecturer lecturer;
    private CourseDB courseDb;

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
            stage.showAndWait();

            refreshCourseList();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void editCourseDetails(ActionEvent event) {

        if (listIsNotEmpty()) {

            Stage stage = new Stage();//(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.REGISTER_COURSE_PAGE));

            Parent root = null;
            try {
                root = loader.load();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.WINDOW_MODAL);

                String selectedCourse = courseListView.getSelectionModel().getSelectedItem();

                RegisterCoursePageController controller = loader.getController();

                for (Course cs : courseDb.getAllCourse(lecturer.getStaffId())) {
                    if (cs.getCourseTitle().equals(selectedCourse)) {
                        controller.setCourse(cs);
                        break;
                    }

                }

                stage.showAndWait();
                refreshCourseList();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean listIsNotEmpty() {
        return !(courseListView.getSelectionModel().isEmpty() || courseListView.getItems().isEmpty());
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
        if (listIsNotEmpty()) {
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

    }


    @FXML
    void removeCourse(ActionEvent event) {
        if (listIsNotEmpty()) {

        }

    }

    @FXML
    void user(ActionEvent event) {


    }

    @FXML
    void viewStudentRecords(ActionEvent event) {
        if (listIsNotEmpty()) {
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshCourseList();
    }

    private void refreshCourseList() {
        courseDb = CourseDB.getInstance();

        ObservableList<String> courseNames = FXCollections.observableArrayList();
        try {
            for (Course cs : courseDb.getAllCourse(lecturer.getStaffId())) {
                courseNames.add(cs.getCourseTitle());
            }
            courseListView.setItems(courseNames);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void setUser(Lecturer lecturer) {
        UserHomePageController.lecturer = lecturer;
        userButton.setText(lecturer.getStaffId());

        refreshCourseList();
    }
}
