package com.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnSeekBarChangeListener, OnKeyListener {
	private MediaPlayer media = null;
	ImageButton play;
	ImageButton prev;
	ImageButton next;
	ImageButton sync;
	ImageView volume;
	SeekBar volumebar;

	TextView commentview;
	TextView songname;
	EditText commentedit;

	ListView listview;
	private ArrayList<String> filename = new ArrayList<String>();
	private File[] musicfilelist;
	private boolean ismute = false;
	private int currentMusic = 0;

	// location
	final double DEG_RATE = 1E6;
	Location location = null;
	LocationManager locationManager = null;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.player);

		listview = (ListView) findViewById(R.id.listview);
		getMusicFile();

		// add listener to list
		listview.setAdapter((new ArrayAdapter<String>(this, R.layout.listmusic,
				filename)));
		listview.setOnItemClickListener(this);

		// add listener to button
		play = (ImageButton) findViewById(R.id.playbutton);
		prev = (ImageButton) findViewById(R.id.prevbutton);
		next = (ImageButton) findViewById(R.id.nextbutton);
		sync = (ImageButton) findViewById(R.id.sync);
		volume = (ImageView) findViewById(R.id.volume);

		play.setOnClickListener(this);
		prev.setOnClickListener(this);
		next.setOnClickListener(this);
		sync.setOnClickListener(this);
		volume.setOnClickListener(this);

		// seek bar and textview
		volumebar = (SeekBar) findViewById(R.id.volumebar);
		volumebar.setOnSeekBarChangeListener(this);
		commentview = (TextView) findViewById(R.id.commentview);
		songname = (TextView) findViewById(R.id.songname);

		commentedit = (EditText) findViewById(R.id.commendtext);
		commentedit.setOnKeyListener(this);

		// Activity a = this.getParent();
		// getLocation();

	}

	// ���y�t�@�C����ǂݏo��
	public void getMusicFile() {
		File root = Environment.getExternalStorageDirectory();
		File[] subroot = root.listFiles();
		for (File file : subroot) {
			if (file.getName().equals("music")) {
				musicfilelist = file.listFiles();
				for (File file2 : musicfilelist) {
					filename.add(file2.getName().toString());
				}
			}
		}
	}

	// �w�������t�@�C�����v���[����
	public void playSelectMusic(String path) {
		if (path.equals("") || path == null) {

		} else {
			if (media != null) {
				media.stop();
				media.release();
				media = null;
			}
			media = new MediaPlayer();
			try {
				media.setDataSource(path);
				media.prepare();
				media.start();

				// songname.setText(musicfilelist[currentMusic].getName());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// play next music in the list
	public void nextPlay() {
		if (currentMusic < musicfilelist.length - 1) {
			currentMusic++;
			this.playSelectMusic(musicfilelist[currentMusic].getAbsolutePath());
			// listview.setItemChecked(currentMusic, true);
		}
	}

	// play prev music in the list
	public void prevPlay() {
		if (currentMusic > 0) {
			currentMusic--;
			this.playSelectMusic(musicfilelist[currentMusic].getAbsolutePath());
		}
	}

	/**
	 * get listening music
	 */
	public String getMusic() {
		String sname = (String) musicfilelist[currentMusic].getName()
				.toString();
		String[] split = sname.split("\\.");
		// songname.setText(split[0]);
		return split[0];
	}

	/**
	 * prepare infor to send to server
	 * 
	 * @return infor
	 */
	public String prepareData() {
		String data = "";
		// user information
		UserInfo user = new UserInfo();
		data = user.toString();

		// listening music
		String sname = this.getMusic();
		data += "," + sname;

		// location
		this.getLocation();
		int longitude = 0, latitude = 0;
		if (location != null) {
			longitude = (int) (location.getLongitude() * DEG_RATE);
			latitude = (int) (location.getLatitude() * DEG_RATE);
		}
		data += "," + String.valueOf(longitude) + ","
				+ String.valueOf(latitude);

		// comment
		String comment = (String) commentview.getText();

		data += "," + comment;

		return data;
	}

	/**
	 * 
	 */
	public void getLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		// if (location != null) {
		// songname.setText(String.valueOf(location.getLongitude()));
		// } else
		// songname.setText("null");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == play) {
			if (media == null) {
				playSelectMusic(musicfilelist[currentMusic].getAbsolutePath());
			}
			if (!media.isPlaying()) {
				// media start
				media.start();
				play.setImageResource(R.drawable.pause);
				// isplay = true;
			} else {
				// isplay = false;
				play.setImageResource(R.drawable.play);
				media.pause();
			}

		}
		if (v == next) {
			nextPlay();
		}
		if (v == prev) {
			prevPlay();
		}
		if (v == sync) {
			String servername = "10.0.2.2";
			int port = 6543;
			Client connect = null;

			connect = new Client(port, servername, "post");

			connect.setData(this.prepareData());
			Thread thread = new Thread(connect);
			thread.start();
			while (thread.isAlive())
				;

		}
		if (v == volume) {
			if (!ismute) {
				volume.setImageResource(R.drawable.volumemute);
				ismute = true;
				// set volume in here
			} else {
				volume.setImageResource(R.drawable.volume);
				ismute = false;
				// set volume in here
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		playSelectMusic(musicfilelist[position].getAbsolutePath());
		currentMusic = position;
		play.setImageResource(R.drawable.pause);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (media != null) {
			if (media.isPlaying())
				media.stop();
			media.release();
			media = null;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (media != null) {
			// nextPlay();
		}
	}

	// seek bar on change listener
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& keyCode == KeyEvent.KEYCODE_ENTER) {
			commentview.setText(commentedit.getText().toString());
			commentedit.setText("");
			return true;
		}
		return false;
	}
}
