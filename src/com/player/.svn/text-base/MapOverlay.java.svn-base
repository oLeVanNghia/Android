package com.player;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlay extends ItemizedOverlay<OverlayItem>{
	private ArrayList<OverlayItem> mOver = new ArrayList<OverlayItem>();
	private Context context;
	public MapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	public MapOverlay(Drawable marker, Context context){
		super(boundCenterBottom(marker));
		this.context = context;
	}
	public void addOverlay(OverlayItem overlay){
		mOver.add(overlay);
		populate();
	}
	@Override
	protected OverlayItem createItem(int arg0) {
		// TODO Auto-generated method stub
		return mOver.get(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOver.size();
	}
	@Override
	protected boolean onTap(int index){
		OverlayItem item = mOver.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}
