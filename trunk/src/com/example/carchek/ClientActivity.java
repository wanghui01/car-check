package com.example.carchek;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import com.ds.bluetoothUtil.BluetoothClientService;
import com.ds.bluetoothUtil.BluetoothTools;
import com.ds.bluetoothUtil.TransmitBean;

public class ClientActivity extends Activity {

	private ToggleButton setToggle;
	private Button sreachBtn;
	private Button connectBtn;
	private ListView seachListView;
	private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
	//private List<String> BlueNameList = new ArrayList<String>();
	private ListViewAdapter blueNameAdapter;
	// �㲥������
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
				// δ�����豸
				// serversText.append("not found device\r\n");

			} else if (BluetoothTools.ACTION_NO_DEVICE.equals(action)) {
				// showInfo(R.string.action_no_device);
				Toast.makeText(ClientActivity.this, R.string.action_no_device,
						Toast.LENGTH_LONG).show();
			} else if (BluetoothTools.ACTION_FOUND_DEVICE.equals(action)) {
				// ��ȡ���豸����
				BluetoothDevice device = (BluetoothDevice) intent.getExtras()
						.get(BluetoothTools.DEVICE);
				if (deviceList.contains(device)) {
					return;
				}
				deviceList.add(device);
				blueNameAdapter.AddItem(device.getName());
				// serversText.append(device.getName() + "\r\n");

			} else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
				// ���ӳɹ�
				// serversText.append("���ӳɹ�");
				// sendBtn.setEnabled(true);
				Toast.makeText(ClientActivity.this, "���ӳɹ�", Toast.LENGTH_LONG)
						.show();
			} else if (BluetoothTools.ACTION_DATA_TO_GAME.equals(action)) {
				// ��������
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
		// ����豸�б�
		deviceList.clear();

		// ������̨service
		Intent startService = new Intent(ClientActivity.this,
				BluetoothClientService.class);
		startService(startService);

		// ע��BoradcasrReceiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);
		intentFilter.addAction(BluetoothTools.ACTION_FOUND_DEVICE);
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
		seachListView = (ListView) findViewById(R.id.listView1);
		setToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (null != adapter) {
					if (isChecked) {
						adapter.enable();
					} else {
						adapter.disable(); // ͬ���Ĺر�WIFiΪ;
					}
					setToggle.setChecked(isChecked);
				} else {
					setToggle.setChecked(false);
				}
			}

		});
		List<Object> source = new ArrayList<Object>();
		blueNameAdapter=new ListViewAdapter(this,source);
		seachListView.setAdapter(blueNameAdapter); 

		/*
		 * sreachBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // ������Ϣ //if
		 * ("".equals(sendEditText.getText().toString().trim())) {
		 * //Toast.makeText(ClientActivity.this, "���벻��Ϊ��", //
		 * Toast.LENGTH_SHORT).show(); //} else { // ������Ϣ TransmitBean data =
		 * new TransmitBean(); //data.setMsg(sendEditText.getText().toString());
		 * Intent sendDataIntent = new Intent(
		 * BluetoothTools.ACTION_DATA_TO_SERVICE);
		 * sendDataIntent.putExtra(BluetoothTools.DATA, data);
		 * sendBroadcast(sendDataIntent); //} } });
		 */

		sreachBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ʼ����
				
				Intent startSearchIntent = new Intent(
						BluetoothTools.ACTION_START_DISCOVERY);
				sendBroadcast(startSearchIntent);
			}
		});
		connectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ѡ���һ���豸
				if (ListView.INVALID_POSITION == seachListView
						.getSelectedItemPosition()) {
					Toast.makeText(ClientActivity.this,
							R.string.action_no_select, Toast.LENGTH_LONG)
							.show();
					return;
				}
				Intent selectDeviceIntent = new Intent(
						BluetoothTools.ACTION_SELECTED_DEVICE);
				selectDeviceIntent.putExtra(BluetoothTools.DEVICE,
						deviceList.get(seachListView.getSelectedItemPosition()));
				sendBroadcast(selectDeviceIntent);
			}
		});
	}

	@Override
	protected void onStop() {
		// �رպ�̨Service
		Intent startService = new Intent(BluetoothTools.ACTION_STOP_SERVICE);
		sendBroadcast(startService);
		unregisterReceiver(broadcastReceiver);
		super.onStop();
	}
}
