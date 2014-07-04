package com.example.somethingtdo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class EventHttpClient {

		public static final int PAGE_SIZE = 10;
		private static final String TAG = "EventHttpClient";
		private static String API_URL = "http://api.eventful.com/json/events/search?";
		private static String SORT_URL = "&sort_order=date";
		private static String KEY_URL = "&app_key=ZG2zx9XgLFMGcGM9";
		private static String SIZE_URL = "&page_size=" + PAGE_SIZE;
		private static String PAGE_URL = "&page_number=";
		private static String LOCATION_URL ="&location=";
		private static String DATE_URL ="&date=";
		private static String FILTER_URL = "&category=";
		private static String BASE_URL = API_URL + SORT_URL + KEY_URL + SIZE_URL + PAGE_URL;
		
		/**
		 * Fetches and returns the string representing event information for a
		 * given location.
		 * 
		 * @param location
		 *            Location received from the user
		 * @return String representing the event information.
		 * @throws IOException
		 */
		public String getEventsData(int pageNo, String location, String date, String filter) throws IOException {
			URL url = new URL(BASE_URL + pageNo + LOCATION_URL + location + DATE_URL + date + FILTER_URL + filter); // can throw exception
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			String out;
			try {
				InputStream in = connection.getInputStream();
				out = readStream(in);
				Log.d(TAG, out);
			} finally {
				connection.disconnect();
			}
			return out;
		}

		/**
		 * Reads the stream by breaking it into lines appended to each other.
		 * 
		 * @param in
		 *            Stream to read
		 * @return Equivalent string
		 * @throws IOException
		 */
		private String readStream(InputStream in) throws IOException {
			StringBuffer buffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\r\n");
			}
			in.close();
			return buffer.toString();
		}
}
