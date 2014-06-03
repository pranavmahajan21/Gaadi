package com.mw.gaadi.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.gaadi.R;
import com.parse.ParseObject;

public class JobsAdapter extends BaseAdapter {

	private Context context;
	private List<ParseObject> jobsList;

	LayoutInflater inflater;

	public JobsAdapter(Context context, List<ParseObject> jobsList) {
		super();
		this.context = context;
		this.jobsList = jobsList;
	}

	static class ViewHolder {
		protected TextView chassisTV;
		protected TextView dateTV;
		protected ImageView tickIV;
	}

	public void swapData(List<ParseObject> jobsList)
	{
		this.jobsList = jobsList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(com.mw.gaadi.R.layout.job_element, parent, false);

			viewHolder.chassisTV = (TextView) convertView
					.findViewById(R.id.chassis_TV);
			viewHolder.dateTV = (TextView) convertView
					.findViewById(R.id.date_TV);
			viewHolder.tickIV = (ImageView) convertView
					.findViewById(R.id.tick_IV);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ParseObject tempJob = jobsList.get(position);
		viewHolder.chassisTV.setText(tempJob.getString("name"));
		viewHolder.dateTV.setText(getDisplayDate(tempJob.getCreatedAt()));

		return convertView;
	}

	@Override
	public int getCount() {
		return jobsList.size();
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
