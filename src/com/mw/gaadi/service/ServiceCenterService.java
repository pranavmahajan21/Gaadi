package com.mw.gaadi.service;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mw.gaadi.DAO.ServiceCenterDAO;
import com.mw.gaadi.extras.MyApp;
import com.mw.gaadi.model.ServiceCenter;
import com.parse.ParseObject;

public class ServiceCenterService extends IntentService {

	ServiceCenterDAO dao;
	MyApp myApp;

	public ServiceCenterService() {
		super("ServiceCenterService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		dao = new ServiceCenterDAO(this);
		myApp = (MyApp) this.getApplicationContext();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		List<ParseObject> serviceCenterPOList = dao.getServiceCenters();
		List<ServiceCenter> serviceCenterList = new ArrayList<ServiceCenter>();
		for (int i = 0; i < serviceCenterPOList.size(); i++) {
			serviceCenterList.add(myApp
					.convertPOToServiceCenter(serviceCenterPOList.get(i)));
		}
		if (serviceCenterPOList != null) {
			myApp.setServiceCenterList(serviceCenterList);
			System.out.println(">>>>>>>" + serviceCenterPOList.size());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Intent nextIntent = new Intent("service_center_list");
		LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
	}
}
