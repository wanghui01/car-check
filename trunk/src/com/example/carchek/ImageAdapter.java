package com.example.carchek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	// ����Context
	private Context mContext;
	// ������������ ��ͼƬԴ
	private int[] mImageIds;
	private int[] mtitle;

	public ImageAdapter(Context c) {
		mContext = c;

	}

	// idΪ��Դarray��id
	public ImageAdapter(Context c, int[] image, int[] title) {
		mContext = c;
		mImageIds = image;
		mtitle = title;
	}

	// ��ȡͼƬ�ĸ���
	public int getCount() {
		return mImageIds.length;
	}

	// ��ȡͼƬ�ڿ��е�λ��
	public Object getItem(int position) {
		return position;
	}

	// ��ȡͼƬID
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater lay = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = lay.inflate(R.layout.mianitem, null);
			holder.title = (TextView) convertView.findViewById(R.id.ItemText);			
			holder.img=(ImageView)convertView.findViewById(R.id.ItemImage);
			ImageView tholder=(ImageView)convertView.findViewById(R.id.imageView1);
			tholder.setAlpha(70);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.img.setImageResource(mImageIds[position]);
		holder.title.setText(mtitle[position]);
		return convertView;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		// public TextView info;
		// public Button viewBtn;
	}

}
