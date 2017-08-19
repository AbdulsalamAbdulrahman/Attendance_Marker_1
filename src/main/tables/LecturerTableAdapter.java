package main.tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LecturerTableAdapter {

private final SimpleIntegerProperty id;
private final SimpleStringProperty title;
private final SimpleStringProperty firstName;
private final SimpleStringProperty lastName;
private final SimpleStringProperty otherName;
private final SimpleStringProperty department;



    public LecturerTableAdapter(SimpleIntegerProperty id, SimpleStringProperty title, SimpleStringProperty firstName,
                                SimpleStringProperty lastName, SimpleStringProperty otherName, SimpleStringProperty department) {

        this.id = id;

        this.title = title;

        this.firstName = firstName;

        this.lastName = lastName;

        this.otherName = otherName;

        this.department = department;
    }
    public int getId() {
        return this.id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return this.id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getTitle() {
        return this.title.get();
    }
    public SimpleStringProperty titleProperty() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public String getFirstName() {
        return this.firstName.get();
    }
    public SimpleStringProperty firstNameProperty() {
        return this.firstName;
}
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public String getLastName() {
        return this.lastName.get();
    }
    public SimpleStringProperty lastNameProperty() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public String getOtherName() {
        return this.otherName.get();
    }
    public SimpleStringProperty otherNameProperty() {
        return this.otherName;
    }
    public void setOtherName(String otherName) {
        this.otherName.set(otherName);
    }
    public String getDepartment() {
        return this.department.get();
    }
    public SimpleStringProperty departmentProperty() {
        return this.department;
}
    public void setDepartment(String department) {
        this.department.set(department);
    }

}
