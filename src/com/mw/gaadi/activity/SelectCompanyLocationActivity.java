package com.mw.gaadi.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.mw.gaadi.R;
import com.mw.gaadi.adapter.SpinnerCustomAdapter;
import com.mw.gaadi.extras.MyApp;
import com.parse.ParseObject;

public class SelectCompanyLocationActivity extends Activity {

	Intent nextIntent;

	Spinner spinnerCompany;
	Spinner spinnerCity;

	List<ParseObject> companyListPO;
	List<ParseObject> cityListPO;

	List<String> listCompany;
	List<String> listCity;

	SpinnerCustomAdapter adapterCompany;
	SpinnerCustomAdapter adapterCity;

	MyApp myApp;

	private BroadcastReceiver companyReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			companyListPO = myApp.getCompanyListPO();

			listCompany = new ArrayList<String>();
			for (int i = 0; i < companyListPO.size(); i++) {
				listCompany.add(i, companyListPO.get(i).getString("name"));
			}
			adapterCompany = new SpinnerCustomAdapter(
					SelectCompanyLocationActivity.this,
					android.R.layout.simple_spinner_item, listCompany);
			adapterCompany
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerCompany.setAdapter(adapterCompany);
			spinnerCompany.refreshDrawableState();
		}
	};

	private BroadcastReceiver cityReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			cityListPO = myApp.getCityListPO();

			listCity = new ArrayList<String>();
			for (int i = 0; i < cityListPO.size(); i++) {
				listCity.add(i, cityListPO.get(i).getString("name"));
			}
			adapterCity = new SpinnerCustomAdapter(
					SelectCompanyLocationActivity.this,
					android.R.layout.simple_spinner_item, listCity);
			adapterCity
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerCity.setAdapter(adapterCity);
			spinnerCity.refreshDrawableState();
		}
	};

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		
		companyListPO = myApp.getCompanyListPO();
		listCompany = new ArrayList<String>();
		for (int i = 0; i < companyListPO.size(); i++) {
			listCompany.add(i, companyListPO.get(i).getString("name"));
		}
		adapterCompany = new SpinnerCustomAdapter(this,
				android.R.layout.simple_spinner_item, listCompany);
		adapterCompany
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCompany.setAdapter(adapterCompany);
		spinnerCompany.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(SelectCompanyLocationActivity.this,
						"" + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		cityListPO = myApp.getCityListPO();
		listCity = new ArrayList<String>();
		for (int i = 0; i < cityListPO.size(); i++) {
			listCity.add(i, cityListPO.get(i).getString("name"));
		}
		adapterCity = new SpinnerCustomAdapter(this,
				android.R.layout.simple_spinner_item, listCity);
		adapterCity
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCity.setAdapter(adapterCity);
		spinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(SelectCompanyLocationActivity.this,
						"" + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void findThings() {
		spinnerCompany = (Spinner) findViewById(R.id.spinner_company);
		spinnerCity = (Spinner) findViewById(R.id.spinner_city);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa);
		findThings();
		initThings();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MyApp.SELECT_CO_LO_ACT = true;
		LocalBroadcastManager.getInstance(this).registerReceiver(cityReceiver,
				new IntentFilter("city_list"));
		LocalBroadcastManager.getInstance(this).registerReceiver(
				companyReceiver, new IntentFilter("company_list"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this)
				.unregisterReceiver(cityReceiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				companyReceiver);
	}

	@Override
	protected void onStop() {
		super.onStop();
		MyApp.SELECT_CO_LO_ACT = false;
	}

	public void onContinue(View view) {
		nextIntent = new Intent(this, MapActivity.class);
		startActivity(nextIntent);
	}
}
