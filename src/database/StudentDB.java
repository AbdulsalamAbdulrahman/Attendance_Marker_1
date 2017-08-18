package database;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDB {
    private Connection conn;
    private Statement statement;

    private static final String TABLE_NAME = "student_table";

    private static final String ID = "_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String OTHER_NAME = "other_name";
    private static final String SEX = "sex";
    private static final String MATRIC_NUMBER = "matric_number";
    private static final String IMAGE = "image";

    public StudentDB() {
        createTable();
    }

    private void createTable() {
        this.conn = DatabaseConnection.getConnection();
        try {
            this.statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + this.TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    FIRST_NAME + " TEXT, " +
                    LAST_NAME + " TEXT, " +
                    OTHER_NAME + " TEXT, " +
                    SEX + " TEXT, " +
                    MATRIC_NUMBER + " TEXT, " +
                    IMAGE + " BLOB)";

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

    public void addStudent(Student student) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String insertStudentString = "INSERT INTO " + this.TABLE_NAME + "(" +
                    FIRST_NAME + ", " +
                    LAST_NAME + ", " +
                    OTHER_NAME + ", " +
                    SEX + ", " +
                    MATRIC_NUMBER + ", " +
                    IMAGE +

                    ") VALUES(?, ?, ?, ?, ?, ?)";

            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertStudentString);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getOtherName());
            preparedStatement.setString(4, student.getSex());
            preparedStatement.setString(5, student.getMatricNumber());
            preparedStatement.setBytes(6, student.getImage());

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

    public void updateStudentRecord(Student student) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String updateStudent = "UPDATE " + this.TABLE_NAME + " SET " +
                    FIRST_NAME + "=?, " +
                    LAST_NAME + "=?, " +
                    OTHER_NAME + "=?, " +
                    SEX + "=?, " +
                    MATRIC_NUMBER + "=?, " +
                    IMAGE + "= ?" +
                    " WHERE " + "_id" + "=" + student.getId();

            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(updateStudent);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getOtherName());
            preparedStatement.setString(4, student.getSex());
            preparedStatement.setString(5, student.getMatricNumber());
            preparedStatement.setBytes(6, student.getImage());

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


    public Student getStudent(int id) {
        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            String getStudent = "SELECT * FROM " + this.TABLE_NAME + " WHERE " + ID + "=" + id;

            resultSet = this.statement.executeQuery(getStudent);
            if (resultSet.next()) {
                return new Student(resultSet
                        .getInt(ID), resultSet
                        .getString(FIRST_NAME), resultSet
                        .getString(LAST_NAME), resultSet
                        .getString(OTHER_NAME), resultSet
                        .getString(SEX), resultSet
                        .getString(MATRIC_NUMBER), resultSet
                        .getBytes(IMAGE));
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

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {
            String getStudent = "SELECT * FROM " + this.TABLE_NAME ;

           resultSet = this.statement.executeQuery(getStudent);
            while (resultSet.next()) {
                Student temp =
                        new Student(resultSet
                        .getInt(ID), resultSet
                        .getString(FIRST_NAME), resultSet
                        .getString(LAST_NAME), resultSet
                        .getString(OTHER_NAME), resultSet
                        .getString(SEX), resultSet
                        .getString(MATRIC_NUMBER), resultSet
                        .getBytes(IMAGE));

                studentList.add(temp);
            }

            return studentList;


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

    public void removeStudent(int id) {
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
