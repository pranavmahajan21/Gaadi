package com.mw.gaadi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mw.gaadi.R;
import com.mw.gaadi.adapter.MyServicesAdapter;
import com.mw.gaadi.extras.MyApp;

public class MyServicesActivity extends Activity {
	ListView myServicesLV;
	MyApp myApp;
	SharedPreferences sharedPreferences;
	Editor editor;

	MyServicesAdapter adapter;
	String aa[];

	Intent nextIntent;

	private void findThings() {
		myServicesLV = (ListView) findViewById(R.id.my_services_LV);
	}

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		editor = sharedPreferences.edit();

		if (sharedPreferences.contains("chassisID")) {
			System.out.println(sharedPreferences.getString("chassisID", null));
			aa = sharedPreferences.getString("chassisID", null).split("\\^\\^");
		}
Toast.makeText(this, aa.length+"", Toast.LENGTH_SHORT).show();
		adapter = new MyServicesAdapter(this, aa);
		myServicesLV.setAdapter(adapter);
		myServicesLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				nextIntent = new Intent(MyServicesActivity.this,
						ServiceStatusActivity.class);
				startActivity(nextIntent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_services_activity);
		findThings();
		initThings();
	}

}
