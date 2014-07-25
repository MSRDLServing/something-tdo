package com.example.somethingtdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class PreferenceLab {
	
	private static final String TAG = "PreferenceLab";
    private static final String FILENAME = "preferences.json";
    private DatabaseHelper dh;
    private static String mInterests;

	
	private ArrayList<Preference> mPreferences;
    private PreferenceIntentJSONSerializer mSerializer;

	
	private static PreferenceLab sPreferenceLab;
    private Context mAppContext;

    private PreferenceLab(Context appContext) {
        mAppContext = appContext;
        
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

    public static PreferenceLab get(Context c) {
    	
        //if (sPreferenceLab == null) {
        	sPreferenceLab = new PreferenceLab(c.getApplicationContext());
        //}
        Log.d(TAG, "Preference Lab created");
        return sPreferenceLab;
    }
    
    public ArrayList<Preference> getPreferences() {
        return mPreferences;
    }
    
    // Add function to convert list to string with comma delimiters
    
    public String getInterestsString () {
    	
		Preference s;
    	String msg = "";
    	Iterator<Preference> e = mPreferences.iterator();
    	while (e.hasNext())
    	{
    	    s = (Preference)e.next();
    	    if (s.isChecked()) {
    	    	msg += (s.getId() + ",");
    	    	
    	    }
    	    
    	   
    	}
    	 return msg;
    }

    public Preference getPreference(UUID id) {
        for (Preference c : mPreferences) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public boolean savePreferences() {
        try {
            mSerializer.savePreferences(mPreferences);
//            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
//            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }
    
    public ArrayList<Preference> loadPreferences() {
        try {
        	ArrayList<Preference> preferences = mSerializer.loadPreferences();
 //           Log.d(TAG, "preferences loaded from file" + preferences);
            return preferences;
        } catch (Exception e) {
//            Log.e(TAG, "Error loading crimes: ", e);
            return null;
        }
    }
}
