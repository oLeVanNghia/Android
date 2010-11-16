package com.player;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapAc extends MapActivity implements OnClickListener{
	MapView map;
	
	ImageButton now = null;
	ImageButton day = null;
	ImageButton week = null;
	
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.map);
		
		map = (MapView) findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);
		
		now = (ImageButton) findViewById(R.id.now);
		day = (ImageButton) findViewById(R.id.day);
		week = (ImageButton) findViewById(R.id.week);
		
		now.setOnClickListener(this);
		day.setOnClickListener(this);
		week.setOnClickListener(this);
		
		
//		
//		List<Overlay> overlay = map.getOverlays();
//		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
//		MapOverlay mapOverlay = new MapOverlay(drawable,map.getContext());
//		
//		GeoPoint point =new GeoPoint(19240000,-99120000);
//		OverlayItem overItem = new OverlayItem(point, "Marker","this is first demo");
//		mapOverlay.addOverlay(overItem);
//		
//		overlay.add(mapOverlay);
		maker(19240000, -99120000, "demo", "this is first demo");
		
		
	}
	public void reloadMap(){
		List<Overlay> overlay = map.getOverlays();
		overlay.clear();
	}
	public void maker(int longi, int lati,String title,String message){
		
		List<Overlay> overlay = map.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		MapOverlay mapOverlay = new MapOverlay(drawable,map.getContext());
		
		GeoPoint point =new GeoPoint(longi,lati);
		OverlayItem overItem = new OverlayItem(point, title, message);
		mapOverlay.addOverlay(overItem);
		
		overlay.add(mapOverlay);
		
		
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String song = "the rose";
		if(v == now){
			Client connect = new Client();
			connect.setProto("get");
			connect.setData(song);
			connect.setGetOption("now");
			
			Thread thread = new Thread(connect);
			thread.start();
			while(thread.isAlive());
			
			ArrayList<String []> results = connect.getInfors();
			
			reloadMap();
			for (String[] result : results) {
				int longitude = Integer.valueOf(result[6]);
				int latitude = Integer.valueOf(result[7]);
				String comment = result[8];
				String time = result[9];
				String name = result[0];
				
				maker(longitude, latitude, name, comment);
			}
			
			//map.refreshDrawableState();
		}
	}
}
