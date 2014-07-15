package com.example.somethingtdo;

import android.os.AsyncTask;
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

/*
public class EventActivity {
	
	class JSONEventTask extends AsyncTask<String, Integer, Events> {

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

}
*/
