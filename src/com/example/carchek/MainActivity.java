package com.example.carchek;


import com.amap.api.search.route.Route;

import android.media.MediaRouter.RouteInfo;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ImageButton imagebutton;
	private ImageView imageView1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// this.setTitle("保险车联网手机终端");//设置参数
		/*************** 添加设置按钮点击事件 *******************************/
		imagebutton = (ImageButton) findViewById(R.id.imageButton1);
		imageView1=(ImageView)findViewById(R.id.imageView1);
		imageView1.setAlpha(20);
		// imagebutton.Click += new ImageClickEventHandler(imgbtn_Click);
		imagebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent serverIntent = new Intent(MainActivity.this,
						ClientActivity.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(serverIntent);
			}
		});

		GridView gridview = (GridView) findViewById(R.id.gridView1);
		int[] img = new int[8];
		int[] title = new int[8];
		for (int i = 0; i < 8; i++) {
			img[i] = getResources().getIdentifier("menu_" + String.valueOf(i),
					"drawable", "com.example.carchek");
			title[i] = getResources().getIdentifier(
					"menu_" + String.valueOf(i), "string",
					"com.example.carchek");
		}
		gridview.setAdapter(new ImageAdapter(this, img, title));
		gridview.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// ViewHolder item = (ViewHolder) v.getTag();
			Intent serverIntent = null;
			switch (position) {
			case 0:
				serverIntent = new Intent(MainActivity.this,
						Oncarcheck.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				
				break;				
			case 1:
				Log.i("页面代号","i");
				serverIntent = new Intent(MainActivity.this,
						Routeinfo.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				
				break;
			case 2:				
				Toast.makeText(MainActivity.this, "请先连接车辆，获得设备Id",
						Toast.LENGTH_LONG).show();					
				return; 
			case 3:
				serverIntent= new Intent(MainActivity.this,
						MapNow.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);						
				break;
			case 4:
							
				serverIntent= new Intent(MainActivity.this,
						Jiashixingwei.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);						
				break;
			case 5:				
				serverIntent= new Intent(MainActivity.this,
						Gzhang.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);						
				break;
			case 6:				
				Toast.makeText(MainActivity.this, "请先连接车辆，获得设备Id",
						Toast.LENGTH_LONG).show();					
				return;
			case 7:				
				Toast.makeText(MainActivity.this, "服务建设中。。",
						Toast.LENGTH_LONG).show();					
				return;
			default:
				return;
			}
			startActivity(serverIntent);
		}
	}

}
