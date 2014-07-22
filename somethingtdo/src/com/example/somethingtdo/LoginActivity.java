package com.example.somethingtdo;

import com.example.somethingtdo.R;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	private DatabaseHelper dh;
	private EditText userNameEditableField;
	private EditText passwordEditableField;
	private final static String OPT_NAME = "name";
	public static String username;
	public static String password;
	private final static String USER_NAME = "name";
	private final static String PASS = "password";
	static SharedPreferences loginDet = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userNameEditableField = (EditText) findViewById(R.id.username_text);
		passwordEditableField = (EditText) findViewById(R.id.password_text);
		View btnLogin = (Button) findViewById(R.id.login_button);
		btnLogin.setOnClickListener(this);
		View btnCancel = (Button) findViewById(R.id.cancel_button);
		btnCancel.setOnClickListener(this);
		View btnNewUser = (Button) findViewById(R.id.new_user_button);
		btnNewUser.setOnClickListener(this);
		
	}
	
	public static String retrieveUsername(){
		
		username = loginDet.getString(USER_NAME, "n/a");
		
		return username;
	}
	
public static String retrievePassword(){
		
		password = loginDet.getString(PASS, "n/a");
		
		return password;
	}
	
	private void checkLogin() {
		String username = this.userNameEditableField.getText().toString();
		String password = this.passwordEditableField.getText().toString();
		this.dh = new DatabaseHelper(this);
		List<String> names = this.dh.selectAll(username, password);
		if (names.size() > 0) { // Login successful
			// Save username as the name of the player
			// Save username as the name of the player
			loginDet = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = loginDet.edit();
			editor.putString(USER_NAME, username);
			editor.putString(PASS, password);
			editor.commit();

			// Bring up the GameOptions screen
			startActivity(new Intent(this, MainActivity.class));
//			 startActivity(new Intent(this, DummyActivity.class));
			
//			  Intent myIntent = new Intent(this, MainActivity.class);
//			  startActivityForResult(myIntent, 0);
			
			//finish();
		} else {
			// Try again?
			new AlertDialog.Builder(this)
					.setTitle("Error")
					.setMessage("Login failed")
					.setNeutralButton("Try Again",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			checkLogin();
			break;
		case R.id.cancel_button:
			finish();
			break;
		case R.id.new_user_button:
			startActivity(new Intent(this, AccountActivity.class));
			break;
		}
	}
		
}
