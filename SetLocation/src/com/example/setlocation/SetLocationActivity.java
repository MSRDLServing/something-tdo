package com.example.setlocation;

import java.util.List;

import com.example.setlocation.R;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.*;
import com.google.android.gms.maps.model.LatLng;

public class SetLocationActivity extends Activity implements OnClickListener {
	
	private Button mSetLocation;
	private EditText mCityName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		mSetLocation = (Button)findViewById(R.id.button1);
		mCityName = (EditText)findViewById(R.id.edit);
		
		mSetLocation.setOnClickListener(this);
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
				
				LatLng latlng =  new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
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
