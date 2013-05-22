package com.example.carchek;


import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ImageButton imagebutton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// this.setTitle("保险车联网手机终端");//设置参数
		/*************** 添加设置按钮点击事件 *******************************/
		imagebutton = (ImageButton) findViewById(R.id.imageButton1);
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
				serverIntent= new Intent(MainActivity.this,
						Oncarcheck.class);
				break;
				
			case 1:
				serverIntent= new Intent(MainActivity.this,
						ClientActivity.class);
				break;
			default:
				break;
			}
			startActivity(serverIntent);
		}
	}

}
