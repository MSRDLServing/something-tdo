package com.example.something_tdo;

import java.util.Date;

public class Event {
	String mTitle;
	String mVenue;
	String mStreetAddress;
	String mVenueUrl;
	String mEventUrl;
	String mCity;
	String mState;
	String mZipCode;
	Float mLongitude;
	Float mLatitude;
	Date mDate;
	Date mStartTime;
	String mDescription;
	
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
	
	public Float getLognitude() {
		return mLongitude;
	}
	
	public Float getLatitude() {
		return mLatitude;
	}
	
	public Date getDate() {
		return mDate;
	}
	
	public Date getStartTime() {
		return mStartTime;
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
	
	public void setLognitude(Float mLongitude) {
		this.mLongitude = mLongitude;
	}
	
	public void setLatitude(Float mLatitude) {
		this.mLatitude = mLatitude;
	}
	
	public void setDate(Date mDate) {
		this.mDate = mDate;
	}
	
	public void setStartTime(Date mStartTime) {
		this.mStartTime = mStartTime;
	}
	
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	@Override
	public String toString() {
		super.toString();
		String string = null;
		string = string + mTitle + " - " + mCity + ", " + mState;
		return string;
	}
}
