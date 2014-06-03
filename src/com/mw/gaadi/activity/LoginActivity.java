package com.mw.gaadi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mw.gaadi.R;
import com.mw.gaadi.DAO.UserDAO;
import com.mw.gaadi.extras.MyApp;
import com.parse.ParseUser;
import com.parse.PushService;

public class LoginActivity extends Activity {

	EditText usernameET;
	EditText passwordET;
	TextView errorMsgTV;

	ProgressBar progressBarPB;
	UserDAO userDAO;
	MyApp myApp;

	Intent nextIntent;

	SharedPreferences sharedPreferences;
	Editor editor;

	private void findThings() {
		usernameET = (EditText) findViewById(R.id.username_ET);
		passwordET = (EditText) findViewById(R.id.password_ET);
		progressBarPB = (ProgressBar) findViewById(R.id.progressBar_PB);
		errorMsgTV = (TextView) findViewById(R.id.error_msg_TV);
	}

	private void myOwnListeners() {
		usernameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				usernameET.setError(null);
			}
		});

		passwordET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				passwordET.setError(null);
			}
		});
	}

	private void initThings() {
		userDAO = new UserDAO(this);
		myApp = (MyApp) getApplicationContext();
		// globalVariable = (GlobalVariable) getApplicationContext();
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		editor = sharedPreferences.edit();
		// nextIntent = new Intent(LoginActivity.this, MainActivity.class);

		passwordET
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							onLogin(null);
						}
						return false;
					}
				});

		nextIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_page);
		findThings();
		myOwnListeners();
		initThings();
		staticBullshit();

		if (ParseUser.getCurrentUser() != null) {
			System.out
					.println(">>>>>>> LoginActivity::onCreate -> user is present in preferences");
			finish();
			startActivity(nextIntent);
		}

		((RelativeLayout) findViewById(R.id.login_page_RL))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});

	}

	private void staticBullshit() {
		usernameET.setText("user1");
		passwordET.setText("qqqq");
	}

	public void onLogin(View view) {
		if (!validate())
			return;
		progressBarPB.setVisibility(View.VISIBLE);
		LoginUserAsynTask asynTask = new LoginUserAsynTask();
		asynTask.execute(new String[] { "hello world" });
	}

	private boolean validate() {
		boolean bool = true;
		if (usernameET.getText().toString().trim().length() == 0) {
			usernameET.setError("Enter a user name");
			bool = false;
		}
		if (usernameET.getText().length() == 0) {
			passwordET.setError("Enter a password");
			bool = false;
		}
		return bool;
	}

	private class LoginUserAsynTask extends AsyncTask<String, Void, ParseUser> {
		// ParseUser user;

		@SuppressLint("DefaultLocale")
		@Override
		protected ParseUser doInBackground(String... params) {
			ParseUser user = userDAO.loginUser(usernameET.getText().toString()
					.trim().toLowerCase(), passwordET.getText().toString());
			return user;
		}

		@Override
		protected void onPostExecute(ParseUser user) {
			super.onPostExecute(user);
			progressBarPB.setVisibility(View.INVISIBLE);
			if (user != null) {
				System.out
						.println(">>>>>>> LoginActivity::onPostCreate() - user is "
								+ user.getUsername());
				// collectUserData();
				PushService.subscribe(LoginActivity.this, ParseUser
						.getCurrentUser().getUsername(), MainActivity.class);
				finish();
				startActivity(nextIntent);
			} else {
				System.out
						.println(">>>>>>> LoginActivity::onPostCreate() - user is null");
				errorMsgTV.setText("Incorrect username or password");
				errorMsgTV.setVisibility(View.VISIBLE);
			}
		}

	}

}
