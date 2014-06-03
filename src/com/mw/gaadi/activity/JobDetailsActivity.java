package com.mw.gaadi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mw.gaadi.R;
import com.mw.gaadi.extras.MyApp;

public class JobDetailsActivity extends Activity {

	EditText chassisET;

	MyApp myApp;
	Intent previousIntent;

	private void findThings() {
		chassisET = (EditText) findViewById(R.id.chassis_ET);
	}

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		previousIntent = getIntent();
	}

	private void initView() {
		if (previousIntent.hasExtra("position")) {
			chassisET.setText(myApp.getJobsListPO()
					.get(previousIntent.getIntExtra("position", 0))
					.getString("chassisID"));
			chassisET.setKeyListener(null);
			chassisET.setFocusable(false);
		} else {
			chassisET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_grey_radius));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.job_details);
		findThings();
		initThings();
		initView();

		((RelativeLayout) findViewById(R.id.job_details_RL))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});
	}

	public void onRespond(View view) {
		finish();
	}

}
