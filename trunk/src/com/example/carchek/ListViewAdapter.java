package com.example.carchek;

import java.util.ArrayList;
import java.util.List;

import com.example.carchek.ImageAdapter.ViewHolder;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private Activity context;
	private List<Object> list;

	private int Type = 0;

	/*
	 * 0,string 1,<string,string>
	 */

	public ListViewAdapter(Activity context, List<Object> list) {
		this.context = context;
		this.list = list;
	}

	public ListViewAdapter(Activity context, List<Object> list, int type) {
		this.context = context;
		this.list = list;
		Type = type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		switch (Type) {
		case 0:
			TextView itemView = new TextView(context);
			itemView.setText((String) list.get(position));
			return itemView;
		case 1:
			LayoutInflater lay = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewHolder1 holder = null;
			if (convertView == null) {
				holder = new ViewHolder1();
				convertView = lay.inflate(R.layout.text_textitem, null);
				holder.title = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.value = (TextView) convertView
						.findViewById(R.id.textView2);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder1) convertView.getTag();
			}			
			
			List string2=(List)list.get(position);
			holder.title.setText((String)string2.get(0));
			holder.value.setText((String)string2.get(1));
			return convertView;
		default:
			return convertView;
		}
		

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void AddItem(Object item) {
		list.add(item);
		this.notifyDataSetChanged();
	}

	public List<Object> GetSource() {
		return list;
	}

	public final class ViewHolder1 {
		public TextView title;
		public TextView value;
		// public TextView info;
		// public Button viewBtn;
	}
}
