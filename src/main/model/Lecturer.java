package main.model;

public class Lecturer {
    private int id;
    private String title;
    private String firstName;
    private String lastName;
    private String otherName;
    private String department;
    private String phoneNumber;
    private String email;
    private String staffId;
    private String password;
    private String securityQuestion;
    private String answer;

    public Lecturer(int id, String title, String firstName, String lastName, String otherName, String department, String phoneNumber,
                    String email, String staffId, String password, String securityQuestion, String answer) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.staffId = staffId;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
    }

    public Lecturer(String title, String firstName, String lastName, String otherName, String department, String phoneNumber,
                    String email, String staffId, String password, String securityQuestion, String answer) {
        this.id = 0;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.staffId = staffId;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
