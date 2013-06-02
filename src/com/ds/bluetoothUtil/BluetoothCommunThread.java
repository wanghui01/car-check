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
 * ����ͨѶ�߳�
 * @author GuoDong
 *
 */
public class BluetoothCommunThread extends Thread {

	private Handler serviceHandler;		//��Serviceͨ�ŵ�Handler
	private BluetoothSocket socket;
	private BufferedReader inStream;		//����������
	private PrintWriter outStream;	//���������
	public volatile boolean isRun = true;	//���б�־λ
	
	/**
	 * ���캯��
	 * @param handler ���ڽ�����Ϣ
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
			//��������ʧ����Ϣ
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
				//���ͳɹ���ȡ���������Ϣ����Ϣ��obj����Ϊ��ȡ���Ķ���
				Message msg = serviceHandler.obtainMessage();
				msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
				msg.obj = obj;
				msg.sendToTarget();	
			} catch (Exception ex) {
				//��������ʧ����Ϣ
				serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
				ex.printStackTrace();
				return;
			}
		}
		
		//�ر���
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
	 * д��һ�������л��Ķ���
	 * @param obj
	 */
	public void writeObject(Object obj) {
		outStream.flush();
		//outStream.writeObject(obj);
		outStream.println(obj);
		outStream.flush();
		
	}
}
