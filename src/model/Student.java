package model;

public class Student {
    private String firstName;
    private String lastName;
    private String otherName;
    private String matricNumber;
    private String sex;

    public Student(String firstName, String lastName, String otherName, String matricNumber, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.matricNumber = matricNumber;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
