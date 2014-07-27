package com.example.somethingtdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private static String mInterests;

    public static final String SELECTED_INTEREST = "selectedInterests";
	
	private ArrayList<Preference> mPreferences;
	private DatabaseHelper dh;
	private Context mAppContext;
	
	private static final String FILENAME = "preferences.json";
	private PreferenceIntentJSONSerializer mSerializer;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "OnCreate PreferenceList");
        
        setHasOptionsMenu(true); //Notify the OS to create action bar.
        
        getActivity().setTitle(R.string.app_name);
        mPreferences = PreferenceLab.get(getActivity()).getPreferences();
        

		
		//mPreferences = PreferenceLab.get(getActivity()).loadPreferences();
        
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
	
	public String removeLast(String str) {

	  if (str.length() > 0 && str.charAt(str.length()-1)==',') {
	    str = str.substring(0, str.length()-1);
	  }
	  return str;
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
	        	msg = removeLast(msg);
	        	Log.d(TAG, "All selected:" + msg);
	        	Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

	        	PreferenceLab.get(getActivity()).savePreferences();
	        	
	        	PreferenceManager.getDefaultSharedPreferences(getActivity())
	            .edit()
	            .putString(SELECTED_INTEREST, msg)
	            .commit();
	        	
	        	//INSERT statement
	        	
	        	String user = LoginActivity.retrieveUsername();
	        	this.dh = new DatabaseHelper(getActivity());
	        	
	        	String interests = PreferenceLab.get(getActivity()).getInterestsString();
	        	
	        	Log.d(TAG, "tnterests string " + interests);
	        	
	        	this.dh.updateInterests(user, interests);
	        	
	        	List <String> profile = this.dh.searchAndGet(user);
	    		mInterests = profile.get(3);
	    		
	    		Log.d(TAG, "mInterests " + mInterests + "for user " + user);
	        	
//	            Intent i = new Intent(getActivity(), CrimePagerActivity.class);
//	            i.putExtra(PreferenceFragment.EXTRA_CRIME_ID, crime.getId());
//	            startActivityForResult(i, 0);
//	        	startActivity(new Intent(getActivity(), MapActivity.class));
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

/*
	@Override
	public void onResume()
	{
		  super.onResume();
//        mAppContext = getApplicationContext();
        
        mPreferences = new ArrayList<Preference>();  
        mSerializer = new PreferenceIntentJSONSerializer(mAppContext, FILENAME);

        Preference[] tempPref = 
        		new Preference[] {
    		  new Preference("Concert","music"), new Preference("Comedy","comedy"), new Preference("Performing Arts","performing_arts"), 
	          new Preference("Sports","sports"), new Preference("Film","movies-film"), new Preference("Galleries","art"), 
	          new Preference("Literary","books"), new Preference("Food","food"), new Preference("Festivals","festivals_parades")
        };
        
      
        
        //Consult the loaded value to set isChecked flag.
      //  ArrayList<Preference> loadedList = loadPreferences();
		String user = LoginActivity.retrieveUsername();
		this.dh = new DatabaseHelper(mAppContext);
		List <String> profile = this.dh.searchAndGet(user);
		
		mInterests = profile.get(3);
		
		Log.d(TAG, "mInterests " + mInterests + "for user " + user);
		
		String[] interestsArr = mInterests.split(",");
		
		Log.d(TAG, interestsArr[0]);
		
    	Preference s;
    	int len = interestsArr.length;
    	for (int i = 0; i < len; i++) {
    		
    		Log.d(TAG, interestsArr[i]);
    		for (int j = 0; j < tempPref.length; j++){
    			
    			if (interestsArr[i].equals(tempPref[j].getId())) {
    				
    				Log.d(TAG, "Match Found");
    				tempPref[j].setChecked(true);
    			}
    		}

    	}
    	
    	mPreferences.addAll(Arrays.asList(tempPref));

    	Log.d(TAG, " after consulting the loaded json file:" + mPreferences.toString());
		
	}
*/
}
