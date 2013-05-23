package com.example.carchek;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Oncarcheck extends Activity {
	/** Called when the activity is first created. */
	private ListView List;
	private ListViewAdapter ValuesList;
	private boolean IsStop=true;
	public ZhuanpanView panView ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ontimecheck);
		List = (ListView) findViewById(R.id.listView1);
		List<Object> source = new ArrayList<Object>();
		ValuesList = new ListViewAdapter(this, source, 2);
		panView= (ZhuanpanView) findViewById(R.id.zhuanpanView);
		List.setAdapter(ValuesList);
		String[] mMenuText = new String[] { "油耗", "发动机水温", "进气管温度", "发动机负荷","氧传感器温度" };
		String[] mMenuSummary = new String[] { "0", "0", "0", "0", "0"};
		String[] ico = new String[] { "youhao", "shuiwen", "jinfengkou","fuzai", "yhl" };
		for (int i = 0; i < mMenuText.length; i++) {
			List string = new ArrayList();
			string.add(mMenuText[i]);
			string.add(mMenuSummary[i]);
			string.add(ico[i]);
			ValuesList.AddItem(string);
		}
		IsStop=false;
		handler.postDelayed(task, 2000);
	}

	private Handler handler = new Handler();
	private Runnable task = new Runnable() {
		private long count = 0;
		 
		@Override
		public void run() {
			// TODO Auto-generated method stub
			  Random rand = new Random();
		        
			//System.out.println(rand.nextInt(99)+1);
			  float x=rand.nextInt(20)+panView.AngleNow-10;
			  if(x>270) x=x-45;
			  if(x<-45) x=x+45;
			  panView.StarRotate(x);
		    	 Log.i("start",String.valueOf(panView.AngleNow));
			if (!IsStop) {
				handler.postDelayed(this, 2000);
			}
				
			// tvCounter.setText("Count: " + count);
		}
	};
	/*protected void onStop()
	{
		IsStop=true;
	}*/

}
