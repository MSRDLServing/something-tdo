package com.example.something_tdo;

import java.io.IOException;
import java.text.DecimalFormat;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EventActivity {
	

		private EditText mInputCity;
		private Button mSearch;
		private Button mClear;
		private TextView mLocation;
		private TextView mCondition;
		private TextView mTemperature;
		private ProgressBar mProgressBar;
		private LinearLayout mResultLayout;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_events);

			mInputCity = (EditText) findViewById(R.id.input_city);
			mSearch = (Button) findViewById(R.id.button_search);
			mSearch.setOnClickListener(this);
			mClear = (Button) findViewById(R.id.button_clear);
			mClear.setOnClickListener(this);

			mResultLayout = (LinearLayout) findViewById(R.id.result_layout);
			mLocation = (TextView) findViewById(R.id.location);
			mCondition = (TextView) findViewById(R.id.condition);
			mTemperature = (TextView) findViewById(R.id.temperature);

			mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.weather, menu);
			return true;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_search:
				mResultLayout.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.VISIBLE);
				String city = mInputCity.getText().toString();

				if (city != null) {
					new JSONWeatherTask().execute(city);
				}
				break;
			case R.id.button_clear:
				// clear the EditText field
				mInputCity.setText("");
				break;
			}
		}

		/**
		 * An asynchronous task to fetch weather data from the server.
		 * 
		 * @author swaroop
		 * 
		 */
		class JSONWeatherTask extends AsyncTask<String, Integer, Weather> {

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
			@Override
			//use in Something t'do
			protected void onPostExecute(Events events) {
				mProgressBar.setVisibility(View.GONE);

				// weather is obtained from the task, now display it on the screen
				if (events == null) {
					// show the error dialog
					new AlertDialog.Builder(EventActivity.this)
							.setTitle("City not found")
							.setMessage("Please enter a valid city name")
							.setPositiveButton("OK",
									// set listener
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											mInputCity.setText("");
											mResultLayout.setVisibility(View.GONE);
											mProgressBar.setVisibility(View.GONE);
										}
									}).show();
					return;
				}
				Log.d(TAG, events.toString());
				mResultLayout.setVisibility(View.VISIBLE);
				mLocation.setText(getResources().getString(R.string.location)
						+ weather.getCity());
				mCondition.setText(getResources().getString(R.string.condition)
						+ " " + weather.getCondition());
				mTemperature
						.setText(getResources().getString(R.string.temparature)
								+ " "
								+ new DecimalFormat("#.##").format(weather
										.getTemperature()) + " Celsius");
			}

		}
		/**
		 * Stores the value of {@code result} on the {@code Bundle}.
		 * 
		 * @param savedInstanceState
		 *            The {@code Bundle} on which the value should be stored.
		 */
		@Override
		protected void onSaveInstanceState(Bundle savedInstanceState) {
//			String result = mResult.getText().toString();
//			savedInstanceState.putString(KEY_RESULT, result);
		}

}
