package com.mw.gaadi.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mw.gaadi.R;
import com.mw.gaadi.DAO.JobSheetDAO;
import com.mw.gaadi.adapter.JobsAdapter;
import com.mw.gaadi.extras.CreateDialog;
import com.mw.gaadi.extras.MyApp;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

public class WelcomeActivity extends Activity {

	TextView welcomeTV;
	ListView jobLV;

	MyApp myApp;

	CreateDialog createDialog;
	ProgressDialog progressDialog;

	// ServiceCenterDAO serviceDAO;
	JobSheetDAO jobDAO;

	JobsAdapter adapter;
	List<ParseObject> jobsListPO;

	Intent nextIntent;

	private void findThings() {
		welcomeTV = (TextView) findViewById(R.id.welcome_TV);
		jobLV = (ListView) findViewById(R.id.job_LV);
	}

	private void initThings() {
		myApp = (MyApp) getApplicationContext();
		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog(null,
				"Fetching Jobs", true, null);

		// serviceDAO = new ServiceCenterDAO(this);
		jobDAO = new JobSheetDAO(this);
		nextIntent = new Intent(this, JobDetailsActivity.class);
		jobLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextIntent.putExtra("position", position);
				startActivity(nextIntent);
			}

		});
	}

	private void initView() {
		welcomeTV
				.setText("Welcome " + ParseUser.getCurrentUser().getUsername());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		findThings();
		initThings();
		initView();
		progressDialog.show();
		FetchJobsAsyn jobsAsyn = new FetchJobsAsyn();
		jobsAsyn.execute(new String[] { "Hello World" });
	}

	private class FetchJobsAsyn extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			List<ParseObject> jobsListPO = jobDAO.getJobsForServiceCenter(null);
			myApp.setJobsListPO(jobsListPO);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			jobsListPO = myApp.getJobsListPO();
			if (adapter == null) {
				adapter = new JobsAdapter(WelcomeActivity.this, jobsListPO);
				jobLV.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		}// onPostExecute
	}// FetchJobsAsyn

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Toast.makeText(this, "onOptionsItemSelected", Toast.LENGTH_SHORT)
				.show();
		System.out.println(">>>>>>>onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.respond_menu_item:
			nextIntent = new Intent(this, JobDetailsActivity.class);
			startActivity(nextIntent);
			return true;
		case R.id.logout_menu_item:
			PushService.unsubscribe(this, ParseUser.getCurrentUser()
					.getUsername());
			ParseUser.logOut();
			nextIntent = new Intent(this, MapActivity.class);
			nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(nextIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
