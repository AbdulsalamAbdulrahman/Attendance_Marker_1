package main.database;

import main.model.Student;

import java.sql.*;

public class AttendanceDB {
    private final String ID = "_id";
    private final String NO_OF_DAYS_PRESENT = "no_of_days_present";
    private String TABLE_NAME = "";
    private Connection conn = null;
    private Statement statement = null;

    public AttendanceDB(String courseCode) {
        TABLE_NAME = courseCode;
    }

    public void createTable() {
        conn = DatabaseConnection.getConnection();
        try {
            statement = this.conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    NO_OF_DAYS_PRESENT + " INTEGER)";

            statement.execute(createTable);

            StudentDB studentDB = new StudentDB();
            for (Student std : studentDB.getAllStudent()) {
                addStudent(std);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                statement.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addStudent(Student student) {
        conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {


//            String insertStudentString = "INSERT INTO " + TABLE_NAME + "VALUES(?, ?, ?, ?, ?, ?);";

            String insertStudentString = "INSERT INTO " + TABLE_NAME + "(" +
                    ID + ", " +
                    NO_OF_DAYS_PRESENT +
                    ") VALUES(?, ?);";

            //conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(insertStudentString);

            System.out.println(student.getImage().toString());

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, 0);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    }

    private void markAttendance(int id) {
        conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {

            int daysPresent = getNumberDaysPresent(id);

            String markAttendance = "UPDATE " + this.TABLE_NAME + " SET " +
                    NO_OF_DAYS_PRESENT + "=?" +
                    " WHERE " + ID + "=" + id;

            preparedStatement = conn.prepareStatement(markAttendance);

            preparedStatement.setInt(1, ++daysPresent);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    preparedStatement.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private int getNumberDaysPresent(int id) {
        this.conn = DatabaseConnection.getConnection();
        ResultSet resultSet = null;
        try {

            statement = conn.createStatement();

            String getStudent = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=" + id;

            resultSet = this.statement.executeQuery(getStudent);
            if (resultSet.next())
                return resultSet.getInt(NO_OF_DAYS_PRESENT);


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
        return 0;
    }


    private void deleteTable() {
        conn = DatabaseConnection.getConnection();

        try {

            statement = conn.createStatement();

            String delete = "DROP TABLE IF EXISTS " + TABLE_NAME;
            statement.execute(delete);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    statement.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
