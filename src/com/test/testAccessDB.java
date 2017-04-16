package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.db.impl.AccessDBManager;

public class testAccessDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="D:\\Mesen\\Desktop\\None Name.mdb";
		Connection conn=AccessDBManager.getConnection(path);
		String sql="select count(*) from SunData";
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			AccessDBManager.closeConnection(conn);
		}
		
	}

}
