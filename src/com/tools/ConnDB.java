package com.tools;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnDB {
	public Connection conn=null;
	public Statement stmt=null;
	public ResultSet rs=null;
	
	private static String propFileName = "connDB.properties";
	private static Properties prop = new Properties();
	private static String dbClassName = "com.mysql.jdbc.Driver";
	private static String dbUrl="jdbc:mysql://10.23.180.108:3306/mesen?user=mesen&password=1234qwer&useUnicode=true";
	
	//初始化，获取数据库驱动与数据库连接信息
	public ConnDB(){
		try{
			InputStream in=getClass().getResourceAsStream(propFileName);
			prop.load(in);
			dbClassName=prop.getProperty("DB_CLASS_NAME",dbClassName);
			dbUrl=prop.getProperty("DB_URL", dbUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		Connection conn=null;
		try{
			Class.forName(dbClassName).newInstance();
			conn=DriverManager.getConnection(dbUrl);
		}catch(Exception ee){
			ee.printStackTrace();
		}
		if(conn==null){
			System.err.println("Warning:DbConnection Manager.getConnection() failed\r\n Connection type:"
					+dbClassName+"\r\n Connection location:"+dbUrl); //在控制台上输出提示信息
		}
		return conn;
	}
	
	public ResultSet executeQuery(String sql){
		try{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery(sql);
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		return rs;
	}
	
	public int excuteUpdate(String sql){
		int result=0;
		try{
			conn=getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result=stmt.executeUpdate(sql);
		}catch(Exception ex){
			result=0;
			System.err.println(ex.getMessage());
		}
		return result;
	}
	
	public void close(){
		try{
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace(System.err);
		}
	}
}
