package com.example.somethingtdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class PreferenceLab {
	
	private static final String TAG = "PreferenceLab";
    private static final String FILENAME = "preferences.json";

	
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
        
        mPreferences.addAll(Arrays.asList(tempPref));
        
        //Consult the loaded value to set isChecked flag.
        ArrayList<Preference> loadedList = loadPreferences();
    	Preference s;
    	int len = loadedList.size();
    	for (int i = 0; i < len; i++) {
    		s = loadedList.get(i);
    		if (s.isChecked()) {
    			Preference updatedPreference = new Preference(s.getName(), s.getId(), true);
    			mPreferences.set(i, updatedPreference);
    		}
    	}

    	Log.d(TAG, " after consulting the loaded json file:" + mPreferences.toString());
    }

    public static PreferenceLab get(Context c) {
    	
        if (sPreferenceLab == null) {
            sPreferenceLab = new PreferenceLab(c.getApplicationContext());
        }
        return sPreferenceLab;
    }
    
    public ArrayList<Preference> getPreferences() {
        return mPreferences;
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
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }
    
    public ArrayList<Preference> loadPreferences() {
        try {
        	ArrayList<Preference> preferences = mSerializer.loadPreferences();
            Log.d(TAG, "preferences loaded from file" + preferences);
            return preferences;
        } catch (Exception e) {
            Log.e(TAG, "Error loading crimes: ", e);
            return null;
        }
    }
}
