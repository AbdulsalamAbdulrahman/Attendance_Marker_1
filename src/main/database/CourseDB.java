package main.database;

import main.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDB {
    private Connection conn;
    private Statement statement;

    private static final String TABLE_NAME = "table_name";

    private static final String ID = "_id";
    private static final String COURSE_CODE = "course_code";
    private static final String COURSE_TITLE = "course_title";
    private static final String SESSION = "session";
    private static final String CREDIT_UNIT = "credit_unit";
    private static final String SEMESTER = "semester";
    private static final String LECTURER_ID = "lecturer_id";

    public CourseDB() {
        createTable();
    }

    private void createTable() {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + this.TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    COURSE_CODE + " TEXT, " +
                    COURSE_TITLE + " TEXT, " +
                    SESSION + " TEXT, " +
                    CREDIT_UNIT + " TEXT, " +
                    SEMESTER + " TEXT, " +
                    LECTURER_ID + " TEXT)";

            this.statement.execute(createTable);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.clearWarnings();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void addCourse(Course course, String lecturerId) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {

            String insertCourseString = "INSERT INTO " + TABLE_NAME + "(" +
                    COURSE_TITLE + ", " +
                    COURSE_CODE + ", " +
                    SESSION + ", " +
                    CREDIT_UNIT + ", " +
                    SEMESTER + ", " +
                    LECTURER_ID + ") " +
                    "VALUES (?, ?, ?, ?, ?, ?)";


            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertCourseString);

            preparedStatement.setString(1, course.getCourseTitle());
            preparedStatement.setString(2, course.getCourseCode());
            preparedStatement.setString(3, course.getSession());
            preparedStatement.setString(4, course.getCreditUnit());
            preparedStatement.setString(5, course.getSemester());
            preparedStatement.setString(6, lecturerId);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.clearWarnings();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void update(Course course, String lecturerId) {

        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {

            String updateCourse = "UPDATE '" + TABLE_NAME + "' SET " +
                    COURSE_TITLE + "=? " +
                    COURSE_CODE + "=? " +
                    SESSION + "=? " +
                    CREDIT_UNIT + "=? " +
                    SEMESTER + "=?" +
                    " WHERE " + LECTURER_ID + "='" + lecturerId + "'";


            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(updateCourse);

            preparedStatement.setString(1, course.getCourseTitle());
            preparedStatement.setString(2, course.getCourseCode());
            preparedStatement.setString(3, course.getSession());
            preparedStatement.setString(4, course.getCreditUnit());
            preparedStatement.setString(5, course.getSemester());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.clearWarnings();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Course getCourse(String courseCode, String lecturerId) {
        ResultSet resultSet = null;
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();

            String getStudent = "SELECT * FROM '" + TABLE_NAME + "' WHERE " +
                    COURSE_CODE + "=" + courseCode + " AND " +
                    LECTURER_ID + "=" + lecturerId;

            resultSet = this.statement.executeQuery(getStudent);
            if (resultSet.next()) {
                return new Course(resultSet
                        .getString(COURSE_TITLE), resultSet
                        .getString(COURSE_CODE), resultSet
                        .getString(SESSION), resultSet
                        .getString(CREDIT_UNIT), resultSet
                        .getString(SEMESTER));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

        public List<Course> getAllCourse () {
        List<Course> courses = new ArrayList<>();

            ResultSet resultSet = null;
            this.conn = DatabaseConnection.getConnection();
            try {
                this.statement = this.conn.createStatement();

                String getStudent = "SELECT * FROM '" + TABLE_NAME+ "'";

                resultSet = this.statement.executeQuery(getStudent);
                while (resultSet.next()) {
                    Course temp =
                            new Course(resultSet
                            .getString(COURSE_TITLE), resultSet
                            .getString(COURSE_CODE), resultSet
                            .getString(SESSION), resultSet
                            .getString(CREDIT_UNIT), resultSet
                            .getString(SEMESTER));

                    courses.add(temp);
                }

                return courses;

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(resultSet != null){
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

    public void removeCourse(int id) {

        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();
            String delete = "DELETE * FROM " + TABLE_NAME + " WHERE " + "_id" + "=" + id;

            this.statement.execute(delete);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.clearWarnings();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
