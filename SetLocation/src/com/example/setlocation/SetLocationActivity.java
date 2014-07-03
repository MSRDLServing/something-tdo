package com.example.setlocation;

import java.util.List;

import com.example.setlocation.R;
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
	
	//private final Context mContext;
	private Button mSetLocation;
	private EditText mCityName;
	private LatLng latlng;
	//boolean isGPSEnabled= false;
	//boolean isNetworkEnabled= false;
	//boolean canGetLocation= false;
	//Location location;
	double latitude;
	double longitude;
	//private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
	//private static final long MIN_TIME_BW_UPDATES = 1000 * 30 * 1;
	//protected LocationManager locationManager;
	//GPSTracker gpsTracker = new GPSTracker(this);
	
/*public SetLocationActivity(Context context) {
        this.mContext = context;
        //latlng = getLocation();
    }*/
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);
		
		GPSTracker gpsTracker = new GPSTracker(this);
		if(gpsTracker.canGetLocation()){
			
			latitude = gpsTracker.latitude;
			longitude = gpsTracker.longitude;
			savingPreference(latitude, longitude);
		}
		else{
			gpsTracker.showSettingsAlert();
		}
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		mSetLocation = (Button)findViewById(R.id.button1);
		mCityName = (EditText)findViewById(R.id.edit);
		mCityName.setText(gpsTracker.getLocality(this));
		//latlng = getLocation();
		
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
//		SharedPreferences date = getSharedPreferences("date",0);
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
				Toast.makeText(getApplicationContext(),"The loaction set is "+ mCityName.getText()+ latlng.toString(),Toast.LENGTH_LONG).show();
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_location, menu);
		return true;
	}*/

	
	

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_set_location,
					container, false);
			return rootView;
		}
	}*/

}
