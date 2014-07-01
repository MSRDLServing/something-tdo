package com.example.somethingtdo;

import com.example.somethingtdo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

        }
	}
	
	private void createMarkers(double lat, double lon, String title, String snippet, float hue) {
	
		MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon))
    			.title(title)
    			.snippet(snippet)
    			.icon(BitmapDescriptorFactory.defaultMarker(hue));
		
		googleMap.addMarker(marker);	
		
	}
	
}