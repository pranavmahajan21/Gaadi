package com.mw.gaadi.receiver;

import org.json.JSONException;
import org.json.JSONObject;

import com.mw.gaadi.extras.MyApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

public class PushReceiver extends BroadcastReceiver {
	Intent nextIntent;
	MyApp myApp;

	SharedPreferences sharedPreferences;
	Editor editor;

	@Override
	public void onReceive(final Context context, Intent intent) {
		myApp = (MyApp) context.getApplicationContext();

		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context.getApplicationContext());
		editor = sharedPreferences.edit();

		Bundle extras = intent.getExtras();
		String message = extras != null ? extras.getString("com.parse.Data")
				: "";
		System.out.println(">>>>><<<<<<" + message);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(message);
			// Toast.makeText(context,
			// "Notif received - " + jsonObject.getInt("type"),
			// Toast.LENGTH_LONG).show();
//			nextIntent = new Intent(context, JustADialogActivity.class);
			nextIntent.putExtra("type", jsonObject.getInt("type"));
			nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (jsonObject.getInt("type") == 2) {
				if (jsonObject.getString("fromUserId").equals(
						"")) {
					// refresh chat list
					Intent nextIntent = new Intent("new_message");
					// add data
					nextIntent.putExtra("message",
							jsonObject.getString("message"));
					nextIntent.putExtra("fromUserId",
							jsonObject.getString("fromUserId"));
					LocalBroadcastManager.getInstance(context).sendBroadcast(
							nextIntent);
				}
			} else {
				context.startActivity(nextIntent);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
