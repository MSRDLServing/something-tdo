package com.android.eventchaser;

public class Events {
	int mSearchCount;
	int mLoadCount;
	Event[] mEvents;

	public Events (int arraySize) {
		this.mSearchCount = arraySize;
		this.mLoadCount = 0;
		this.mEvents = new Event[arraySize];			
	}
	
	public void insertEvent (Event input) {
		this.mEvents[mLoadCount] = input;
		this.mLoadCount++;
	}
	public int getSearchCount() {
		return mSearchCount;
	}
	public int getLoadCount() {
		return mLoadCount;
	}
	public Event[] getEventArray() {
		return mEvents;
	}
	public Event getEvent(int index) {
		return mEvents[index];
	}
}
