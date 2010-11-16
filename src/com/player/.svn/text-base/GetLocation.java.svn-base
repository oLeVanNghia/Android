package com.player;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

public class GetLocation extends MapActivity {
	final double DEG_RATE = 1E6;
	final GeoPoint YOKOHAMA = new GeoPoint(35443708, 139638026);
	Location location = null;
	LocationManager loManage = null;
	GeoPoint point = null;
	private Geocoder geocoder;

	public GetLocation() {
		loManage = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = null;

		location = loManage.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = loManage
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		point = null;

		if (location != null) {
			point = new GeoPoint((int) (location.getLongitude() * DEG_RATE),
					(int) (location.getLatitude() * DEG_RATE));
		}
		else{
			point = YOKOHAMA;
		}
	}
	public int getLongitude(){
		if(location!=null)
			return (int)(location.getLongitude()*DEG_RATE);
		return 0;
	}
	public int getLatitude(){
		if(location!=null)
			return (int)(location.getLatitude()*DEG_RATE);
		return 0;
	}
	public String getAddress(){
		geocoder = new Geocoder(this, Locale.JAPAN);
		StringBuffer sb = null;
		try {
			List<Address> listAddress = geocoder.getFromLocation(location.getLongitude(), location.getLatitude(), 1);
			Address add = (Address) listAddress.get(0);
			String s;
			sb = new StringBuffer();
			for(int i=1; i < 16; i++){
				s = add.getAddressLine(i);
				if(s==null)
					break;
				sb.append(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
