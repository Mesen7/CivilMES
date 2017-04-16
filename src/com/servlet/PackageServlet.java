package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tools.ConnDB;
import com.tools.Print;

@WebServlet("/Labels/PackageServlet")
public class PackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ConnDB conn=new ConnDB();
	private static final Gson gson=new Gson(); 
	private static final Print print=new Print();
    public PackageServlet() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setCharacterEncoding("UTF-8");
		PrintWriter output=response.getWriter();
		String action=request.getParameter("action");
		System.out.println(action);
		if(action.equals("query")){
			String label=request.getParameter("Label");
			String packId=request.getParameter("Package");
			String sql="select * from civil_alabel_test where Alabel='"+label+"'";
			ResultSet rs=conn.executeQuery(sql);
			JsonObject obj=new JsonObject();
			try {
				if(!rs.first()){
					obj.addProperty("Result", 0);
					obj.addProperty("Message", "查询不到数据");
					output.println(gson.toJson(obj));
					System.out.println("there's no data");
					return;
				}
				else{
					String prodType=rs.getString("ProdType");
					String binConfig=rs.getString("BinConfig");
					String bin=rs.getString("Bin");
					String sql1="select * from civil_bin_config where BinConfig='"+binConfig+"' and bin='"+bin+"'";
					System.out.println(sql1);
					ResultSet rs1=conn.executeQuery(sql1);
					if(rs1.first()){
						rs1.first();
						if(!rs1.getString("Catagory").equals("1")){
							obj.addProperty("Result", 0);
							obj.addProperty("Message", "此组件的Bin无法出货");
							output.println(gson.toJson(obj));
							System.out.println("此组件的Bin无法出货");
							return;
						}
					}else{
						obj.addProperty("Result", 0);
						obj.addProperty("Message", "组件的Bin信息不存在，请联系MES管理员");
						output.println(gson.toJson(obj));
						System.out.println("组件的Bin信息不存在，请联系MES管理员");
						return;
					}
					String strSql="select * from civil_pack_label where Label='"+label+"'";
					ResultSet rsTemp=conn.executeQuery(strSql);
					if(rsTemp.first()){
						obj.addProperty("Result", 0);
						obj.addProperty("Message", "此组件已经绑定了包装箱:"+rsTemp.getString("PackId"));
						output.println(gson.toJson(obj));
						System.out.println("此组件已经绑定了包装箱");
						System.out.println(gson.toJson(obj));
						return;
					}
					if(packId==null||packId.equals("")){
						packId=getPackId(prodType,binConfig,bin);
						String strIns="insert into civil_pack_info(PackId,ProdType,BinConfig,Bin) values('"+packId+"','"+prodType+"','"+binConfig+"','"+bin+"')";
						conn.excuteUpdate(strIns);
					}
					else{
						String sql2="select * from civil_pack_info where PackId='"+packId+"'";
						ResultSet rs2=conn.executeQuery(sql2);
						if(!rs2.first()){
							obj.addProperty("Result", 0);
							obj.addProperty("Message", "无法找到包装箱的信息，请联系MES管理员");
							output.println(gson.toJson(obj));
							System.out.println("无法找到包装箱的信息，请联系MES管理员");
							return;
						};
						rs2.first();
						String packProdType=rs2.getString("ProdType");
						String packBin=rs2.getString("Bin");
						String packBinConfig=rs2.getString("BinConfig");
						if(!(prodType.equals(packProdType)&&bin.equals(packBin)&&binConfig.equals(packBinConfig))){
							obj.addProperty("Result", 0);
							obj.addProperty("Message", "此组件的Bin信息与当前包装箱不匹配");
							output.println(gson.toJson(obj));
							System.out.println("此组件的Bin信息与当前包装箱不匹配");
							return;
						}
					}
					String sql3="insert into civil_pack_label(PackId,Label) values('"+packId+"','"+label+"')";
					System.out.println(sql3);
					conn.excuteUpdate(sql3);
					String result=getLabelbyPackId(packId);
					System.out.println(result);
					output.println(result);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(action.equals("delete")){
			String alabel=request.getParameter("alabel");
			String packId=request.getParameter("packId");
			String sql="delete from civil_pack_label where Label in ("+alabel+")";
			int rowdel=conn.excuteUpdate(sql);
			if(rowdel==0){
				JsonObject obj=new JsonObject();
				obj.addProperty("Result", 1);
				obj.addProperty("Message", "删除失败，请联系管理员");
				output.println(gson.toJson(obj));
				return;
			}
			String result=getLabelbyPackId(packId);
			if(result=="nodata"){
				JsonObject obj=new JsonObject();
				obj.addProperty("Result", 0);
				obj.addProperty("Message", "此包装箱无绑定数据");
				output.println(gson.toJson(obj));
				return;
			}
			output.println(result);
			return;
		}
		else if(action.equals("getbyPackId")){
			String packId=request.getParameter("packId");
			String result=getLabelbyPackId(packId);
			if(result=="nodata"){
				JsonObject obj=new JsonObject();
				obj.addProperty("Result", 0);
				obj.addProperty("Message", "此包装箱无绑定数据");
				output.println(gson.toJson(obj));
				return;
			}
			output.println(result);
			return;
		}
		else if(action.equals("print")){
			String packId=request.getParameter("packId");
			String sql1="select a.PackId,NOW() LabelDate,a.ProdType, b.*,count(c.Label) QTY from civil_pack_info a join civil_bin_print b"
					+" on a.Bin=b.Bin and a.BinConfig=b.BinConfig join civil_pack_label c on a.PackId=c.PackId "
					+ "and a.PackId='"+packId+"' group by a.PackId";
			ResultSet rs=conn.executeQuery(sql1);
			try {
				rs.first();
				String sql3="select * from civil_print_config where type='Package' and selector='"+rs.getString("ProdType")+"'";
				ResultSet rs3=conn.executeQuery(sql3);
				String ip="";
				String code="";
				rs3.first();
				code=rs3.getString("code");
				ip=rs3.getString("ip");
				System.out.println(code);
				code=code.replace("[ModuleName]", "Civil Product");
				code=code.replace("[CrateNumber]", packId);
				code=code.replace("[ProductName]", "ProductName");
				code=code.replace("[QTY]", rs.getInt("QTY")+"");
				code=code.replace("[Pmpp]", rs.getFloat("Pmpp")+"");
				code=code.replace("[Impp]", rs.getFloat("Impp")+"");
				code=code.replace("[Vmpp]", rs.getFloat("Vmpp")+"");
				code=code.replace("[Voc]", rs.getFloat("Voc")+"");
				code=code.replace("[Isc]", rs.getFloat("Isc")+"");
				code=code.replace("[LabelDate]",rs.getDate("LabelDate")+"");
				print.zplPrint(ip, code);
				System.out.println(code);
				output.println(code);
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getLabelbyPackId(String packId){
		JsonArray array=new JsonArray();
		String sql4="select * from civil_pack_label a join civil_alabel_test b on a.Label=b.ALabel and a.packId='"+packId+"'";
		ResultSet rs4=conn.executeQuery(sql4);
		try {
			if(!rs4.first()) return "nodata";
			rs4.beforeFirst();
			while(rs4.next()){
				JsonObject obj1=new JsonObject();
				obj1.addProperty("PackId", rs4.getString("PackId"));
				obj1.addProperty("Label", rs4.getString("Label"));
				obj1.addProperty("BinConfig", rs4.getString("BinConfig"));
				obj1.addProperty("Bin", rs4.getString("Bin"));
				obj1.addProperty("Pmpp", rs4.getFloat("Pmpp"));
				obj1.addProperty("Vmpp", rs4.getFloat("Vmpp"));
				obj1.addProperty("Impp", rs4.getFloat("Impp"));
				obj1.addProperty("Voc", rs4.getFloat("Voc"));
				obj1.addProperty("Isc", rs4.getFloat("Isc"));
				obj1.addProperty("ProdType", rs4.getString("ProdType")+"");
				array.add(obj1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gson.toJson(array);
	}
	private String getPackId(String prodType, String binConfig, String bin) {
		String sql="select * from civil_pack_info where prodType='"+prodType+"' order by DTStamp desc limit 1";
		ResultSet rs=conn.executeQuery(sql);
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String packId="FGC"+prodType.substring(1)+df.format(new Date());
		try{
			if(rs.first()){
				String packIdLast=rs.getString("PackId");
				if(packIdLast.contains(packId)){
					String count=packIdLast.replaceAll(packId, "");
					count="0000"+(Integer.parseInt(count)+1);
					count=count.substring(count.length()-4, count.length());
					packId+=count;
					return packId;
				}
			}
			packId+="0001";
			String sql1="insert into civil_pack_info(PackId,ProdType,BinConfig,Bin) values('"+packId+"','"+prodType+"','"+binConfig+"','"+bin+"')";
			conn.excuteUpdate(sql1);
			System.out.println(packId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return packId;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
