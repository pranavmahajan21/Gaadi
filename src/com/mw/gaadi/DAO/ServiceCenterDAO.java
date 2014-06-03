package com.mw.gaadi.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ServiceCenterDAO {

	ParseQuery<ParseObject> query;
	Context context;

	public ServiceCenterDAO(Context context) {
		super();
		this.context = context;
		Parse.initialize(context, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		query = ParseQuery.getQuery("ServiceCenter");
	}

	public List<ParseObject> getServiceCenters() {
		List<ParseObject> serviceCenterListPO = null;
		query.include("owner");
		try {
			serviceCenterListPO = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return serviceCenterListPO;
	}

	public ParseObject getServiceCenterForOwner() {
		List<ParseObject> serviceCenterListPO = null;
		query.whereEqualTo("owner", ParseUser.getCurrentUser());
		try {
			serviceCenterListPO = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (serviceCenterListPO != null & serviceCenterListPO.size() > 0)
			return serviceCenterListPO.get(0);
		else
			return null;
	}
}
