package com.bloodante.hereby;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class HerebyApplication extends Application implements LocationListener {
	private static String TAG = HerebyApplication.class.toString();
	
	private String mLocationProvider;
	LocationManager mLocationManager;
	double mLat, mLon;
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location lastLocation = mLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		mLocationProvider = LocationManager.GPS_PROVIDER;
		if (lastLocation == null) {
			lastLocation = mLocationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			mLocationProvider = LocationManager.NETWORK_PROVIDER;
		}
		if (lastLocation != null) {
			Log.d(TAG, "location:" + lastLocation.toString());
			onLocationChanged(lastLocation);
		} else {
			mLocationProvider = LocationManager.GPS_PROVIDER;
			Log.d(TAG, "Last Location is null.");
		}
	}
	
	public double getLat() { return mLat; }
	public double getLon() { return mLon; }

	public LocationManager getLocationManager() {
		return mLocationManager;
	}
	
	public void startUpdatingLocation() {
		mLocationManager.requestLocationUpdates(mLocationProvider, 1000, 0.01f, this);
	}
	
	public void stopUpdatingLocation() {
		mLocationManager.removeUpdates(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		mLocationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		this.mLat = location.getLatitude();
		this.mLon = location.getLongitude();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		mLocationProvider = provider;
		startUpdatingLocation();
	}

	@Override
	public void onProviderEnabled(String provider) {
		mLocationProvider = provider;
		startUpdatingLocation();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, 
				getResources().getString(R.string.toast_gps_disabled),
				Toast.LENGTH_SHORT).show();
	}

}
