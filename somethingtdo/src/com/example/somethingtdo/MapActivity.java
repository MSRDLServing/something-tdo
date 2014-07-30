package com.example.somethingtdo;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.example.somethingtdo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

public class MapActivity extends Activity {

	private String mLoc;
	private DatabaseHelper dh;
	
	class CustomInfoWindowAdapter implements InfoWindowAdapter {
		
		 
		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			View v = getLayoutInflater().inflate(R.layout.marker, null);
			
            SpannableString titleText = new SpannableString(marker.getTitle());
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
            
            SpannableString snippetText = new SpannableString(marker.getSnippet());
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
			
			TextView title = (TextView) v.findViewById(R.id.title);
            TextView info= (TextView) v.findViewById(R.id.info);

            title.setText(titleText);
            info.setText(snippetText);

            return v;
		}

		@Override
		public View getInfoWindow(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}

	private GoogleMap googleMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }

            googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());       
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            
            
	    	GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
	    	
			String user = LoginActivity.retrieveUsername();
			this.dh = new DatabaseHelper(this);
			
			String cityName;
			String time;
			String prefs;
			String[] date;
			
    		cityName = this.dh.searchAndGet(user).get(2);
    		time = this.dh.searchAndGet(user).get(1);
    		prefs = this.dh.searchAndGet(user).get(3);
    		
    		date = time.split("/");
    		
//    		time = date[2] + "0" + date[1] + date[0] + "00";
    		
//    		time = time + "-" + time;
 
    		
    		Log.d("Map", cityName);
			Log.d("Map", time);
	    	
	    	gpsTracker.getLocation();
	    	LatLng latlng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
	    	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));
            
    		new JSONEvent().execute(cityName,time,prefs);

        }
	}
	
	private void createMarkers(double lat, double lon, String title, String snippet, float hue) {
	
		String snip;
	
		MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon))
    			.title(title)
    			.snippet("\n" + snippet)
    			.icon(BitmapDescriptorFactory.defaultMarker(hue));
		
		googleMap.addMarker(marker);	
		
	}
	
	class JSONEvent extends AsyncTask<String, Integer, Events>  {
		
		private EditText mCityName;


		private static final String TAG = "JSONEventsTask";

		@Override
		protected Events doInBackground(String... params) {
//			String pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("selectedInterests", null);			System.out.println(pref);
	    	
	    	String cityname = params[0];
	    	String time = params[1];
	    	String prefs = params[2];
	    	
	    	System.out.println(cityname);
			
	    	
			String data = null;
			try {
				data = ((new EventHttpClient()).getEventsData(1, cityname, time, prefs));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Events events = null;
			try {
				events = (new EventParser(data)).getEvents();
			} catch (JSONException e) {
				e.printStackTrace();
			}
						
			return events;
		}
		
	    protected void onPostExecute(Events mEvents) {
	    	
	    	if (mEvents.getSearchCount() > 1) {
	    		for (int i = 0; i < 20; i++){ 
	    			Event ev = mEvents.getEvent(i);
	    			createMarkers(ev.getLatitude(), 
	    					ev.getLongitude(), 
	    					ev.getTitle(), 
	    					ev.getVenue() + "\n" +  ev.getStreetAddress() + "\n" + ev.getStartTime(),
	    					BitmapDescriptorFactory.HUE_RED);
	    		}
	    	}
	    }

	}
	
}