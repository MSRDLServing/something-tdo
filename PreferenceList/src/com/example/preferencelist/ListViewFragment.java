package com.example.preferencelist;

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

	private ArrayList<Event> mEvents;
	EventLab mEventLab;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActivity().setTitle(R.string.app_name);
        
        mEventLab = EventLab.get(getActivity());

        new FetchItemsTask().execute();
    
//        mEvents = EventLab.get(getActivity()).loadEvents();
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Event c = ((EventAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getId() + " was clicked" + " isChecked?" + c.toString());
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
//		            i.putExtra(PreferenceFragment.EXTRA_CRIME_ID, crime.getId());
//		            startActivityForResult(i, 0);
		            startActivity(i);
				}
			});
            
            return convertView;
        }
    }
	
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(Void... params) {
//        	new EventFetcher(mEventLab).fetchItems();
        	String filter = PreferenceManager.getDefaultSharedPreferences(getActivity())
        					.getString(PreferenceListFragment.SELECTED_INTEREST, null);
        	ArrayList<Event> mEvents = new EventFetcher(mEventLab).getEventsData(1, "Columbus", "Today", "music");
//        	try {
//        		String data = (new EventHttpClient()).getTestData();
//        		Log.d(TAG, "retrieved data:" + data);
//        	} catch (IOException e) {
//        		Log.e(TAG, " IO exception!");
//        	}
        	
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
