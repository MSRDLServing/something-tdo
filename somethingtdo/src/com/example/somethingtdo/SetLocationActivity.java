package com.example.somethingtdo;

import java.util.List;

import com.example.somethingtdo.R;

import android.app.Activity;
//import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
//import android.util.Log;
//import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//import com.google.android.maps.*;
import com.google.android.gms.maps.model.LatLng;

public class SetLocationActivity extends Activity implements OnClickListener {
	
	SharedPreferences slocation = null;
	
	private Button mSetLocation;
	private EditText mCityName;
	private LatLng latlng;
	private String mLoc;
	double latitude;
	double longitude;
	private DatabaseHelper dh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);
		
		mCityName = (EditText)findViewById(R.id.edit);
		
		GPSTracker gpsTracker = new GPSTracker(this);
		
		if(gpsTracker.canGetLocation()){
			
			latitude = gpsTracker.latitude;
			longitude = gpsTracker.longitude;
			mCityName.setText(gpsTracker.getLocality(this));
			savingPreference(latitude, longitude);
			latlng = new LatLng(latitude,longitude);
			Toast.makeText(getApplicationContext(),"The current loaction set is "+ mCityName.getText()+ latlng.toString(),Toast.LENGTH_LONG).show();
			
		}
		else{
			gpsTracker.showSettingsAlert();
		}
		
		mSetLocation = (Button)findViewById(R.id.button1);
		
		mSetLocation.setOnClickListener(this);
	}
	
	public void savingPreference(double lat, double lng){
		slocation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor slocation_editor = slocation.edit();
		slocation_editor.putLong("lat", (long) lat);
		slocation_editor.putLong("long", (long) lng);
		slocation_editor.commit();
	}
	
	public LatLng retrievePreference(){
		LatLng slatlong= null;
		latitude = (slocation.getLong("lat",0));
		longitude = (slocation.getLong("lat",0));
		 slatlong= new LatLng(latitude,longitude);	 
		 return slatlong;
}

	@Override
	public void onClick(View v){
		
		int id = v.getId();
		if (id == R.id.button1) {
			try{
			String locationName = mCityName.getText().toString();
			Geocoder gc= new Geocoder(this);
			List<Address> addresses = gc.getFromLocationName(locationName, 1);
			if(addresses.isEmpty()){
				Toast.makeText(getApplicationContext(),"No location found matching this address",Toast.LENGTH_LONG).show();
			}else{
				
			latlng =  new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
				latitude = addresses.get(0).getLatitude();
				longitude= addresses.get(0).getLongitude();
				savingPreference(latitude, longitude);
				
				//Update Database
				String user = LoginActivity.retrieveUsername();
				this.dh = new DatabaseHelper(this);
				this.dh.updateLocation(user,locationName);
				
	        	List <String> profile = this.dh.searchAndGet(user);
	    		mLoc = profile.get(3);
	    		
	    		Log.d("SetLocation", "mLoc " + mLoc + "for user " + user);
				
				Toast.makeText(getApplicationContext(),"The loaction set is "+ mCityName.getText()+ latlng.toString(),Toast.LENGTH_LONG).show();
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
