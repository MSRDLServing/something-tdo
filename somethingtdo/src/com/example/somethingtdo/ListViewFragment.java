package com.example.somethingtdo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewFragment extends ListFragment {

    private static final String TAG = "ListViewFragment";
    private DatabaseHelper dh;

	private ArrayList<Event> mEvents;
	EventLab mEventLab;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActivity().setTitle(R.string.app_name);
        
        mEventLab = EventLab.get(getActivity());
        
		String user = LoginActivity.retrieveUsername();
		this.dh = new DatabaseHelper(getActivity());
		
		String cityName;
		String time;
		String prefs;
		
		cityName = this.dh.searchAndGet(user).get(2);
		time = this.dh.searchAndGet(user).get(1);
		prefs = this.dh.searchAndGet(user).get(3);

      new FetchItemsTask().execute(cityName, time, prefs);
    
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Event c = ((EventAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getId() + " list item was clicked" + " isChecked?" + c.toString());
        
        Intent i = new Intent(getActivity(), EventDetailActivity.class);
        i.putExtra(EventDeatilFragment.EXTRA_EVENT_ID, c.getId());
        i.putExtra(EventDeatilFragment.EXTRA_EVENT_TITLE, c.getTitle());
        i.putExtra(EventDeatilFragment.EXTRA_EVENT_DATE, c.getDate());
        i.putExtra(EventDeatilFragment.EXTRA_EVENT_LOC, c.getStreetAddress());
        i.putExtra(EventDeatilFragment.EXTRA_EVENT_DETAIL, c.getDescription());
        i.putExtra(EventDeatilFragment.EXTRA_VENUE_URL, c.getVenueUrl());

        Log.d(TAG, c.getId() + " list item was clicked" + " isChecked?" + c.toString() + " Event url:" + c.getVenueUrl());
        
        startActivity(i);
    }
	
	private class EventAdapter extends ArrayAdapter<Event> {

        public EventAdapter(ArrayList<Event> Events) {
            super(getActivity(), 0, Events);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            TextView textView ; 
        	
        	// If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_event, null);
            }

            // Configure the view for this Crime
            Event c = getItem(position);

            textView = (TextView) convertView.findViewById( R.id.event_list_item_titleTextView);
            textView.setText(c.getTitle());
            
            textView = (TextView) convertView.findViewById( R.id.event_list_item_dateTextView);
            textView.setText(c.getStartTime().toString());
            
            textView = (TextView) convertView.findViewById( R.id.event_list_item_locationTextView);
            textView.setText(c.getStreetAddress());
            
            Button sharedButton = (Button) convertView.findViewById( R.id.event_list_item_sharedButton );
            
            sharedButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
		            Intent i = new Intent(getActivity(), SendNotificationActivity.class);
		            startActivity(i);
				}
			});
            
            return convertView;
        }
    }
	
    private class FetchItemsTask extends AsyncTask<String,Void,ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(String... params) {
        	String filter = PreferenceManager.getDefaultSharedPreferences(getActivity())
        					.getString(PreferenceListFragment.SELECTED_INTEREST, null);
        	
        	String cityname = params[0];
        	String time = params[1];
        	String prefs = params[2];
        	
        	
        	ArrayList<Event> mEvents = new EventFetcher(mEventLab).getEventsData(1, cityname, "This Week", prefs);
        	
            return mEvents;
        }
        
        @Override
        protected void onPostExecute(ArrayList<Event> result) {
        	//This method runs on the main thread.
        	EventAdapter adapter = new EventAdapter(result);
	        setListAdapter(adapter);
        }
    }
    
}
