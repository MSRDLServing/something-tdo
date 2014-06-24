package com.example.somethingtdomap;

import com.example.somethingtdomap.R;
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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {
	
	class CustomInfoWindowAdapter implements InfoWindowAdapter {

		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			View v = getLayoutInflater().inflate(R.layout.marker, null);

            TextView info= (TextView) v.findViewById(R.id.info);

            info.setText(marker.getTitle() + "\n" + marker.getSnippet());

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

            createMarkers();
            

        }
	}
	
	private void createMarkers() {
		double lat1 = 39.960103547869075;
		double lon1 = -82.99892485141754;
		
		double lat2 = 39.987312;
		double lon2 = -83.00459699999999;
		
		MarkerOptions marker1 = new MarkerOptions().position(new LatLng(lat1, lon1))
    			.title("Sample Symphony")
    			.snippet("Time: 7:30 PM 15 July 2014\n " +
    					"Location: The Ohio Theater\n" +
    					"Address: 39 East State Street, Columbus, OH, 43215");
		
		MarkerOptions marker2 = new MarkerOptions().position(new LatLng(lat2, lon2))
    			.title("Local Band Event")
    			.snippet("Time: 10:00 PM 12 July 2014\n " +
    					"Location: Brother's Drake Meadery and Bar\n" +
    					"Address: 226 E 5th Ave, Columbus, OH 43201")
    			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		
		googleMap.addMarker(marker1);
		googleMap.addMarker(marker2);
		
		
	}
	
}
