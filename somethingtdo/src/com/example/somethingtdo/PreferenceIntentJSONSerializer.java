package com.example.somethingtdo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class PreferenceIntentJSONSerializer {
	
	private static final String TAG = "PreferenceSERIALIZER";
	
	private Context mContext;
    private String mFilename;

    public PreferenceIntentJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void savePreferences(ArrayList<Preference> Preferences)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Preference c : Preferences)
            array.put(c.toJSON());

        Log.d(TAG, " save JSON file:" + Preferences.toString());
        
        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext
                .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
    
//    public ArrayList<Preference> loadPreferences() throws IOException, JSONException {
    public ArrayList<Preference> loadPreferences() throws IOException, JSONException {
        ArrayList<Preference> preferences = new ArrayList<Preference>();
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // Build the array of crimes from JSONObjects
            for (int i = 0; i < array.length(); i++) {
            	preferences.add(new Preference(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return preferences;
//        return xmlString;
    }

}
