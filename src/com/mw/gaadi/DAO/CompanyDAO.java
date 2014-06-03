package com.mw.gaadi.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CompanyDAO {

	ParseQuery<ParseObject> query;
	Context context;

	public CompanyDAO(Context context) {
		super();
		this.context = context;
		Parse.initialize(context, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		query = ParseQuery.getQuery("Company");
	}

	public List<ParseObject> getCompanies() {
		List<ParseObject> companyListPO = null;
		try {
			companyListPO = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return companyListPO;
	}

}
