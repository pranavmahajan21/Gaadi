package com.mw.gaadi.extras;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.mw.gaadi.activity.MainActivity;
import com.mw.gaadi.model.ServiceCenter;
import com.mw.gaadi.service.CityService;
import com.mw.gaadi.service.CompanyService;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

public class MyApp extends Application {

	public static boolean SELECT_CO_LO_ACT = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;
	// public static boolean = false;

	List<ServiceCenter> serviceCenterList;
	List<ParseObject> cityListPO;
	List<ParseObject> companyListPO;
	List<ParseObject> jobsListPO;

	Intent serviceIntent1;
	Intent serviceIntent2;

	private void intiThings() {
		serviceCenterList = new ArrayList<ServiceCenter>();
		jobsListPO = new ArrayList<ParseObject>();
		cityListPO = new ArrayList<ParseObject>();
		companyListPO = new ArrayList<ParseObject>();

		serviceIntent1 = new Intent(this, CompanyService.class);
		serviceIntent2 = new Intent(this, CityService.class);
		startService(serviceIntent1);
		startService(serviceIntent2);

	}

	@Override
	public void onCreate() {
		super.onCreate();
		intiThings();
//		startService(serviceIntent);
		Parse.initialize(this, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

	public ServiceCenter convertPOToServiceCenter(ParseObject parseObject) {
		return new ServiceCenter(parseObject.getObjectId(),
				parseObject.getString("name"),
				parseObject.getString("address"),
				parseObject.getString("phoneNo"),
				getAddress(parseObject.getString("address")),
				parseObject.getParseUser("owner"));
	}

	public ParseObject convertServiceCenterToPO(ServiceCenter serviceCenter) {
		ParseObject parseObject = new ParseObject("ServiceCenter");
		parseObject.setObjectId(serviceCenter.getObjectID());
		parseObject.put("name", serviceCenter.getName());
		parseObject.put("address", serviceCenter.getAddress());
		parseObject.put("phoneNo", serviceCenter.getPhoneNo());
		parseObject.put("owner", serviceCenter.getOwner());
		return parseObject;
	}

	private Address getAddress(String address) {
		Geocoder geocoder = new Geocoder(this);
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocationName(address, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (addresses.size() > 0) {
			System.out.println(">>>>>>right");
			return addresses.get(0);
		} else {
			System.out.println("<<<<<<left");
			return null;
		}
	}

	public List<ServiceCenter> getServiceCenterList() {
		return serviceCenterList;
	}

	public void setServiceCenterList(List<ServiceCenter> serviceCenterList) {
		this.serviceCenterList = serviceCenterList;
	}

	public List<ParseObject> getJobsListPO() {
		return jobsListPO;
	}

	public void setJobsListPO(List<ParseObject> jobsListPO) {
		this.jobsListPO = jobsListPO;
	}

	public List<ParseObject> getCityListPO() {
		return cityListPO;
	}

	public void setCityListPO(List<ParseObject> cityListPO) {
		this.cityListPO = cityListPO;
	}

	public List<ParseObject> getCompanyListPO() {
		return companyListPO;
	}

	public void setCompanyListPO(List<ParseObject> companyListPO) {
		this.companyListPO = companyListPO;
	}
}
