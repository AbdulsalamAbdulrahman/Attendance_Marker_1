/*    */ package main.database;
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
