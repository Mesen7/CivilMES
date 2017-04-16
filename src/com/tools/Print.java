package com.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Print {
	public void zplPrint(String ip,String code){
		Socket socket;
		try {
			socket = new Socket(ip,9100);
			//2.获取输出流，向服务器端发送信息
	         OutputStream os = socket.getOutputStream();//字节输出流
	         PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
	         pw.write(code);
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
