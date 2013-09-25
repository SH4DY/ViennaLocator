package com.shady.viennalocator.geoData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class GeoDataManager implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Update frequency in milliseconds
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	private static GeoDataManager _instance;
	private static Context _context;
	private LocationClient _locationClient;
	private LocationRequest _locationRequest;
	private Location _location;

	public static GeoDataManager getInstance(Context context) {
		if (_instance == null) {
			_context = context;
			_instance = new GeoDataManager();
		}
		return _instance;
	}

	private GeoDataManager() {
		_locationClient = new LocationClient(_context, this, this);
		_locationRequest = LocationRequest.create();
		_locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		_locationRequest.setInterval(UPDATE_INTERVAL);
		_locationRequest.setFastestInterval(FASTEST_INTERVAL);
	}

	public Location getLiveLocation() {
		return _location;
	}

	public Address getAddressFromLocation(Location location) {
		Geocoder geo = new Geocoder(_context);
		ArrayList<Address> addresses = null;
		try {
			addresses = (ArrayList<Address>)geo.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addresses.get(0);
	}

	@Override
	public void onLocationChanged(Location location) {
		_location = location;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d("Shady", "GeoDataManager conntection failed");
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(_context, "Play Services connected", Toast.LENGTH_SHORT)
				.show();
		Log.d("Shady", "GeoDataManager connected");
		_locationClient.requestLocationUpdates(_locationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Log.d("Shady", "GeoDataManager disconnected");
	}

}
