package com.mw.gaadi.activity;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RedirectActivity extends Activity {

	Intent nextIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ParseUser.getCurrentUser() != null) {

			nextIntent = new Intent(this, WelcomeActivity.class);
		} else {
			nextIntent = new Intent(this, MapActivity.class);
		}
		startActivity(nextIntent);
	}

}
