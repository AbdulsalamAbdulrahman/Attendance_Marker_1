package main.tableData;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentTableData {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty otherName;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty matricNumber;

    public StudentTableData(SimpleIntegerProperty id, SimpleStringProperty firstName, SimpleStringProperty lastName,
                            SimpleStringProperty otherName, SimpleStringProperty sex, SimpleStringProperty matricNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.sex = sex;
        this.matricNumber = matricNumber;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getOtherName() {
        return otherName.get();
    }

    public void setOtherName(String otherName) {
        this.otherName.set(otherName);
    }

    public SimpleStringProperty otherNameProperty() {
        return otherName;
    }

    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public String getMatricNumber() {
        return matricNumber.get();
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber.set(matricNumber);
    }

    public SimpleStringProperty matricNumberProperty() {
        return matricNumber;
    }
}
