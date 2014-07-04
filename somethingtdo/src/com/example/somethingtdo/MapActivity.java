package com.example.somethingtdo;

import java.io.IOException;

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
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {

	
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
		
		
//		System.out.println(mEvents.getEvent(0).toString());
		
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
    		new JSONEvent().execute("1 Columbus today music,comedy");

            googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            
    		new JSONEvent().execute("1 Columbus today music,comedy");

/*
    		double lat = 39.960103547869075;
    		double lon = -82.99892485141754;
    		String title = "Sample Symphony";
    		String snippet = "\nTime: 7:30 PM 15 July 2014\n " +
					"Location: The Ohio Theater\n" +
					"Address: 39 East State Street, Columbus, OH, 43215\n" +
					"Category: Live Music\n\n" +
					"A very nice Sample Sympony";
            
            createMarkers(lat, lon, title, snippet, BitmapDescriptorFactory.HUE_RED);
            
    		double lat2 = 39.987312;
    		double lon2 = -83.00459699999999;
    		String title2 = "Local Band Event";
    		String snippet2 = "\nTime: 10:00 PM 12 July 2014\n " +
					"Location: Brothers Drake Meadery and Bar\n" +
					"Address: 226 E 5th Ave, Columbus, OH 43201\n" +
					"Category: Live Music\n\n" +
					"A Local Band";            
            
            createMarkers(lat2, lon2, title2, snippet2, BitmapDescriptorFactory.HUE_GREEN);
*/

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


		private static final String TAG = "JSONEventsTask";

		@Override
		protected Events doInBackground(String... params) {
			String data = null;
			try {
				data = ((new EventHttpClient()).getEventsData(1, "Columbus", "today", "music,comedy"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.d("SUCCESS", data);
			

//			Log.d("Before Event Parser",null);
			System.out.println("Before EventParser");
			Events events = null;
			try {
				events = (new EventParser(data)).getEvents();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			System.out.println("After EventParser");
			
//			System.out.println(events.getEvent(0).toString());
						
			
			return events;
		}
		
		
/*		
		protected void onProgressUpdate(Integer ...progress) {
			setProgressPercent(progress[0]);
	    }
*/
	    protected void onPostExecute(Events mEvents) {
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    	
	    	//System.out.println("MapsActivity " + mEvents.getEvent(0).toString());
	    	
	    	for (int i = 0; i < 10; i++){ 
	    		Event ev = mEvents.getEvent(i);
	    		createMarkers(ev.getLatitude(), ev.getLongitude(), ev.getTitle(), ev.getVenue(), BitmapDescriptorFactory.HUE_RED);
	    	}
	    }

	}
	
}