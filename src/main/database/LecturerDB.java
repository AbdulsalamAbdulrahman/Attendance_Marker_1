package main.database;

import main.model.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDB {

    private Connection conn;
    private Statement statement;

    private final String TABLE_NAME = "lecturer_table";
    private final String ID = "_id";
    private final String TITLE = "title";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String OTHER_NAME = "other_name";
    private final String DEPARTMENT = "department";
    private final String PHONE_NUMBER = "phone_number";
    private final String EMAIL = "email";
    private final String STAFF_ID = "staff_id";
    private final String PASSWORD = "password";
    private final String SECURITY_QUESTION = "security_question";
    private final String ANSWER = "answer";

    public static LecturerDB lecturerDB;

    public static LecturerDB getInstance(){
        if(lecturerDB == null){
            lecturerDB = new LecturerDB();
        }

        return lecturerDB;
    }

    private LecturerDB() {
        createTable();
    }

    public void createTable() {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + this.TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
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
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public boolean addLecturer(Lecturer lecturer) {
        conn = DatabaseConnection.getConnection();
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
                    ANSWER +
                    ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


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

            return preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return false;

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
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public Lecturer getLecturer(String staffId, String password) {
        conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();

            String getStudent = "SELECT * FROM " + TABLE_NAME +
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
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return null;

    }

    public Lecturer getLecturer(int id) {

        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {

            statement = conn.createStatement();

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
                    System.out.println(e.getMessage());
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return null;
    }

    public List<Lecturer> getLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();

        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();

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
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return lecturers;

    }

    public void removeLecturer(int id) {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();

            String delete = "DELETE * FROM " + this.TABLE_NAME + " WHERE " + "_id" + "=" + id;

            this.statement.execute(delete);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }


}
