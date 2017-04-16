package com.data;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.obj.IVData;

public class TestData {
	public static List<IVData> parseIVData(ResultSet rs){
		List<IVData> list=new ArrayList<IVData>();
		try{
			if(rs.next()){			
				IVData data=new IVData();
				data.setFab_id(rs.getString(0));
				list.add(data);
			}else{
				list=null;
			}
		}catch(Exception e){
			e.printStackTrace();
			list=null;
		}
		return list;
	}
}
