package main.ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.database.AttendanceDB;
import main.database.CourseDB;
import main.model.Course;
import main.ui.utils.AlertDialogs;

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
    private JFXButton addCourseButton;

    @FXML
    private JFXComboBox<String> semesterCombo;
    private Course course;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> semester = FXCollections.observableArrayList("First", "Second");
        semesterCombo.setItems(semester);


    }

    @FXML
    void addCourse(ActionEvent event) {

        if (addCourseButton.getText().equals("Save")) {
            if (courseCodeTextField.getText().isEmpty() || courseTitleTextField.getText().isEmpty() || sessionTextField.getText().isEmpty()
                    || creditUnitTextField.getText().isEmpty()) {
                AlertDialogs.showErrorMessage("Error updating course info, Some fields are empty\n" +
                        "fill all fields and try again");
            } else {
                Course course = new Course(courseCodeTextField.getText(), courseTitleTextField.getText(), sessionTextField.getText(),
                        creditUnitTextField.getText(), semesterCombo.getSelectionModel().getSelectedItem());

                CourseDB courseDB = CourseDB.getInstance();
                courseDB.removeCourse(this.course);
                courseDB.addCourse(course, UserHomePageController.lecturer.getStaffId());
                courseDB.alterTable(this.course, course);

                cancel(event);
            }

        } else {
            if (courseCodeTextField.getText().isEmpty() || courseTitleTextField.getText().isEmpty() || sessionTextField.getText().isEmpty()
                    || creditUnitTextField.getText().isEmpty()) {
                AlertDialogs.showErrorMessage("Error adding course, Some fields are empty\n" +
                        "fill all fields and try again");
            } else {
                Course course = new Course(courseCodeTextField.getText(), courseTitleTextField.getText(), sessionTextField.getText(),
                        creditUnitTextField.getText(), semesterCombo.getSelectionModel().getSelectedItem());

                CourseDB courseDB = CourseDB.getInstance();
                courseDB.addCourse(course, UserHomePageController.lecturer.getStaffId());
                AttendanceDB attendanceDB = new AttendanceDB(course.getCourseCode());
                attendanceDB.createTable();

                cancel(event);
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();

    }


    public void setCourse(Course cs) {
        course = cs;

        courseCodeTextField.setText(cs.getCourseCode());
        courseTitleTextField.setText(cs.getCourseTitle());
        semesterCombo.getSelectionModel().select(cs.getSemester());
        sessionTextField.setText(cs.getSession());
        creditUnitTextField.setText(cs.getCreditUnit());

        addCourseButton.setText("Save");


    }
}
