package com.player;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MediaEx extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent().setClass(this, PlayerActivity.class);
        spec = tabHost.newTabSpec("Player").setIndicator("Player",
        		res.getDrawable(R.drawable.player)).setContent(intent);
        tabHost.addTab(spec);
        
      //Do the same for the other tabs
        intent = new Intent().setClass(this, MapAc.class);
        spec = tabHost.newTabSpec("Map").setIndicator("Map",
                          res.getDrawable(R.drawable.map))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        
        intent = new Intent().setClass(this, ConfigActivity.class);
        spec = tabHost.newTabSpec("Config").setIndicator("Config",
                          res.getDrawable(R.drawable.config))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);

    }
    public void switchTab(int index){
    	
    }
}