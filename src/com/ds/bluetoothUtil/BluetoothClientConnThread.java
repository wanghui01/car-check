package com.ds.bluetoothUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import com.test.BTClient.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

/**
 * 蓝牙客户端连接线程
 * 
 * @author GuoDong
 * 
 */
public class BluetoothClientConnThread extends Thread {

	private Handler serviceHandler; // 用于向客户端Service回传消息的handler
	private BluetoothDevice serverDevice; // 服务器设备
	private BluetoothSocket socket; // 通信Socket

	/**
	 * 构造函数
	 * 
	 * @param handler
	 * @param serverDevice
	 */
	public BluetoothClientConnThread(Handler handler,
			BluetoothDevice serverDevice) {
		this.serviceHandler = handler;
		this.serverDevice = serverDevice;
	}

	@Override
	public void run() {
		
//		 try {
//		 //socket =
//		 serverDevice.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);
//		 if (serverDevice.getBondState() == BluetoothDevice.BOND_NONE) {
//		 // 利用反射方法调用BluetoothDevice.createBond(BluetoothDevice
//		 // remoteDevice);
//		
//		 Method m;
//		 m = serverDevice.getClass().getMethod("createRfcommSocket", new
//		 Class[] {int.class});
//		 socket = (BluetoothSocket) m.invoke(serverDevice, 1);
//		 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//		 socket.connect();
//		 } else if (serverDevice.getBondState() ==
//		 BluetoothDevice.BOND_BONDED) {
//		try {
//			socket = serverDevice
//					.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);
//			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//			Log.d("BlueToothTestActivity", "开始连接...");
//			socket.connect();
//		} catch (IOException e) {
//			try {
//				socket.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
//					.sendToTarget();
//			return;
//		}
//		 }
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
//		 .sendToTarget();
//		 return;
//		 }
         try{
         	socket = serverDevice.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);
         }catch(IOException e){
        	 e.printStackTrace();
       		 serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
       		 .sendToTarget();
       		 return;
         }
         //连接socket
         try{
         	socket.connect();
         }catch(IOException e){
         	try{
         		socket.close();
         		socket = null;
         	}catch(IOException ee){
         		e.printStackTrace();
       		 serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
       		 .sendToTarget();
       		 return;
         	}
         	
         	return;
         }
		Message msg = serviceHandler.obtainMessage();
		msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
		msg.obj = socket;
		msg.sendToTarget();
	}
}
