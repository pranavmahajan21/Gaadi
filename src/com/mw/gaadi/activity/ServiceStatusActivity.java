package com.mw.gaadi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.mw.gaadi.R;

public class ServiceStatusActivity extends Activity {

	RadioGroup radioGroup;
	ImageView arrowIV;
	boolean boo = false;

	private void findThings() {
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		arrowIV = (ImageView) findViewById(R.id.arrow_IV);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_status);
		findThings();
	}

	public void onFeedback(View view) {
		boo = !boo;
		if (boo) {
			radioGroup.setVisibility(View.VISIBLE);
			arrowIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down_black));
		} else {
			radioGroup.setVisibility(View.GONE);
			arrowIV.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_black));
		}
	}

}
