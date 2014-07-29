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

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class EventIntentSerializer {
	
	private static final String TAG = "EVENTSERIALIZER";
	
	private Context mContext;
    private String mFilename;

    public EventIntentSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveEvents(ArrayList<Event> events)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Event c : events)
            array.put(c.toJSON());

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
    
//    public ArrayList<Event> loadEvents() throws IOException, JSONException {
    public byte[] loadEvents() throws IOException {
        ArrayList<Event> Events = new ArrayList<Event>();
        BufferedReader reader = null;
        StringBuilder out = new StringBuilder();
        byte[] xmlString = null;
        try {
            // Open and read the file into a StringBuilder
        	AssetManager am = mContext.getAssets();
            InputStream in = am.open(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                out.append(line);
            }
            xmlString = out.toString().getBytes();
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        	Log.e(TAG, "File not found Exception"+e);
        } finally {
            if (reader != null)
                reader.close();
        }
        return xmlString;
    }

}
