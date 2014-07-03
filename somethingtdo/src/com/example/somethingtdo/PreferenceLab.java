package com.example.somethingtdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import android.content.Context;

public class PreferenceLab {
	
	private ArrayList<Preference> mPreferences;
	
	private static PreferenceLab sPreferenceLab;
    private Context mAppContext;

    private PreferenceLab(Context appContext) {
        mAppContext = appContext;
        mPreferences = new ArrayList<Preference>();
        
        mPreferences = new ArrayList<Preference>();  
        
        Preference[] tempPref = 
        		new Preference[] {
    		  new Preference("Concert","music"), new Preference("Comedy","comedy"), new Preference("Performing Arts","performing_arts"), 
	          new Preference("Sports","sports"), new Preference("Film","movies-film"), new Preference("Galleries","art"), 
	          new Preference("Literary","books"), new Preference("Food","food"), new Preference("Festivals","festivals_parades")
        };
        
        mPreferences.addAll(Arrays.asList(tempPref));
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
}
