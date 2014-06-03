package com.mw.gaadi.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mw.gaadi.R;
import com.mw.gaadi.extras.CreateDialog;
import com.mw.gaadi.extras.GPSTracker;
import com.mw.gaadi.extras.MapWrapperLayout;
import com.mw.gaadi.extras.MyApp;
import com.mw.gaadi.extras.OnInfoWindowElemTouchListener;
import com.mw.gaadi.model.ServiceCenter;
import com.mw.gaadi.service.ServiceCenterService;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	GPSTracker gpsTracker;

	double lat;
	double lon;

	Intent nextIntent, serviceIntent;

	MyApp myApp;

	CreateDialog createDialog;
	ProgressDialog progressDialog;

	private ViewGroup infoWindow;
	private TextView infoTitleTV;
	private TextView infoSnippet;
	private TextView infoPhone;
	private Button infoButton;
	private OnInfoWindowElemTouchListener infoButtonListener;

	private BroadcastReceiver serviceCenterReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			progressDialog.dismiss();

			BitmapDescriptor bitmapMarker = null;

			List<ServiceCenter> listServiceCenter = myApp
					.getServiceCenterList();
			if (listServiceCenter != null && listServiceCenter.size() > 0) {
				for (int i = 0; i < listServiceCenter.size(); i++) {
					Address tempAddress = listServiceCenter.get(i)
							.getAddress2();
					if (tempAddress != null) {
						// if (i == 0) {
						// bitmapMarker = BitmapDescriptorFactory
						// .defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
						// } else if (i == 1) {
						// bitmapMarker = BitmapDescriptorFactory
						// .defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
						// } else if (i == 2) {
						// bitmapMarker = BitmapDescriptorFactory
						// .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
						// }
						bitmapMarker = BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
						LatLng latLng = new LatLng(tempAddress.getLatitude(),
								tempAddress.getLongitude());
						googleMap
								.addMarker(new MarkerOptions()
										.position(latLng)
										.title(listServiceCenter.get(i)
												.getName())
										.icon(bitmapMarker)
										.snippet(
												listServiceCenter.get(i)
														.getAddress()));
						// googleMap
						// .setInfoWindowAdapter(new MyInfoWindowAdapter());

						// googleMap
						// .setOnInfoWindowClickListener(new
						// OnInfoWindowClickListener() {
						//
						// @Override
						// public void onInfoWindowClick(Marker arg0) {
						// Toast.makeText(MapActivity.this,
						// "success", Toast.LENGTH_SHORT)
						// .show();
						// nextIntent = new Intent(
						// MapActivity.this,
						// MainActivity.class);
						// startActivity(nextIntent);
						// }
						// });
					} else {
						Toast.makeText(MapActivity.this, "no address : " + i,
								Toast.LENGTH_SHORT).show();

					}
				}
			}// if
		}
	};

	// class MyInfoWindowAdapter implements InfoWindowAdapter {
	//
	// private final View myContentsView;
	//
	// MyInfoWindowAdapter() {
	// myContentsView = getLayoutInflater().inflate(
	// R.layout.map_custom_info_window, null);
	// }
	//
	// @Override
	// public View getInfoContents(Marker marker) {
	//
	// TextView titleTV = ((TextView) myContentsView
	// .findViewById(R.id.title_TV));
	// TextView snippetTV = ((TextView) myContentsView
	// .findViewById(R.id.snippet_TV));
	// TextView phoneTV = ((TextView) myContentsView
	// .findViewById(R.id.phone_TV));
	//
	// titleTV.setTypeface(Typeface.SERIF);
	// titleTV.setTextSize(20);
	// snippetTV.setTypeface(Typeface.SERIF);
	//
	// titleTV.setText(marker.getTitle());
	// snippetTV.setText(marker.getSnippet());
	// phoneTV.setText("9988998898");
	//
	// return myContentsView;
	// }
	//
	// @Override
	// public View getInfoWindow(Marker marker) {
	// return null;
	// }
	//
	// }

	private void initThings() {
		myApp = (MyApp) getApplicationContext();

		gpsTracker = new GPSTracker(this);
		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog(null,
				"Searching service centers near you.", true, null);
		serviceIntent = new Intent(this, ServiceCenterService.class);
	}

	public static int getPixelsFromDp(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		initThings();

		getMyCoordinates();

		try {
			// Loading map
			// no need for try-catch
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

		final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);
		mapWrapperLayout.init(googleMap, getPixelsFromDp(this, 39 + 20));

		this.infoWindow = (ViewGroup) getLayoutInflater().inflate(
				R.layout.map_custom_info_window, null);
		this.infoTitleTV = (TextView) infoWindow.findViewById(R.id.title_TV);
		this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet_TV);
		this.infoPhone = (TextView) infoWindow.findViewById(R.id.phone_TV);
		this.infoButton = (Button) infoWindow.findViewById(R.id.contact_B);

		this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
				getResources().getDrawable(R.drawable.custom_bg_opaque_radius),
				getResources().getDrawable(R.drawable.custom_bg_opaque_radius)) {
			@Override
			protected void onClickConfirmed(View v, Marker marker) {
				// Here we can perform some action triggered after clicking the
				// button
				Toast.makeText(MapActivity.this,
						marker.getTitle() + "'s button clicked!",
						Toast.LENGTH_SHORT).show();

				nextIntent = new Intent(MapActivity.this, MainActivity.class);
				startActivity(nextIntent);

			}
		};
		this.infoButton.setOnTouchListener(infoButtonListener);

		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				// Setting up the infoWindow with current's marker info
				infoTitleTV.setText(marker.getTitle());
				infoTitleTV.setTypeface(Typeface.SERIF, Typeface.BOLD);
				infoSnippet.setText(marker.getSnippet());
				infoPhone.setText("9898989898");
				infoButtonListener.setMarker(marker);

				// We must call this to set the current marker and infoWindow
				// references
				// to the MapWrapperLayout
				mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
				return infoWindow;
			}
		});

		progressDialog.show();
		startService(serviceIntent);

	}

	private void getMyCoordinates() {
		if (gpsTracker.canGetLocation()) {

			lat = gpsTracker.getLatitude();
			lon = gpsTracker.getLongitude();

			// \n is for new line
			Toast.makeText(getApplicationContext(),
					"Your Location is - \nLat: " + lat + "\nLong: " + lon,
					Toast.LENGTH_SHORT).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gpsTracker.showSettingsAlert();
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// googleMap.addMarker(new MarkerOptions().position(
			// new LatLng(lat, lon)).title("Hello Maps "));
			googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,
					lon)));
			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,
					lon));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

			googleMap.moveCamera(center);

			googleMap.animateCamera(zoom);

			// googleMap.setMyLocationEnabled(true);
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(
				serviceCenterReceiver, new IntentFilter("service_center_list"));
		getMyCoordinates();
		initilizeMap();
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				serviceCenterReceiver);
		// zzunregisterReceiver(serviceCenterReceiver);
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
		case R.id.login_menu_item:
			nextIntent = new Intent(this, LoginActivity.class);
			startActivity(nextIntent);
			return true;
		case R.id.my_services_menu_item:
			nextIntent = new Intent(this, MyServicesActivity.class);
			startActivity(nextIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onContact(View view) {
		Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
	}
}
