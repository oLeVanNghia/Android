package com.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class ConfigActivity extends Activity implements OnClickListener {
	// Spinner property;
	EditText name;
	RadioButton male;
	RadioButton female;
	EditText age;
	EditText address;
	EditText email;
	EditText twitter;
	EditText facebook;

	ImageButton savep;
	ImageButton newp;
	ImageButton delete;
	ImageButton edit;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.property);

		name = (EditText) findViewById(R.id.name);
		male = (RadioButton) findViewById(R.id.check_male);
		male.setChecked(true);
		female = (RadioButton) findViewById(R.id.check_female);
		age = (EditText) findViewById(R.id.age);
		address = (EditText) findViewById(R.id.address);
		twitter = (EditText) findViewById(R.id.twitter);

		savep = (ImageButton) findViewById(R.id.save);
		edit = (ImageButton) findViewById(R.id.edit);

		// add listener
		savep.setOnClickListener(this);
		edit.setOnClickListener(this);

		male.setOnClickListener(this);
		female.setOnClickListener(this);

		loadProperty();
		disEditable();
	}

	public void enEditable() {
		name.setFocusableInTouchMode(true);
		male.setClickable(true);
		female.setClickable(true);
		age.setFocusableInTouchMode(true);
		address.setFocusableInTouchMode(true);
		twitter.setFocusableInTouchMode(true);
	}

	public void disEditable() {
		name.setFocusable(false);
		male.setClickable(false);
		female.setClickable(false);
		age.setFocusable(false);
		address.setFocusable(false);
		twitter.setFocusable(false);
	}

	public String getGender() {
		if (male.isChecked())
			return "male";
		return "female";
	}

	public void loadProperty() {
		UserInfo user = new UserInfo();
		name.setText(user.getName());
		age.setText(user.getAge());
		if (user.getGender().equals("male")) {
			male.setChecked(true);
		} else
			female.setChecked(true);
		address.setText(user.getAddress());
		twitter.setText(user.getTwitter());
	}
	public void createDialog(Context context,String title, String message){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == savep) {
			String inname = name.getText().toString();
			if (inname.equals("") || inname == null) {
				Toast.makeText(getApplicationContext(), "input name, please", Toast.LENGTH_SHORT).show();
				//createDialog(getApplicationContext(), "Warning", "input name, please");
				//age.setText("error");
			} else {
				String ingender = this.getGender();
				String inage = age.getText().toString();
				String inandress = address.getText().toString();
				String intwitter = twitter.getText().toString();

				disEditable();

				UserInfo user = new UserInfo(inname, inage, ingender,
						inandress, intwitter);
				user.save();
			}

		}
		if (v == edit) {
			enEditable();
		}

	}

}
