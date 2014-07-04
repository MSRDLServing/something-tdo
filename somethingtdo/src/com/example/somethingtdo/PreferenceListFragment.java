package com.example.somethingtdo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;

//import com.example.somethingtdo.JSONEvent;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenceListFragment extends ListFragment {

    private static final String TAG = "PreferenceListFragment";

	
	private ArrayList<Preference> mPreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true); //Notify the OS to create action bar.
        
        getActivity().setTitle(R.string.app_name);
        mPreferences = PreferenceLab.get(getActivity()).getPreferences();
    
        PreferenceAdapter adapter = new PreferenceAdapter(mPreferences);
        setListAdapter(adapter);
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Preference c = ((PreferenceAdapter)getListAdapter()).getItem(position);
		c.toggleChecked();
		CheckBox cb = (CheckBox) v.findViewById(R.id.CheckBox01);
		cb.setChecked(c.isChecked());
        Log.d(TAG, c.getId() + " was clicked" + " isChecked?" + c.isChecked());
    }
	
	private class PreferenceAdapter extends ArrayAdapter<Preference> {

        public PreferenceAdapter(ArrayList<Preference> preferences) {
            super(getActivity(), 0, preferences);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            CheckBox checkBox ; 
            TextView textView ; 
        	
        	// If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_preference, null);
            }

            // Configure the view for this Crime
            Preference c = getItem(position);

            textView = (TextView) convertView.findViewById( R.id.rowTextView );
            textView.setText(c.getName());
            
            checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );
            checkBox.setChecked(c.isChecked());
            
            checkBox.setTag(c);
            
            checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Preference c = (Preference) cb.getTag();
					c.setChecked(cb.isChecked());
					
				}
			});

            return convertView;
        }
    }
	
	//Action bar
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	    inflater.inflate(R.menu.preference_options_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Preference s;
    	String msg = "";
    	Iterator<Preference> e = mPreferences.iterator();
    	ListView lv = getListView();
    	int num = lv.getChildCount();
    	CheckBox checkBox;
	    switch (item.getItemId()) {
	        case R.id.menu_item_choose_interest:
	        	
	        	while (e.hasNext())
	        	{
	        	    s = (Preference)e.next();
	        	    if (s.isChecked()) {
	        	    	Log.d(TAG, s.getId());
	        	    	msg += (s.getId() + ",");
	        	    }
	        	}
	        	Log.d(TAG, "All selected:" + msg);
	        	Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

//	            Intent i = new Intent(getActivity(), MapActivity.class);
//	            i.putExtra(PreferenceFragment.EXTRA_CRIME_ID, .getId());
//	            startActivityForResult(i, 0)
	        	
//	        	JSONEvent je = new JSONEvent();
	        	
//        	new JSONEvent().execute("1 Columbus today music,comedy");

/*	        	
	        System.out.println("!!!Pre try");
			String data = null;
			try {
				data = ((new EventHttpClient()).getEventsData(1, "Columbus", "today", "music,comedy"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("!!!Http client");
				
				e1.printStackTrace();
			}
	        	
	        	Events events = null;
			try {
				events = (new EventParser(data)).getEvents();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				System.out.println("!!!Event Parser");
				e1.printStackTrace();
			}
*/
	        	
	        	startActivity(new Intent(getActivity(), MapActivity.class));
	            return true;
	        case R.id.menu_item_select_all:
	        	
	        	while (e.hasNext())
	        	{
	        	    s = (Preference)e.next();
	        	    s.setChecked(true);
	        	}
	        	
	        	for (int i = 0; i < num; i++) {
	        		View row = lv.getChildAt(i);
	        		checkBox = (CheckBox)row.findViewById( R.id.CheckBox01);
	        		checkBox.setChecked(true);
	        	}
	        	return true;
	        case R.id.menu_item_unselect_all:
	        	while (e.hasNext())
	        	{
	        	    s = (Preference)e.next();
	        	    s.setChecked(false);
	        	}
	        	
	        	for (int i = 0; i < num; i++) {
	        		View row = lv.getChildAt(i);
	        		checkBox = (CheckBox)row.findViewById( R.id.CheckBox01);
	        		checkBox.setChecked(false);
	        	}
	        	return true;
	        case R.id.setdate:
	        	startActivity(new Intent(getActivity(), SetDateActivity.class));
	        	return true;
	        case R.id.setloc:
	        	startActivity(new Intent(getActivity(), SetLocationActivity.class));
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
/*
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
		
		
	
		protected void onProgressUpdate(Integer ...progress) {
			setProgressPercent(progress[0]);
	    }

	    protected void onPostExecute(Event events) {
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }

	}
*/


}
