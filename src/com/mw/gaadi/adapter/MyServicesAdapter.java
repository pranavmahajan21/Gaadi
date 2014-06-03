package com.mw.gaadi.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.gaadi.R;

public class MyServicesAdapter extends BaseAdapter {

	private Context context;
	private String myServices[];

	LayoutInflater inflater;

	public MyServicesAdapter(Context context, String myServices[]) {
		super();
		this.context = context;
		this.myServices = myServices;
	}

	public void swapData(String myServices[]) {
		this.myServices = myServices;
	}

	static class ViewHolder {
		protected TextView chassisTV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.my_services_element,
					parent, false);

			viewHolder.chassisTV = (TextView) convertView
					.findViewById(R.id.chassis_TV);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.chassisTV.setText(myServices[position]);

		return convertView;
	}

	@Override
	public int getCount() {
		return myServices.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("SimpleDateFormat")
	public String getDisplayDate(Date date) {
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd MMM, hh:mm");
			String stringDate = simpleDateFormat.format(date);
			return stringDate;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"dd MMM, hh:mm");
		String stringDate = simpleDateFormat.format(new Date());
		return stringDate;

	}

}
