package com.android.eventchaser;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends FragmentActivity implements OnInfoWindowClickListener {
	
    private static final String TAG = "MapViewActivity";

	private GoogleMap mMap;
	
	EventLab mEventLab;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        
        mEventLab = EventLab.get(getApplicationContext());

        setUpMapIfNeeded();
        
        new FetchEventsTask().execute(); //Fetch the events and show them on the map as markers.
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	setUpMapIfNeeded();
    }
    
	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.setMyLocationEnabled(true);
//			if (mMap != null) {
//				setUpMap();
//			}
		}
	}
	
    public boolean bounceMarker(final Marker marker) {
        // This causes the marker to bounce into position when it is clicked.
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(1 - interpolator
                        .getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
	
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
        Log.d(TAG, " Click Info window");
    }
//	private void setUpMap() {
//		mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//	}
	
	private class FetchEventsTask extends AsyncTask<Void, Void, ArrayList<Event>> {
		@Override
		protected ArrayList<Event> doInBackground(Void... params) {
			String filter = PreferenceManager.getDefaultSharedPreferences(
					getApplicationContext()).getString(
					PreferenceListFragment.SELECTED_INTEREST, null);
			ArrayList<Event> mEvents = new EventFetcher(mEventLab).getEventsData(1, "Columbus", "This Week", filter);

			return mEvents;
		}

		@Override
		protected void onPostExecute(ArrayList<Event> result) {
			// In the main thread
			// Retrieved the events info
			// Draw markers on the map.
			Log.d(TAG, " retrieved events:" + result.toString());
			int len = result.size();
			Event e;
			for (int i = 0; i < len; i++) {
				e = result.get(i);
				double lat = Double.parseDouble(e.getLatitude());
				double lon = Double.parseDouble(e.getLongitude());
				String title = e.getTitle();
				String date = e.getDate().toString();
				String loc = e.getStreetAddress();
				mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(title).snippet("Time:" + date + " ^ " + " Loc:" + loc));
			}
			
	        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

	        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					boolean result = bounceMarker(marker);
					return result;
				}
			});
		}
	}
	
	class CustomInfoWindowAdapter implements InfoWindowAdapter {
		private final View mWindow;
		
		public CustomInfoWindowAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
		}
		
		@Override
		public View getInfoWindow(Marker marker) {
			render(marker, mWindow);
			return mWindow;
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			return null;
		}
		
        private void render(Marker marker, View view) {
        	String title = marker.getTitle();
        	TextView titleUi = (TextView)view.findViewById(R.id.event_info_window_titleTextView);
        	if (title != null){
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
        	} else {
        		titleUi.setText("");
        	}
        	
        	String snippet = marker.getSnippet();
        	String[] detail = snippet.split("^");
        	if (detail.length == 2) {
	        	String date = detail[0];
	        	String loc = detail[1];
	        	TextView dateUi = (TextView)view.findViewById(R.id.event_info_window_dateTextView);
	        	dateUi.setText(date);
	        	TextView locUi = (TextView)view.findViewById(R.id.event_info_window_locationTextView);
	        	locUi.setText(loc);
        	} else {
        		Log.d(TAG, " incorrect details:" + snippet);
        		Log.d(TAG, " incorrect snippets:" + detail.toString());

        	}
        }
	}
}
