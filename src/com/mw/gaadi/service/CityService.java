package com.mw.gaadi.service;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mw.gaadi.DAO.CityDAO;
import com.mw.gaadi.extras.MyApp;
import com.parse.ParseObject;

public class CityService extends IntentService {

	CityDAO dao;
	MyApp myApp;

	public CityService() {
		super("CityService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		dao = new CityDAO(this);
		myApp = (MyApp) this.getApplicationContext();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		List<ParseObject> cityPOList = dao.getCities();
		if (cityPOList != null) {
			myApp.setCityListPO(cityPOList);
			System.out.println(">>>>>>>" + cityPOList.size());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Intent nextIntent = new Intent("city_list");
		LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
	}
}
