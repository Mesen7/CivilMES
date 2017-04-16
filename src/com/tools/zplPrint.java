package com.tools;
import java.io.IOException;  
import java.io.OutputStream;  
import java.io.PrintWriter;
import java.net.*;  

public class zplPrint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1.创建客户端Socket，指定服务器端地址和端口
        Socket socket;
		try {
			socket = new Socket("192.168.28.222",9100);
			//2.获取输出流，向服务器端发送信息
	         OutputStream os = socket.getOutputStream();//字节输出流
	         PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
	         String str="^XA^LH50,10^FS^XGE:Flex2.grf^FS^CWK,E:ARI001.FNT^FS  ^CWJ,E:ARI002.FNT^FS ^CWM,E:ARI000.FNT"+"^FS^PW1000^FS^FT150,250^A0N,95,95^FH^FD[ModuleName]^FS^FT735,300,1^AMN^FH^FD[Power]^FS"+"^FT735,345,1^AMN^FH"+"^FD[CurrentMpp]^FS^FT735,390,1^AMN^FH^FD[VoltageMpp]^FS"+"^FT735,435,1^AMN^FH^FD[isc]^FS"+"   ^FT735,480,1^AMN^FH^FD[voc]^FS  ^FT735,525,1^AMN^FH^FD[mfr]^FS   ^FT300,760^AMN^FH^FD[Date]^FS  ^BY3,5,80^FT40,1380^BCN,,N,N^FD[ModuleID]^FS  ^FT40,1420^AMN^FH^FDID:^FS  ^FT110,1420^AMN^FH^FD[ModuleID]^FS  ^FT528,714^AMN^FH^FD.^FS  ^FT482,892^AMN^FH^FD.^FS ^FW^PQ1,0,1,Y^XZ";
	         pw.write(str);
	         pw.flush();//刷新缓存，向服务器端输出
	         socket.shutdownOutput();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
