package com.mw.gaadi.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CityDAO {

	ParseQuery<ParseObject> query;
	Context context;

	public CityDAO(Context context) {
		super();
		this.context = context;
		Parse.initialize(context, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		query = ParseQuery.getQuery("City");
	}

	public List<ParseObject> getCities() {
		List<ParseObject> cityListPO = null;
		try {
			cityListPO = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cityListPO;
	}

}
