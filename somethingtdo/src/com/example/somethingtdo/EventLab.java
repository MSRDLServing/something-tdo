package com.example.somethingtdo;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class EventLab {
    private static final String FILENAME = "event_columbus.xml";
    
    private ArrayList<Event> mEvents;
    private EventIntentSerializer mSerializer;
    
	private static EventLab sEventLab;
    private Context mAppContext;

    
    private EventLab(Context appContext) {
        mAppContext = appContext;
        mEvents = new ArrayList<Event>();
        mSerializer = new EventIntentSerializer(mAppContext, FILENAME);
    }
    
    public static EventLab get(Context c) {
        if (sEventLab == null) {
            sEventLab = new EventLab(c.getApplicationContext());
        }
        return sEventLab;
    }
    
    public EventIntentSerializer getSerializer() {
		return mSerializer;
	}
}
