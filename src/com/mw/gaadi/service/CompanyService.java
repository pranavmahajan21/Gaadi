package com.mw.gaadi.service;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mw.gaadi.DAO.CompanyDAO;
import com.mw.gaadi.extras.MyApp;
import com.parse.ParseObject;

public class CompanyService extends IntentService {

	CompanyDAO dao;
	MyApp myApp;

	public CompanyService() {
		super("CompanyService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		dao = new CompanyDAO(this);
		myApp = (MyApp) this.getApplicationContext();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		List<ParseObject> companyPOList = dao.getCompanies();
		if (companyPOList != null) {
			myApp.setCompanyListPO(companyPOList);
			System.out.println(">>>>>>>" + companyPOList.size());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Intent nextIntent = new Intent("company_list");
		LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
	}
}
