package com.loveplusplus.nearby.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 类说明
 * 
 * @author 程辉
 * @version V1.0 创建时间：2012-11-20 下午12:44:45
 */
public class DBUtil {
	
	
	private static String DBURL = "jdbc:mysql://localhost:3306/nearby";  
	private static String USERNAME="root";  
	private static String PASSWORD="root";  
    
    private DBUtil() {
    }  
    
    static {//注册驱动    
        try {    
            Class.forName("com.mysql.jdbc.Driver");    
        } catch (ClassNotFoundException e) {    
            throw new ExceptionInInitializerError(e);  
        }    
    }    
  
    public static Connection getDBConn() throws SQLException {  
        return DriverManager.getConnection(DBURL,USERNAME,PASSWORD);  
    }    

	
	
	public static void closeDB(ResultSet rs,Statement ps,Connection conn){
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if(null!=ps){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(null!=conn){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
