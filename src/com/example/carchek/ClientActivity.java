package com.example.carchek;

import java.util.Date;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.ds.bluetoothUtil.BluetoothClientService;
import com.ds.bluetoothUtil.BluetoothTools;
import com.ds.bluetoothUtil.TransmitBean;

public class ClientActivity extends Activity {

	private ToggleButton setToggle;
	private Button sreachBtn;
	private Button connectBtn;
	private RadioGroup deviceccrollLayout;
	private int choseId = -1;
	private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	//private Dictionary<Integer,BluetoothDevice> deviceList = new Hashtable<Integer,BluetoothDevice>();
	// private List<String> BlueNameList = new ArrayList<String>();
	// 广播接收器
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
				// 未发现设备
				// serversText.append("not found device\r\n");

			}else if (BluetoothTools.ACTION_CONNECT_ERROR.equals(action)) {
				// showInfo(R.string.action_no_device);
				Toast.makeText(ClientActivity.this, "连接错误",
						Toast.LENGTH_LONG).show();
			}
			else if (BluetoothTools.ACTION_NO_DEVICE.equals(action)) {
				// showInfo(R.string.action_no_device);
				Toast.makeText(ClientActivity.this, R.string.action_no_device,
						Toast.LENGTH_LONG).show();
			} else if (BluetoothTools.ACTION_FOUND_DEVICE.equals(action)) {
				// 获取到设备对象
				BluetoothDevice device = (BluetoothDevice) intent.getExtras()
						.get(BluetoothTools.DEVICE);				
				RadioButton tempButton = new RadioButton(context);				
				tempButton.setText(device.getName() + "|" + device.getAddress());
				deviceccrollLayout.addView(tempButton,
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				//deviceList.put(tempButton.getId(),device);				
				// serversText.append(device.getName() + "\r\n");

			} else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
				// 连接成功
				// serversText.append("连接成功");
				// sendBtn.setEnabled(true);
				Toast.makeText(ClientActivity.this, "连接成功", Toast.LENGTH_LONG)
						.show();
				
				TransmitBean data =new TransmitBean(); //
				data.setMsg("A");				  
				  Intent sendDataIntent = new Intent(
				  BluetoothTools.ACTION_DATA_TO_SERVICE);
				  sendDataIntent.putExtra(BluetoothTools.DATA, data);
				  sendBroadcast(sendDataIntent); //} } });
			} else if (BluetoothTools.ACTION_DATA_TO_GAME.equals(action)) {
				// 接收数据
				TransmitBean data = (TransmitBean) intent.getExtras()
						.getSerializable(BluetoothTools.DATA);
				String msg = "from remote " + new Date().toLocaleString()
						+ " :\r\n" + data.getMsg() + "\r\n";
				// chatEditText.append(msg);
				
			}
		}
	};

	@Override
	protected void onStart() {
		// 清空设备列表	

		// 开启后台service
		Intent startService = new Intent(ClientActivity.this,
				BluetoothClientService.class);
		startService(startService);

		// 注册BoradcasrReceiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);
		intentFilter.addAction(BluetoothTools.ACTION_FOUND_DEVICE);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_ERROR);
		intentFilter.addAction(BluetoothTools.ACTION_DATA_TO_GAME);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_SUCCESS);
		intentFilter.addAction(BluetoothTools.ACTION_NO_DEVICE);
		registerReceiver(broadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		setToggle = (ToggleButton) findViewById(R.id.toggleButton1);
		if (null == adapter) {
			Toast.makeText(ClientActivity.this, R.string.action_no_device,
					Toast.LENGTH_LONG).show();
			setToggle.setChecked(false);
		} else if (adapter.isEnabled()) {
			setToggle.setChecked(true);
		}
		sreachBtn = (Button) findViewById(R.id.button1);
		connectBtn = (Button) findViewById(R.id.button2);
		setToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (null != adapter) {
					if (isChecked) {
						adapter.enable();
					} else {
						
						adapter.disable(); // 同样的关闭WIFi为;
					}
					setToggle.setChecked(isChecked);
				} else {
					setToggle.setChecked(false);
				}
			}

		});
		// List<Object> source = new ArrayList<Object>();

		// seachListView.setAdapter(blueNameAdapter);

		/*
		 * sreachBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // 发送消息 //if
		 * ("".equals(sendEditText.getText().toString().trim())) {
		 * //Toast.makeText(ClientActivity.this, "输入不能为空", //
		 * Toast.LENGTH_SHORT).show(); //} else { // 发送消息 TransmitBean data =
		 * new TransmitBean(); //data.setMsg(sendEditText.getText().toString());
		 * Intent sendDataIntent = new Intent(
		 * BluetoothTools.ACTION_DATA_TO_SERVICE);
		 * sendDataIntent.putExtra(BluetoothTools.DATA, data);
		 * sendBroadcast(sendDataIntent); //} } });
		 */

		sreachBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 开始搜索

				Intent startSearchIntent = new Intent(
						BluetoothTools.ACTION_START_DISCOVERY);
				sendBroadcast(startSearchIntent);
			}
		});
		connectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 选择第一个设备
//				if (deviceList.isEmpty()) {
//					Toast.makeText(ClientActivity.this,
//							R.string.action_no_found, Toast.LENGTH_LONG)
//							.show();
//					return;					
//				}
				if (choseId == -1) {
					Toast.makeText(ClientActivity.this,
							R.string.action_no_select, Toast.LENGTH_LONG)
							.show();
					return;
				}
				adapter.cancelDiscovery();
				RadioButton rb = (RadioButton)ClientActivity.this.findViewById(choseId);
				String str = (String) rb.getText();
				String[] values = str.split("\\|");
				String address=values[1];
				Log.e("address",values[1]);				
				BluetoothDevice btDev = adapter.getRemoteDevice(address);				
				Intent selectDeviceIntent = new Intent(
						BluetoothTools.ACTION_SELECTED_DEVICE);
				selectDeviceIntent.putExtra(BluetoothTools.DEVICE,
						btDev);
				sendBroadcast(selectDeviceIntent);				
			}
		});
		// devicelistLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		deviceccrollLayout = (RadioGroup) findViewById(R.id.group);
		deviceccrollLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				choseId=checkedId;
				
				//choseId=group.getCheckedRadioButtonId();
				//choseId=group.check(checkedId));
				
			}
		});
//		deviceccrollLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

	@Override
	protected void onStop() {
		// 关闭后台Service
		Intent startService = new Intent(BluetoothTools.ACTION_STOP_SERVICE);
		sendBroadcast(startService);
		unregisterReceiver(broadcastReceiver);
		super.onStop();
	}
}
