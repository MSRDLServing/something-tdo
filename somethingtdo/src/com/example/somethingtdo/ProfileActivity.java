package com.example.somethingtdo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity{

	private TextView mUsername, mDate,mLocation, mInterests;
	private DatabaseHelper dh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		mUsername = (TextView) findViewById(R.id.textView6);
		mDate = (TextView) findViewById(R.id.textView7);
		mLocation = (TextView) findViewById(R.id.textView8);
		mInterests = (TextView) findViewById(R.id.textView9);
		
		String user = LoginActivity.retrieveUsername();
		this.dh = new DatabaseHelper(this);
		List <String> profile = this.dh.searchAndGet(user);
		if (profile.size()>0){
			
			mUsername.setText(profile.get(0));
			mDate.setText(profile.get(1));
			mLocation.setText(profile.get(2));
			mInterests.setText(profile.get(3));
		}
		else{
			new AlertDialog.Builder(this)
		.setTitle("Error")
		.setMessage("User Not Found")
		.setNeutralButton("Try Again",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				}).show();
}
		
		
	}
}
