package com.mw.gaadi.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerCustomAdapter extends ArrayAdapter<String> {

	Context context;

//	public void swapData(List<String> objects) {
//	}

	public SpinnerCustomAdapter(Context context, int resource,
			List<String> objects) {
		super(context, resource, objects);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = (TextView) super.getView(position, convertView,
				parent);
		textView.setTypeface(Typeface.SERIF);
		return textView;

	}

}
