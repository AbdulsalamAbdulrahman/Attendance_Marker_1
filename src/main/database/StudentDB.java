package main.database;

import main.model.Student;

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
        conn = DatabaseConnection.getConnection();
        try {
            statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRST_NAME + " TEXT, " +
                    LAST_NAME + " TEXT, " +
                    OTHER_NAME + " TEXT, " +
                    SEX + " TEXT, " +
                    MATRIC_NUMBER + " TEXT, " +
                    IMAGE + " BLOB)";
/*
            String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRST_NAME + " TEXT, " +
                    LAST_NAME + " TEXT, " +
                    OTHER_NAME + " TEXT, " +
                    SEX + " TEXT, " +
                    MATRIC_NUMBER + " TEXT)";*/

            statement.execute(createTable);
            System.out.println("table created");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                statement.close();
                conn.close();

                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            /*if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    public boolean addStudent(Student student) {
        conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {


//            String insertStudentString = "INSERT INTO " + TABLE_NAME + "VALUES(?, ?, ?, ?, ?, ?);";

            String insertStudentString = "INSERT INTO " + TABLE_NAME + "(" +
                    FIRST_NAME + ", " +
                    LAST_NAME + ", " +
                    OTHER_NAME + ", " +
                    SEX + ", " +
                    MATRIC_NUMBER + ", " +
                    IMAGE +
                    ") VALUES(?, ?, ?, ?, ?, ?);";
/*
            conn = DatabaseConnection.getConnection();
            statement = conn.createStatement();

            String insertStudentString = "INSERT INTO " + TABLE_NAME + "(" +
                    FIRST_NAME + ", " +
                    LAST_NAME + ", " +
                    OTHER_NAME + ", " +
                    SEX + ", " +
                    MATRIC_NUMBER + ", " +
                    IMAGE +
                    ") VALUES('" +
                    student.getFirstName() + "', '" +
                    student.getLastName() + "', '" +
                    student.getOtherName() + "', '" +
                    student.getSex() + "', '" +
                    student.getMatricNumber() + "', " +
                    student.getImage() +
                    ")";

            System.out.println(insertStudentString);

            statement.execute(insertStudentString);
            
*/

            //conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertStudentString);

            System.out.println(student.getImage().toString());

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
            /*if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("prepared statement" + e.getMessage());
                }
            }*/

            if (conn != null) {
                try {
                    //statement.close();
                    preparedStatement.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;

    }

    public void updateStudentRecord(Student student) {
        this.conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String updateStudent = "UPDATE " + TABLE_NAME + " SET " +
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
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
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


    public Student getStudent(int id) {
        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {

            statement = conn.createStatement();

            String getStudent = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=" + id;

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

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;

        try {

            statement = conn.createStatement();

            String getStudent = "SELECT * FROM " + TABLE_NAME;

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
                    e.printStackTrace();
                }
            }
        }
        return studentList;
    }

    public void removeStudent(int id) {
        this.conn = DatabaseConnection.getConnection();
        try {
            statement = conn.createStatement();
            String delete = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=" + id;
            statement.execute(delete);

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

    public int getLastId() {

        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + "DESC LIMIT 1";

            resultSet = this.statement.executeQuery(selectQuery);
            if (resultSet.next()) {
                return resultSet.getInt(ID);
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

        return -1;
    }


}
