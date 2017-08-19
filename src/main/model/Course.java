package main.model;

public class Course {
   private String courseCode;
    private String courseTitle;
    private String session;
    private String creditUnit;
    private String semester;

    public Course(String courseCode, String courseTitle, String session, String creditUnit, String semester) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.session = session;
        this.creditUnit = creditUnit;
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(String creditUnit) {
        this.creditUnit = creditUnit;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
