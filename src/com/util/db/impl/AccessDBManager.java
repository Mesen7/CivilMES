package com.util.db.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.ucanaccess.jdbc.UcanaccessDriver;

public class AccessDBManager {
	private static final Logger logger = LogManager.getLogger("AccessDBManager.class");	
	public static Connection getConnection(String path){
		Connection conn=null;
		String url=UcanaccessDriver.URL_PREFIX +path+";newDatabaseVersion=V2003";		
		//String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+path;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(url, "sa", "");
			logger.debug("getConnection conn="+conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return conn;
	}
	
	public static void closeConnection(Connection conn){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
