/*    */ package database;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class DatabaseConnection
/*    */ {
/*    */   public static Connection getConnection()
/*    */   {
/* 13 */     Connection conn = null;
/*    */     try {
/* 15 */       conn = DriverManager.getConnection("jdbc:sqlite:resources/data/attendancemarker.db");
/*    */     } catch (SQLException e) {
/* 17 */       System.out.println("Error connecting to database " + e.getMessage());
/*    */     }
/*    */     
/*    */ 
/* 21 */     return conn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Oga Emma\Downloads\Compressed\AttendanceMarker_v2.01_jar_2\AttendanceMarker_jar\AttendanceMarker.jar!\main\utils\DatabaseConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */