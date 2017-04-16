package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.tools.ConnDB;
import com.tools.Print;


@WebServlet("/Labels/AlabelPrintServlet")
public class AlabelPrintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ConnDB conn=new ConnDB();
	private static final Gson gson=new Gson();
	private static final Print print=new Print();
    public AlabelPrintServlet() { super();    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=utf-8");
		PrintWriter output=response.getWriter();
		String action=request.getParameter("action");
		System.out.println(action);
		if(action.equals("get")){
			String type=request.getParameter("infoGet");
			String company=request.getParameter("company");
			String factory=request.getParameter("factory");
			String strJson=infoGet(type,company,factory);
			output.write(strJson);
		}
		else if(action.equals("print")){
			String company=request.getParameter("company");
			String factory=request.getParameter("factory");
			String vendor=request.getParameter("vendor");
			String date=request.getParameter("date");
			String date1=date.replaceAll("-", "");
			String date2=date1.substring(2);
			int printNo=Integer.parseInt(request.getParameter("printNumber"));
			String pre=company+factory+vendor;
			String preLable=pre+date2;
			String[] label = new String[printNo];
			int seqMin=0;
			int seqMax=99999;
			String sql="select * from civil_alabel_rule where useable=1 and company='"+company+"' and factory='"+factory+"' and vendor='"+vendor+"'";
			ResultSet rs=conn.executeQuery(sql);
			try{
				rs.first();
				seqMin=rs.getInt("SeqMin");
				seqMax=rs.getInt("SeqMax");
			}catch(Exception e){
				e.printStackTrace();
			}
			String sql1="select max(Trim('"+preLable+"' from Alabel)) as MaxNo from civil_alabel_log where Alabel like '"+preLable+"%' ";
			int seq=seqMin;
			ResultSet rs1=conn.executeQuery(sql1);
			System.out.println(sql1);
			try{
				rs1.first();
				seq=Integer.parseInt(rs1.getString("MaxNo"))+1;
			}catch(Exception e){
				e.printStackTrace();
			}
			if(seq+printNo>seqMax){
				output.println("Error, the number exceed the max limit");
				return;
			}
			String sql2="insert into civil_alabel_log (ALabel,ProdType) values ";
			for(int i=0;i<printNo;i++){
				int tt=i+seq;
				String tlabel=(preLable+(("0000")+tt).substring((("0000")+tt).length()-5, (("0000")+tt).length()));
				sql2+="('"+tlabel+"','"+pre+"'),";
				label[i]=tlabel;
			}
			sql2=sql2.substring(0, sql2.length()-1);
			conn.excuteUpdate(sql2);
			String sql3="select * from civil_print_config where type='ALabel' and selector='"+pre+"'";
			ResultSet rs3=conn.executeQuery(sql3);
			String ip="";
			String code="";
			try{
				rs3.first();
				code=rs3.getString("code");
				ip=rs3.getString("ip");
			}catch(Exception e){
				e.printStackTrace();
			}
			String strCode="";
			for(int i=0;i<label.length;i++){
				strCode+=code.replace("[ALabel]", label[i]);
			}
			print.zplPrint(ip, strCode);
			output.println(sql2);
			output.println(strCode);
		}
	}
	
	//get Company,Factory,Vendor
	private String infoGet(String type,String company,String factory){
		String sql="select distinct a.Name,a.Code from civil_alabel_info a join civil_alabel_rule b where b.useable=1 and a.type='"+type+"' and a.Code=b."+type;
		if(company!=null) sql+=" and b.Company='"+company+"'";
		if(factory!=null) sql+=" and b.Factory='"+factory+"'";
		ResultSet rs=conn.executeQuery(sql);
		System.out.println(sql);
		JsonArray ary=new JsonArray();
		try {
			while(rs.next()){
				JsonObject obj=new JsonObject();
				obj.addProperty("name", rs.getString("Name"));
				obj.addProperty("code",rs.getString("Code"));
				ary.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gson.toJson(ary);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
