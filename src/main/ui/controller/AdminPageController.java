package main.ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.database.LecturerDB;
import main.database.StudentDB;
import main.model.Lecturer;
import main.model.Student;
import main.tableData.LecturerTableData;
import main.tableData.StudentTableData;
import main.ui.utils.AlertDialogs;
import main.ui.utils.Navigation;
import main.utils.Images;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {

    @FXML
    private JFXListView<String> courseListView;

    @FXML
    private TableView<StudentTableData> studentTable;

    @FXML
    private TableColumn<StudentTableData, Integer> studentId;

    @FXML
    private TableColumn<StudentTableData, String> studentFirstName;

    @FXML
    private TableColumn<StudentTableData, String> studentLastName;

    @FXML
    private TableColumn<StudentTableData, String> studentOtherName;

    @FXML
    private TableColumn<StudentTableData, String> studentSex;

    @FXML
    private TableColumn<StudentTableData, String> studentMatricNumber;

    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private JFXTextField studentFirstNameTextField;

    @FXML
    private JFXTextField studentLastNameTextField;

    @FXML
    private JFXTextField studentOtherNameTextField;

    @FXML
    private JFXTextField studentMatircNumberTextField;

    @FXML
    private JFXTextField studentSexTextField;

    @FXML
    private TableView<LecturerTableData> lectTable;

    @FXML
    private TableColumn<LecturerTableData, Integer> lectId;

    @FXML
    private TableColumn<LecturerTableData, String> lectTitle;

    @FXML
    private TableColumn<LecturerTableData, String> lectFirstName;

    @FXML
    private TableColumn<LecturerTableData, String> lectLastName;

    @FXML
    private TableColumn<LecturerTableData, String> lectOtherName;

    @FXML
    private TableColumn<LecturerTableData, String> lectDepartment;

    @FXML
    private JFXTextField lecturerTitleTextField;

    @FXML
    private JFXTextField lectFirstNameTextField;

    @FXML
    private JFXTextField lectLastNameTextField;

    @FXML
    private JFXTextField lectureOtherNameTextField;

    @FXML
    private Button removeButton;

    @FXML
    private JFXTextField lectDepartmentTextField;

    private StudentDB studentDb;
    private LecturerDB lecturerDb;
    private ObservableList<StudentTableData> studentList;
    private ObservableList<LecturerTableData> lectList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentDb = new StudentDB();
        lecturerDb = LecturerDB.getInstance();

        initStudentTable();
        refreshStudentTableData();

        initLecturerTable();
        refreshLecturerData();
    }

    private void initLecturerTable() {

        lectId.setCellValueFactory(new PropertyValueFactory("id"));
        lectTitle.setCellValueFactory(new PropertyValueFactory("title"));
        lectFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        lectLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        lectOtherName.setCellValueFactory(new PropertyValueFactory("otherName"));
        lectDepartment.setCellValueFactory(new PropertyValueFactory("department"));

    }

    private void refreshLecturerData() {

        lectTable.getItems().clear();
        lectList = FXCollections.observableArrayList(getListOfLecturers());
        lectTable.setItems(lectList);
    }

    private List<LecturerTableData> getListOfLecturers() {

        List<LecturerTableData> list = new ArrayList();

        ///if(studentDb.getAllStudent() != null) {
        int id = 1;
        for (Lecturer lect : lecturerDb.getLecturers()) {
            LecturerTableData lecturer = new LecturerTableData(
                    new SimpleIntegerProperty(id++),
                    new SimpleStringProperty(lect.getTitle()),
                    new SimpleStringProperty(lect.getFirstName()),
                    new SimpleStringProperty(lect.getLastName()),
                    new SimpleStringProperty(lect.getOtherName()),
                    new SimpleStringProperty(lect.getDepartment())
            );


            list.add(lecturer);
        }
//       }

        return list;
    }

    @FXML
    void deleteLecturer(ActionEvent event) {

    }


    @FXML
    void lectTableClicked(MouseEvent event) {

    }


    private void initStudentTable() {

        studentId.setCellValueFactory(new PropertyValueFactory("id"));
        studentFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        studentOtherName.setCellValueFactory(new PropertyValueFactory("otherName"));
        studentSex.setCellValueFactory(new PropertyValueFactory("sex"));
        studentMatricNumber.setCellValueFactory(new PropertyValueFactory("matricNumber"));


    }


    private void refreshStudentTableData() {
        studentTable.getItems().clear();
        studentList = FXCollections.observableArrayList(getListOfStudents());
        studentTable.setItems(studentList);

    }

    @FXML
    void addStudent(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.REGISTER_NEW_STUDENT));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    @FXML
    void deleteStudent(ActionEvent event) {
        if (studentTable.getItems().isEmpty()) {
            AlertDialogs.showErrorMessage("No student selected");

        } else {
            int id = studentTable.getSelectionModel().getSelectedItem().getId();
            studentDb.removeStudent(id);

            refreshStudentTableData();

            studentFirstNameTextField.setText("");
            studentLastNameTextField.setText("");
            studentOtherNameTextField.setText("");
            studentSexTextField.setText("");
            studentMatircNumberTextField.setText("");

            profilePictureImageView.setVisible(false);
            removeButton.setDisable(false);
        }

    }

    @FXML
    void tableClicked(MouseEvent event) {
        if (studentTable.getItems().isEmpty() || studentTable.getSelectionModel().getSelectedItem() == null) {

        } else {
            int id = studentTable.getSelectionModel().getSelectedItem().getId();
            Student std = studentDb.getAllStudent().get(--id);

            if (std != null) {
                studentFirstNameTextField.setText(std.getFirstName());
                studentLastNameTextField.setText(std.getLastName());
                studentOtherNameTextField.setText(std.getOtherName());
                studentSexTextField.setText(std.getSex());
                studentMatircNumberTextField.setText(std.getMatricNumber());

                profilePictureImageView.setImage(Images.getImageFromByte(std.getImage()));
                profilePictureImageView.setVisible(true);
                removeButton.setDisable(false);
            }

        }
    }


    public List<StudentTableData> getListOfStudents() {
        List<StudentTableData> list = new ArrayList();

        ///if(studentDb.getAllStudent() != null) {
        int id = 1;
        for (Student s : studentDb.getAllStudent()) {
            StudentTableData std = new StudentTableData(
                    new SimpleIntegerProperty(id++),
                    new SimpleStringProperty(s.getFirstName()),
                    new SimpleStringProperty(s.getLastName()),
                    new SimpleStringProperty(s.getOtherName()),
                    new SimpleStringProperty(s.getSex()),
                    new SimpleStringProperty(s.getMatricNumber())
            );


            list.add(std);
        }
//       }

        return list;
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
}
