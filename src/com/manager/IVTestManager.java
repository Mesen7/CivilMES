package com.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.TestData;
import com.obj.IVData;
import com.util.db.impl.AccessDBManager;

public class IVTestManager {
	private static final Logger logger = LogManager.getLogger("IVTestManager.class");

	public static boolean IVTestData(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean flag=true;
		String path=request.getParameter("path");
		Connection conn=AccessDBManager.getConnection(path);
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			StringBuffer sb=new StringBuffer();
			sb.append("");
			logger.debug("IVTestData sql="+sb.toString());
			ps=conn.prepareStatement(sb.toString());
			rs=ps.executeQuery();
			List<IVData> list=TestData.parseIVData(rs);
			if(list!=null){
				for(IVData data:list){
					data.getLot_no();
					//mesen IVData ivData=finishLotInfo();
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			
		}
		return flag;
	}

}
