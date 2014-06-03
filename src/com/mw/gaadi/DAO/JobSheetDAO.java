package com.mw.gaadi.DAO;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.mw.gaadi.activity.MainActivity;
import com.mw.gaadi.extras.MyApp;
import com.mw.gaadi.model.ServiceCenter;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

public class JobSheetDAO {

	ParseQuery<ParseObject> query;
	Context context;
	MyApp myApp;

	ServiceCenterDAO serviceDAO;

	SharedPreferences sharedPreferences;
	Editor editor;

	public JobSheetDAO(Context context) {
		super();
		this.context = context;
		myApp = (MyApp) context.getApplicationContext();

		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context.getApplicationContext());
		editor = sharedPreferences.edit();

		serviceDAO = new ServiceCenterDAO(context);
		Parse.initialize(context, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		query = ParseQuery.getQuery("JobSheet");
	}

	public void saveJobSheet(final String chassisID, String registrationID,
			String name, String mobileNo, String email,
			ServiceCenter serviceCenter) {
		System.out.println("message is  : " + mobileNo);
		ParseObject parseObject = new ParseObject("JobSheet");
		parseObject.put("chassisID", chassisID);
		parseObject.put("registrationID", registrationID);
		parseObject.put("name", name);
		parseObject.put("mobileNo", mobileNo);
		parseObject.put("email", email);
		parseObject.put("serviceCenter",
				myApp.convertServiceCenterToPO(serviceCenter));

		// try {
		// parseObject.save();
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		parseObject.saveEventually(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				Toast.makeText(context, "Your service request has been sent.",
						Toast.LENGTH_SHORT).show();
				if (sharedPreferences.contains("chassisID")) {
					editor.putString("chassisID",
							sharedPreferences.getString("chassisID", "") + "^^"
									+ chassisID);
				} else {
					editor.putString("chassisID", chassisID);
				}
				editor.commit();

			}
		});
		PushService.subscribe(context, chassisID, MainActivity.class);
		JSONObject data = new JSONObject();
		try {
			data.put("action", "com.mw.biker.JOB_SHEET");
			data.put("type", 1);
			data.put("alert", "asdf");
			// data.put("fromUserId", fromPU.getObjectId());
			// data.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ParsePush push = new ParsePush();
		push.setChannel(serviceCenter.getOwner().getUsername());
		// Notice we use setChannels not setChannel
		push.setData(data);
		push.sendInBackground();
	}

	public List<ParseObject> getJobsForServiceCenter(ParseObject serviceCenterPO) {

		List<ParseObject> jobsListPO = null;

		if (serviceCenterPO == null) {
			// fetch service center
			serviceCenterPO = serviceDAO.getServiceCenterForOwner();
			if (serviceCenterPO == null) {
				return null;
			}
		}

		query.whereEqualTo("serviceCenter", serviceCenterPO);

		try {
			jobsListPO = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jobsListPO;
	}
}
