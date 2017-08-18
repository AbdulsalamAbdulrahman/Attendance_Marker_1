package database;

import model.Lecturer;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDB {

    private Connection conn;
    private Statement statement;

    private static final String TABLE_NAME = "lecturer_table";
    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String OTHER_NAME = "other_name";
    private static final String DEPARTMENT = "department";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";
    private static final String STAFF_ID = "staff_id";
    private static final String PASSWORD = "password";
    private static final String SECURITY_QUESTION = "security_question";
    private static final String ANSWER = "answer";

    public LecturerDB() {
        createTable();
    }

    public void createTable() {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + this.TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    TITLE + " TEXT, " +
                    FIRST_NAME + " TEXT, " +
                    LAST_NAME + " TEXT, " +
                    OTHER_NAME + " TEXT, " +
                    DEPARTMENT + " TEXT, " +
                    PHONE_NUMBER + " TEXT, " +
                    EMAIL + " TEXT, " +
                    STAFF_ID + " TEXT, " +
                    PASSWORD + " TEXT, " +
                    SECURITY_QUESTION + " TEXT, " +
                    ANSWER + " TEXT)";

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

    public void addLecturer(Lecturer lecturer) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String insertStudentString = "INSERT INTO " + this.TABLE_NAME + "(" +
                    TITLE + ", " +
                    FIRST_NAME + ", " +
                    LAST_NAME + ", " +
                    OTHER_NAME + ", " +
                    DEPARTMENT + ", " +
                    PHONE_NUMBER + ", " +
                    EMAIL + ", " +
                    STAFF_ID + ", " +
                    PASSWORD + ", " +
                    SECURITY_QUESTION + ", " +
                    ANSWER + ", " +
                    ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertStudentString);

            preparedStatement.setString(1, lecturer.getTitle());
            preparedStatement.setString(2, lecturer.getFirstName());
            preparedStatement.setString(3, lecturer.getLastName());
            preparedStatement.setString(4, lecturer.getOtherName());
            preparedStatement.setString(5, lecturer.getDepartment());
            preparedStatement.setString(6, lecturer.getPhoneNumber());
            preparedStatement.setString(7, lecturer.getEmail());
            preparedStatement.setString(8, lecturer.getStaffId());
            preparedStatement.setString(9, lecturer.getPassword());
            preparedStatement.setString(10, lecturer.getSecurityQuestion());
            preparedStatement.setString(11, lecturer.getAnswer());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
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

    public void updateLecturerInfo(Lecturer lecturer) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String updateStudent = "UPDATE " + this.TABLE_NAME + " SET " +
                    TITLE + "=?, " +
                    FIRST_NAME + "=?, " +
                    LAST_NAME + "=?, " +
                    OTHER_NAME + "=?, " +
                    DEPARTMENT + "=?, " +
                    PHONE_NUMBER + "=?, " +
                    EMAIL + "= ?" +
                    STAFF_ID + "=?, " +
                    PASSWORD + "=?, " +
                    SECURITY_QUESTION + "=?, " +
                    ANSWER + "=?, " +
                    " WHERE " + "_id" + "=" + lecturer.getId();

            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(updateStudent);

            preparedStatement.setString(1, lecturer.getTitle());
            preparedStatement.setString(2, lecturer.getFirstName());
            preparedStatement.setString(3, lecturer.getLastName());
            preparedStatement.setString(4, lecturer.getOtherName());
            preparedStatement.setString(5, lecturer.getDepartment());
            preparedStatement.setString(6, lecturer.getPhoneNumber());
            preparedStatement.setString(7, lecturer.getEmail());
            preparedStatement.setString(8, lecturer.getStaffId());
            preparedStatement.setString(9, lecturer.getPassword());
            preparedStatement.setString(10, lecturer.getSecurityQuestion());
            preparedStatement.setString(11, lecturer.getAnswer());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
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

    public Lecturer getLecturer(String staffId, String password) {
        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            String getStudent = "SELECT * FROM " + this.TABLE_NAME +
                    " WHERE " + STAFF_ID + "='" + staffId + "' AND " + PASSWORD + "='" + password + "'";

            resultSet = this.statement.executeQuery(getStudent);
            if (resultSet.next()) {
                return new Lecturer(resultSet
                        .getInt(ID), resultSet
                        .getString(TITLE), resultSet
                        .getString(FIRST_NAME), resultSet
                        .getString(LAST_NAME), resultSet
                        .getString(OTHER_NAME), resultSet
                        .getString(DEPARTMENT), resultSet
                        .getString(PHONE_NUMBER), resultSet
                        .getString(EMAIL), resultSet
                        .getString(STAFF_ID), resultSet
                        .getString(PASSWORD), resultSet
                        .getString(SECURITY_QUESTION), resultSet
                        .getString(ANSWER));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
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

        return null;

    }

    public Lecturer getLecturer(int id) {

        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            String getStudent = "SELECT * FROM " + this.TABLE_NAME +
                    " WHERE " + ID + "=" + id;

            resultSet = this.statement.executeQuery(getStudent);
            if (resultSet.next()) {
                return new Lecturer(resultSet
                        .getInt(ID), resultSet
                        .getString(TITLE), resultSet
                        .getString(FIRST_NAME), resultSet
                        .getString(LAST_NAME), resultSet
                        .getString(OTHER_NAME), resultSet
                        .getString(DEPARTMENT), resultSet
                        .getString(PHONE_NUMBER), resultSet
                        .getString(EMAIL), resultSet
                        .getString(STAFF_ID), resultSet
                        .getString(PASSWORD), resultSet
                        .getString(SECURITY_QUESTION), resultSet
                        .getString(ANSWER));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
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

        return null;
    }

    public List<Lecturer> getStudents() {
        List<Lecturer> lecturers = new ArrayList<>();

        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            String getStudent = "SELECT * FROM " + this.TABLE_NAME;

            resultSet = this.statement.executeQuery(getStudent);
            while (resultSet.next()) {
                Lecturer temp =
                        new Lecturer(resultSet
                                .getInt(ID), resultSet
                                .getString(TITLE), resultSet
                                .getString(FIRST_NAME), resultSet
                                .getString(LAST_NAME), resultSet
                                .getString(OTHER_NAME), resultSet
                                .getString(DEPARTMENT), resultSet
                                .getString(PHONE_NUMBER), resultSet
                                .getString(EMAIL), resultSet
                                .getString(STAFF_ID), resultSet
                                .getString(PASSWORD), resultSet
                                .getString(SECURITY_QUESTION), resultSet
                                .getString(ANSWER));
                lecturers.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
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

        return null;

    }

    public void removeLecturer(int id) {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();

            String delete = "DELETE * FROM " + this.TABLE_NAME + " WHERE " + "_id" + "=" + id;

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
