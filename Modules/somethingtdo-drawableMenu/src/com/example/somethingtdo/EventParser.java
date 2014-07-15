package com.example.somethingtdo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

//import com.example.something_tdo;

public class EventParser {
	Events mEvents;
	JSONObject mData;
	
	public EventParser(String data) throws JSONException {
		this.mData = new JSONObject(data);
		parseData();
	}
	
// implement latter
//	public EventParser(String data, Events eventArray) throws JSONException {
//		mData = new JSONObject(data);
//		parseData();
//	}

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
//		JSONArray jArr = mData.getJSONArray("events");
		
		JSONObject jObjEvents = mData.getJSONObject("events");
//		System.out.println(jObjEvents.getString("event"));
//		System.out.println("Between");
		
		JSONArray jArr = jObjEvents.getJSONArray("event"); //Test for array length?
		System.out.println("After");
		
		System.out.println("Middle Parse Data");
		
		for (int i =0; i < pageSize; i++) {
//			System.out.println("For Parse Data"+i);
			JSONObject jObj = jArr.getJSONObject(i);
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
			mEvent.setLognitude((float) jObj.getDouble("longitude"));
			mEvent.setDate(jObj.getString("start_time"));
			mEvent.setStartTime(jObj.getString("start_time"));
			mEvent.setDescription(jObj.getString("description"));
			this.mEvents.insertEvent(mEvent);
			Log.d("EventParser", mEvent.toString());
		}
		
		

	}

	public Events getEvents() {
		return mEvents;
	}

}
