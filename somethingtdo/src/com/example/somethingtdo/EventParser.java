package com.example.somethingtdo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class EventParser {
	Events mEvents;
	JSONObject mData;
	
	public EventParser(String data) throws JSONException {
		this.mData = new JSONObject(data);
		parseData();
	}
	
	private void parseData() throws JSONException {
		int resultCount = mData.getInt("total_items");
		System.out.println("Top Parse Data" + resultCount);
		if (resultCount <= 0) {
			// error
			// abort
			return;
		}
		this.mEvents = new Events (resultCount);
		int pageSize = mData.getInt("page_size");
		
		JSONObject jObjEvents = mData.getJSONObject("events");

		if (pageSize <= 1) {
			JSONObject jObj = jObjEvents.getJSONObject("event");
			this.mEvents.insertEvent(buildEvent(jObj));
			}
		else {		
			JSONArray jArr = jObjEvents.getJSONArray("event"); 
			
			for (int i =0; i < pageSize; i++) {
				JSONObject jObj = jArr.getJSONObject(i);
				this.mEvents.insertEvent(buildEvent(jObj));
			}
		}
	}
	private Event buildEvent (JSONObject jObj) throws JSONException {
		Event mEvent = new Event();
		mEvent.setTitle(jObj.getString("title"));
		mEvent.setVenue(jObj.getString("venue_name"));
		mEvent.setStreetAddress(jObj.getString("venue_address"));
		mEvent.setVenueUrl(jObj.getString("venue_url"));
		mEvent.setEventUrl(jObj.getString("url"));
		mEvent.setCity(jObj.getString("city_name"));
		mEvent.setState(jObj.getString("region_name"));
		mEvent.setZipCode(jObj.getString("postal_code"));
		mEvent.setLatitude((float) jObj.getDouble("latitude"));
		mEvent.setLongitude((float) jObj.getDouble("longitude"));
		mEvent.setDate(jObj.getString("start_time"));
		mEvent.setStartTime(jObj.getString("start_time"));
		mEvent.setDescription(jObj.getString("description"));
		Log.d("EventParser", mEvent.toString());
		return mEvent;
	}

	public Events getEvents() {
		return mEvents;
	}

}