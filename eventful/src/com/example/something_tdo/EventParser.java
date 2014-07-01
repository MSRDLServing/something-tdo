package com.example.something_tdo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.something_tdo;

public class EventParser {
	private static final int ERROR_CODE = 404;
	Event mEvent;
	JSONObject mData;
	
	public EventParser(String data) throws JSONException {
		mData = new JSONObject(data);
		parseData();
	}
	private void parseData() throws JSONException {
		int code = mData.getInt("cod");
		if (code == ERROR_CODE) {
			// error
			// abort
			return;
		}
		mEvent = new Event();
		mEvent.setCity(mData.getString("name"));
		JSONArray jArr = mData.getJSONArray("weather");
		JSONObject jObj = jArr.getJSONObject(0);
		mEvent.setCondition(jObj.getString("description"));
		// converting from Kelvin to Celsius
		mEvent.setTemperature(mData.getJSONObject("main").getDouble("temp") - 273.15);
	}

	public Event getEvent() {
		return mEvent;
	}

}
