package com.ds.bluetoothUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.security.auth.login.LoginException;

import com.example.carchek.R.string;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
	
/**
 * 蓝牙通讯线程
 * @author GuoDong
 *
 */
public class BluetoothCommunThread extends Thread {

	private Handler serviceHandler;		//与Service通信的Handler
	private BluetoothSocket socket;
	private BufferedReader inStream;		//对象输入流
	private PrintWriter outStream;	//对象输出流
	public volatile boolean isRun = true;	//运行标志位
	
	/**
	 * 构造函数
	 * @param handler 用于接收消息
	 * @param socket
	 */
	public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
		this.serviceHandler = handler;
		this.socket = socket;
		try {
			this.outStream = new PrintWriter(socket.getOutputStream());
			this.inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Log.i("Test","Hi");
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//发送连接失败消息
			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			if (!isRun) {
				break; 
			}
			try {
				//Object obj = inStream.readObject();
			    String obj=inStream.readLine();
				//发送成功读取到对象的消息，消息的obj参数为读取到的对象
				Message msg = serviceHandler.obtainMessage();
				msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
				msg.obj = obj;
				msg.sendToTarget();	
			} catch (Exception ex) {
				//发送连接失败消息
				serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
				ex.printStackTrace();
				return;
			}
		}
		
		//关闭流
		if (inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outStream != null) {
			outStream.close();
			
//			catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 写入一个可序列化的对象
	 * @param obj
	 */
	public void writeObject(Object obj) {
		outStream.flush();
		//outStream.writeObject(obj);
		outStream.println(obj);
		outStream.flush();
		
	}
}
