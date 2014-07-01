package com.example.something_tdo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class EventHttpClient {

		private static final String TAG = "EventHttpClient";
		private static String API_URL = "http://api.eventful.com/json/events/get?";
		private static String KEY_URL = "&app_key=ZG2zx9XgLFMGcGM9";
		private static String SIZE_URL = "&page_size=1";
		private static String PAGE_URL = "&page_number=";
		private static String BASE_URL = API_URL + KEY_URL + SIZE_URL + PAGE_URL;
		

		/**
		 * Fetches and returns the string representing event information for a
		 * given location.
		 * 
		 * @param location
		 *            Location received from the user
		 * @return String representing the event information.
		 * @throws IOException
		 */
		public String getWeatherData(String location) throws IOException {
			URL url = new URL(BASE_URL + location); // can throw exception
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
