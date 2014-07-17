package com.android.eventchaser;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {
	String mId;
	String mTitle;
	String mVenue;
	String mStreetAddress;
	String mVenueUrl;
	String mEventUrl;
	String mCity;
	String mState;
	String mZipCode;
	String mLongitude;
	String mLatitude;
	String mDate; //in ISO 8601 format (e.g. "2005-03-01 19:00:00").
	String mStartTime; //in ISO 8601 format (e.g. "2005-03-01 19:00:00").
	String mDescription;
	
	private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_STARTTIME = "starttime";
    private static final String JSON_LOCATION = "location";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_LONGITUDE = "longitude";
    private static final String JSON_LATITUDE = "latitude";

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_STARTTIME, mStartTime);
        json.put(JSON_LOCATION, mStreetAddress);
        json.put(JSON_DESCRIPTION, mDescription);
        json.put(JSON_LONGITUDE, mLongitude);
        json.put(JSON_LATITUDE, mLatitude);

        return json;
    }
	
	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getVenue() {
		return mVenue;
	}
	
	public String getStreetAddress() {
		return mStreetAddress;
	}
	
	public String getVenueUrl() {
		return mVenueUrl;
	}
	
	public String getEventUrl() {
		return mEventUrl;
	}
	
	public String getCity() {
		return mCity;
	}
	
	public String getState() {
		return mState;
	}
	
	public String getZipCode() {
		return mZipCode;
	}
	
	public String getLongitude() {
		return mLongitude;
	}
	
	public String getLatitude() {
		return mLatitude;
	}
	
	public Date getDate() {
		return convertDate(mDate);
	}
	
	public Date getStartTime() {
		return convertDate (mStartTime);
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
	public void setVenue(String mVenue) {
		this.mVenue = mVenue;
	}
	
	public void setStreetAddress(String mStreetAddress) {
		this.mStreetAddress = mStreetAddress;
	}
	
	public void setVenueUrl(String mVenueUrl) {
		this.mVenueUrl = mVenueUrl;
	}
	
	public void setEventUrl(String mEventUrl) {
		this.mEventUrl = mEventUrl;
	}
	
	public void setCity(String mCity) {
		this.mCity = mCity;
	}
	
	public void setState(String mState) {
		this.mState = mState;
	}
	
	public void setZipCode(String mZipCode) {
		this.mZipCode = mZipCode;
	}
	
	public void setLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}
	
	public void setLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}
	
	public void setDate(String mDate) {
		this.mDate = mDate;
	}
	
	public void setStartTime(String mStartTime) {
		this.mStartTime = mStartTime;
	}
	
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	@Override
	public String toString() {
		super.toString();
		String string = null;
		string = string + mTitle + " - " + mCity + ", " + mState + " lon:" + mLongitude + " lat:" +mLatitude ;
		return string;
	}
	private Date convertDate(String input){
		Date result = new Date();
		
		return result;
	}
}
