package com.com;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.obj.Bin;
import com.obj.IVData;

public class BinHandle {
	public static void getBinByIVData(Connection conn,IVData data){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * FROM C_MOD_BIN WHERE PRODUCT_ID='"+data.getProduct_id()+"'");
		
		
	}
	public static void main(String[] args) {
		validValue(11.22,9.2323,23.21823428);
	}
	public static String getBinConfigByProduct(Connection conn,String product_id){
		String bin_config="";
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bin_config; 
	}
	
	public static List<List<Bin>> getBinInfoByBinConfig(Connection conn,String bin_config){
		List<List<Bin>> list=null;
		try{
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT * FROM C_BIN_BINCONFIG WHERE BIN_CONFIG='"+bin_config+"'");
			//? list=getBinInfos(conn,sb.toString());
		}catch(Exception e){
			e.printStackTrace();
			list=null;
		}
		return list;
	}

	private static Map<String,List<Bin>> getBinInfos(Connection conn, String sql) {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		ResultSet rs=null;
		//List<List<Bin>> motherList=null;
		Map<String,List<Bin>> motherList=null;
		try{
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			String logic_bin="";
			List<Bin> subList=null;
			while(rs.next()){
				Bin bin=new Bin();
				
				if(logic_bin.equalsIgnoreCase(rs.getString("logic_bin"))){
					//logic_bin=rs.getString("logic_bin");
					subList.add(bin);
				}else{
					if(subList==null){
						subList.add(bin);
						logic_bin=rs.getString("logic_bin");
					}else{
						//motherList.add(subList);
						motherList.put(logic_bin, subList);
						subList=null;
						subList.add(bin);
						logic_bin=rs.getString("logic_bin");
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return motherList;
	}
	
	public static String getModuleBin(IVData data,Map<String,List<Bin>> list){
		String bin_id="";
		try{
			Set set=list.entrySet();
			for(Entry<String,List<Bin>> entry:list.entrySet()){
				String logic_bin=entry.getKey();
				List<Bin> bin_params=entry.getValue();
				boolean result=true;
				for(Bin bin_param:bin_params){
					String bin_parameter=bin_param.getBin_parameter();
					Double param_value=getParamValueByParam(bin_parameter,data);
					Double min_value=bin_param.getMin_value();
					Double max_value=bin_param.getMax_value();
					boolean flag=validValue(param_value,min_value,max_value);
					result=result&&flag;
				}
				if(result){
					bin_id=logic_bin;
					continue;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bin_id;
	}

	private static boolean validValue(Double param_value, Double min_value, Double max_value) {
		// TODO Auto-generated method stub
		BigDecimal param_value1=new BigDecimal(param_value);
		BigDecimal min_value1=new BigDecimal(min_value);
		BigDecimal max_value1=new BigDecimal(max_value);
	     int compareMin = param_value1.compareTo(min_value1);
	     int compareMax = max_value1.compareTo(param_value1);
	     
	     if(compareMin > 0 && compareMax>0) {
	        System.out.println("this data is ok");
	        return true;
	     }
	     System.out.println("this data out of the range");
		return false;
	}

	private static Double getParamValueByParam(String bin_parameter, IVData data) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
}
