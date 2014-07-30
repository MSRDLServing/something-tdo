package com.example.somethingtdo;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public class JSONEvent extends AsyncTask<String, Integer, Events>  {


		private static final String TAG = "JSONEventsTask";

		@Override
		protected Events doInBackground(String... params) {
			String data = null;
			try {
				data = ((new EventHttpClient()).getEventsData(Integer.parseInt(params[0]), params[1], params[2], params[3]));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Log.d(TAG, data);

			Events events = null;
			try {
				events = (new EventParser(data)).getEvents();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return events;
		}
			
}
