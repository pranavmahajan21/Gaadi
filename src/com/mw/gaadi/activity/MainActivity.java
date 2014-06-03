package com.mw.gaadi.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import com.mw.gaadi.R;
import com.mw.gaadi.DAO.JobSheetDAO;
import com.mw.gaadi.adapter.SpinnerCustomAdapter;
import com.mw.gaadi.extras.MyApp;
import com.mw.gaadi.model.ServiceCenter;
import com.mw.gaadi.service.ServiceCenterService;

public class MainActivity extends Activity {
	// extends ActionBarActivity
//	private int year;
//	private int month;
//	private int day;
//	private int hour;
//	private int minute;

	static final int DATE_PICKER_ID_Start = 1;
	static final int DATE_PICKER_ID_end = 2;
	static final int TIME_PICKER_ID_start = 3;
	static final int TIME_PICKER_ID_end = 4;

//	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
//	SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
//	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");

	Date startDateTime = new Date();
	Date endDate;
	Date endDateTime;

	MyApp myApp;

	ScrollView jobSheetSV;
	EditText chassisET, registrationET, nameET, mobileET, emailET;
//	EditText startDateET, startTimeET;
	// EditText endDateET, endTimeET;
//	Spinner spinner;

	JobSheetDAO jobSheetDAO;

	List<ServiceCenter> serviceCenterList;
	List<String> list;
	// ArrayAdapter<String> adapter;
	SpinnerCustomAdapter adapter2;

	Intent nextIntent;
	Intent serviceIntent;

	int positionServiceCenter;

	SharedPreferences sharedPreferences;
	Editor editor;

	private BroadcastReceiver serviceCenterReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			serviceCenterList = myApp.getServiceCenterList();

			list = new ArrayList<String>();
			for (int i = 0; i < serviceCenterList.size(); i++) {
				list.add(i, serviceCenterList.get(i).getName());
				System.out.println(">>>>>>>"
						+ serviceCenterList.get(i).getAddress2());
			}
			adapter2 = new SpinnerCustomAdapter(MainActivity.this,
					android.R.layout.simple_spinner_item, list);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// adapter = new ArrayAdapter<String>(MainActivity.this,
			// android.R.layout.simple_spinner_item, list);
			// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			spinner.setAdapter(adapter2);
//			spinner.refreshDrawableState();
		}
	};

//	private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//		// when dialog box is closed, below method will be called.
//		@Override
//		public void onDateSet(DatePicker view, int selectedYear,
//				int selectedMonth, int selectedDay) {
//			startDateET.setError(null);
//			year = selectedYear;
//			month = selectedMonth;
//			day = selectedDay;
//			startDateTime = getDateFromCalendar(year, month, day);
//
//			System.out.println(">>>>>>> start date  :  " + startDateTime);
//
//			startDateET.setText(dateFormat.format(startDateTime));
//			startDateET.setVisibility(View.VISIBLE);
//		}
//	};
//
//	private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//		@Override
//		public void onDateSet(DatePicker view, int selectedYear,
//				int selectedMonth, int selectedDay) {
//			year = selectedYear;
//			month = selectedMonth;
//			day = selectedDay;
//			endDate = getDateFromCalendar(year, month, day);
//
//			System.out.println(">>>>>>> end date  :  " + endDate);
//
//			// endDateET.setText(dateFormat.format(endDate));
//			// endDateET.setVisibility(View.VISIBLE);
//		}
//	};
//
//	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
//
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minuteofHour) {
//			startTimeET.setError(null);
//			String hh, mm;
//			hour = hourOfDay;
//			if (hour < 10) {
//				hh = "0" + hour;
//			} else {
//				hh = "" + hour;
//			}
//			if (minuteofHour < 10) {
//				mm = "0" + minuteofHour;
//			} else {
//				mm = "" + minuteofHour;
//			}
//			minute = minuteofHour;
//			startTimeET.setText(hh + ":" + mm);
//		}
//	};
//
//	private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
//
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minuteofHour) {
//			// endTimeET.setError(null);
//			String hh, mm;
//			hour = hourOfDay;
//			if (hour < 10) {
//				hh = "0" + hour;
//			} else {
//				hh = "" + hour;
//			}
//			if (minuteofHour < 10) {
//				mm = "0" + minuteofHour;
//			} else {
//				mm = "" + minuteofHour;
//			}
//			minute = minuteofHour;
//			// endTimeET.setText(hh + ":" + mm);
//		}
//	};

	private void findThings() {
		chassisET = (EditText) findViewById(R.id.chassis_ET);
		registrationET = (EditText) findViewById(R.id.registration_ET);
		nameET = (EditText) findViewById(R.id.name_ET);
		mobileET = (EditText) findViewById(R.id.mobile_ET);
		emailET = (EditText) findViewById(R.id.email_ET);

//		startDateET = (EditText) findViewById(R.id.startDate_ET);
//		startTimeET = (EditText) findViewById(R.id.startTime_ET);
		// endDateET = (EditText) findViewById(R.id.endDate_ET);
		// endTimeET = (EditText) findViewById(R.id.endTime_ET);

//		spinner = (Spinner) findViewById(R.id.serviceCenter_Spinner);
	}

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		editor = sharedPreferences.edit();

		serviceCenterList = myApp.getServiceCenterList();
		list = new ArrayList<String>();
		for (int i = 0; i < serviceCenterList.size(); i++) {
			list.add(serviceCenterList.get(i).getName());
		}
		// adapter = new ArrayAdapter<String>(MainActivity.this,
		// android.R.layout.simple_spinner_item, list);
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2 = new SpinnerCustomAdapter(MainActivity.this,
				android.R.layout.simple_spinner_item, list);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner.setAdapter(adapter2);
//		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(MainActivity.this, "" + position,
//						Toast.LENGTH_SHORT).show();
//				positionServiceCenter = position;
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//
//			}
//		});

		serviceIntent = new Intent(this, ServiceCenterService.class);
		jobSheetDAO = new JobSheetDAO(this);

//		Calendar calendar = Calendar.getInstance();
//
//		year = calendar.get(Calendar.YEAR);
//		month = calendar.get(Calendar.MONTH);
//		day = calendar.get(Calendar.DAY_OF_MONTH);
//		hour = calendar.get(Calendar.HOUR_OF_DAY);
//		minute = calendar.get(Calendar.MINUTE);
	}

	private void onTouchListeners() {
//		startDateET.setOnTouchListener(new OnTouchListener() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				((EditText) v).setVisibility(View.GONE);
//
//				showDialog(DATE_PICKER_ID_Start);
//				return false;
//			}
//		});
//
		// endDateET.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// @SuppressWarnings("deprecation")
		// public boolean onTouch(View v, MotionEvent event) {
		// ((EditText) v).setVisibility(View.GONE);
		// showDialog(DATE_PICKER_ID_end);
		// return false;
		// }
		// });
//
//		startTimeET.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			@SuppressWarnings("deprecation")
//			public boolean onTouch(View v, MotionEvent event) {
//				showDialog(TIME_PICKER_ID_start);
//				return false;
//			}
//		});
//
		// endTimeET.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// @SuppressWarnings("deprecation")
		// public boolean onTouch(View v, MotionEvent event) {
		// showDialog(TIME_PICKER_ID_end);
		// return false;
		// }
		// });
	}

	@SuppressLint("NewApi")
	private void onFoucusListeners() {
		chassisET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					chassisET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_blue_radius));
				} else {
					chassisET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_grey_radius));
				}
			}
		});

		registrationET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					registrationET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_blue_radius));
				} else {
					registrationET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_grey_radius));
				}
			}
		});

		nameET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					nameET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_blue_radius));
				} else {
					nameET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_grey_radius));
				}
			}
		});

		mobileET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mobileET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_blue_radius));
				} else {
					mobileET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_grey_radius));
				}
			}
		});

		emailET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					emailET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_blue_radius));
				} else {
					emailET.setBackground(getResources().getDrawable(
							R.drawable.custom_bg_grey_radius));
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findThings();
		initThings();
		startService(serviceIntent);
		((ScrollView) findViewById(R.id.jobsheet_SV))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});
		onTouchListeners();
		onFoucusListeners();
	}

//	@Override
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		case DATE_PICKER_ID_Start:
//			return new DatePickerDialog(this, startDatePickerListener, year,
//					month, day);
//		case DATE_PICKER_ID_end:
//			return new DatePickerDialog(this, endDatePickerListener, year,
//					month, day);
//		case TIME_PICKER_ID_start:
//			return new TimePickerDialog(this, timePickerListener, hour, minute,
//					true);
//		case TIME_PICKER_ID_end:
//			return new TimePickerDialog(this, timePickerListener1, hour,
//					minute, true);
//		}
//		return null;
//	}

	public void onSave(View view) {
		if (!validate())
			return;
		jobSheetDAO.saveJobSheet(chassisET.getText().toString(), registrationET
				.getText().toString(), nameET.getText().toString(), mobileET
				.getText().toString(), emailET.getText().toString(),
				serviceCenterList.get(positionServiceCenter));

	}

	@SuppressLint("NewApi")
	private boolean validate() {
		boolean temp = true;
		chassisET.setBackground(getResources().getDrawable(
				R.drawable.custom_bg_grey_radius));
		registrationET.setBackground(getResources().getDrawable(
				R.drawable.custom_bg_grey_radius));
		nameET.setBackground(getResources().getDrawable(
				R.drawable.custom_bg_grey_radius));
		mobileET.setBackground(getResources().getDrawable(
				R.drawable.custom_bg_grey_radius));
		emailET.setBackground(getResources().getDrawable(
				R.drawable.custom_bg_grey_radius));

		if (chassisET.getText().toString().trim().length() == 0) {
			chassisET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_red_radius));
			temp = false;
		}
		if (registrationET.getText().toString().trim().length() == 0) {
			registrationET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_red_radius));
			temp = false;
		}
		if (nameET.getText().toString().trim().length() == 0) {
			nameET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_red_radius));
			temp = false;
		}
		if (mobileET.getText().toString().trim().length() == 0) {
			mobileET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_red_radius));
			temp = false;
		}
		if (emailET.getText().toString().length() == 0) {
			emailET.setBackground(getResources().getDrawable(
					R.drawable.custom_bg_red_radius));
			temp = false;
		}

		return temp;
	}

	private Date getDateFromCalendar(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, 0, 0, 0);
		return cal.getTime();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.my_services_menu_item:
			nextIntent = new Intent(this, MyServicesActivity.class);
			startActivity(nextIntent);
			return true;
		case R.id.login_menu_item:
			nextIntent = new Intent(this, LoginActivity.class);
			startActivity(nextIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(
				serviceCenterReceiver, new IntentFilter("service_center_list"));
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				serviceCenterReceiver);
	}
}
