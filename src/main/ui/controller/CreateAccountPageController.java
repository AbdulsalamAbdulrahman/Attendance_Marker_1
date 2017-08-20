package main.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.database.LecturerDB;
import main.model.Lecturer;
import main.ui.utils.AlertDialogs;
import main.ui.utils.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountPageController implements Initializable {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField otherNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField answerTextField;

    @FXML
    private TextField departmentTextField;

    @FXML
    private TextField staffIdTextField;

    @FXML
    private ComboBox<String> securityQuestionCombo;

    @FXML
    private PasswordField newPasswordPasswordField;

    @FXML
    private PasswordField confirmPasswordPasswordField;

    @FXML
    void register(ActionEvent event) {
        String title = titleTextField.getText().trim();
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String otherName = otherNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String phoneNumber = phoneNumberTextField.getText().trim();
        String securityQuestion = securityQuestionCombo.getSelectionModel().getSelectedItem();
        String answer = answerTextField.getText().trim();
        String department = departmentTextField.getText().trim();
        String staffId = staffIdTextField.getText().trim();
        String password = newPasswordPasswordField.getText();

        if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || otherName.isEmpty() || email.isEmpty() ||
                phoneNumber.isEmpty() || answer.isEmpty() || department.isEmpty() || staffId.isEmpty() || password.isEmpty()) {
            AlertDialogs.showErrorMessage("Some fileds are empty, please fill all fields correctly");
            return;
        }
        if (!password.equals(confirmPasswordPasswordField.getText())) {
            AlertDialogs.showErrorMessage("The password you entered does not match");
            return;
        }

        Lecturer lecturer = new Lecturer(0, title, firstName, lastName, otherName, department, phoneNumber, email, staffId,
                password, securityQuestion, answer);

        LecturerDB lecturerDB = LecturerDB.getInstance();
        lecturerDB.addLecturer(lecturer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.LOGIN_PAGE));

        try {
            AnchorPane root = loader.load();
            stage.setScene(new Scene(root));

            LoginPageController controller = loader.getController();
            controller.setUserNameAndPassword(staffId, password);

            AlertDialogs.showInformationDialog("Account created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void signin(ActionEvent event) {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> securityQuestion = FXCollections.observableArrayList(
                "What is the name of you favourite food?", "What is the name of your first pet?",
                "Where did you spend you childhood?", "Where did you meet your spouse?",
                "What is the name of your child hood bestfriend?"
        );
        securityQuestionCombo.setItems(securityQuestion);
        securityQuestionCombo.getSelectionModel().selectFirst();
    }
}
