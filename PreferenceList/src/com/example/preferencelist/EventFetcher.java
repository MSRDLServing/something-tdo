package com.example.preferencelist;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.net.Uri;
import android.util.Log;

public class EventFetcher{
	
	public static final String TAG = "EventFetcher";
	
    private static final String ENDPOINT = "http://api.eventful.com/rest";
    private static final String API_KEY = "m6Wj5dR94NQ5jhs5";
    private static final String METHOD_SEARCH = "/events/search";
    private static final String PARAM_LOCATION = "location";

    private final static String LOCAL_DATA="event_columbus.xml";
    
    private static final String XML_EVENT_TAG = "event";
    private static final String TITLE_TAG = "title";
    private static final String START_DATE_TAG = "start_time";
    private static final String LOCATION_TAG = "venue_address";
    private static final String DESCRIPTION_TAG = "description";
    private static final String LONGITUDE_TAG = "longitude";
    private static final String LATITUDE_TAG = "latitude";

	public static final int PAGE_SIZE = 10;
	private static String API_URL = "http://api.eventful.com/events/search?";
	private static String SORT_URL = "&sort_order=date";
	private static String KEY_URL = "&app_key=m6Wj5dR94NQ5jhs5";
	private static String SIZE_URL = "&page_size=" + PAGE_SIZE;
	private static String PAGE_URL = "&page_number=";
	private static String LOCATION_URL ="&location=";
	private static String DATE_URL ="&date=";
	private static String FILTER_URL = "&category=";
	private static String BASE_URL = API_URL + SORT_URL + KEY_URL + SIZE_URL + PAGE_URL;
    
    EventLab mEventLab;
    
    public EventFetcher(EventLab eventLab) {
    	mEventLab = eventLab;
    }
    
	byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//        ((HttpURLConnection) connection).setInstanceFollowRedirects(false);
        
        Log.d(TAG, " url generated:" + urlSpec);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            Log.d(TAG, " response code:" + connection.getResponseCode());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            	return mEventLab.getSerializer().loadEvents();
//            	return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    
    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    
//    public ArrayList<Event> fetchItems() {
    public ArrayList<Event> getEventsData(int pageNo, String location, String date, String filter) {
    	
    	ArrayList<Event> items = new ArrayList<Event>();
    	
        try {
			String url = new URL(BASE_URL + pageNo + LOCATION_URL + location + DATE_URL + date + FILTER_URL + filter).toString(); // can throw exception
			
//            String url = Uri.parse(ENDPOINT + METHOD_SEARCH).buildUpon()
//                    .appendQueryParameter("app_key", API_KEY)
//                    .appendQueryParameter(PARAM_LOCATION, "Columbus")
//                    .build().toString();
//            String url = "http://api.eventful.com/rest/events/search?app_key=m6Wj5dR94NQ5jhs5&location=Columbus";
            String xmlString = getUrl(url);
            Log.i(TAG, "Received xml: " + xmlString);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));

            loadXML(items, parser);
            
            Log.d(TAG, " parsed data:" + items.toString());
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (XmlPullParserException xppe) {
            Log.e(TAG, "Failed to parse items", xppe);
        }
        return items;
    }
	
    void loadXML(ArrayList<Event> items, XmlPullParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.next();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && XML_EVENT_TAG.equals(xpp.getName())) {
                loadElement(items, xpp);
            }
            eventType = xpp.next();   
        }
    }

    private void loadElement(ArrayList<Event> items,XmlPullParser xpp) throws XmlPullParserException, IOException {

        int eventType = xpp.getEventType();
        if ( eventType == XmlPullParser.START_TAG && XML_EVENT_TAG.equals(xpp.getName()) ) {
        	Event item = new Event();
        	String id = xpp.getAttributeValue(null, "id");
        	item.setId(id);
        	Log.d(TAG, " added id:" + id);
        	eventType = xpp.next();
             
            while ( eventType != XmlPullParser.END_TAG || !XML_EVENT_TAG.equals(xpp.getName()) ) {
                if (eventType == XmlPullParser.START_TAG) {
                    loadItem(item, xpp);
                }

                eventType = xpp.next();   
            }
            items.add(item);
        } 
    }

    private void loadItem(Event item, XmlPullParser xpp) throws XmlPullParserException, IOException {

        int eventType = xpp.getEventType();
        
        if ( eventType == XmlPullParser.START_TAG) {
	        if (xpp.getName().equals(TITLE_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setTitle(xpp.getText());
	        	}
	            Log.d(TAG, " Added title:" + xpp.getText());
	        } else if (xpp.getName().equals(START_DATE_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setStartTime(xpp.getText());
	        	}
	            Log.d(TAG, " Added start time:" + xpp.getText());
	        } else if (xpp.getName().equals(LOCATION_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setVenue(xpp.getText());
	        	}
	            Log.d(TAG, " Added location:" + xpp.getText());
	        } else if (xpp.getName().equals(DESCRIPTION_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setDescription(xpp.getText());
	        	}
	            Log.d(TAG, " Added desc:" + xpp.getText());
	        } else if (xpp.getName().equals(LONGITUDE_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setLognitude(xpp.getText());
	        	}
	            Log.d(TAG, " Added desc:" + xpp.getText());
	        } else if (xpp.getName().equals(LATITUDE_TAG)) {
	        	eventType = xpp.next();
	        	if (eventType == XmlPullParser.TEXT) {
	        		item.setLatitude(xpp.getText());
	        	}
	            Log.d(TAG, " Added desc:" + xpp.getText());
	        }
        }
    }
    
    void parseItems(ArrayList<Event> items, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType = parser.next();
        
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
           switch (eventType)
           {
              case XmlPullParser.START_DOCUMENT:
                   System.out.println("Start document");
                   break;
   
              case XmlPullParser.START_TAG:
                   System.out.println("Start tag " + parser.getName());
                   break;
   
              case XmlPullParser.TEXT:
                   System.out.println("Text " + parser.getText());
                   break;
   
              case XmlPullParser.END_TAG:
                   System.out.println("End tag " + parser.getName());
           }
           eventType = parser.next();
        }
        
//        while (eventType != XmlPullParser.END_DOCUMENT) {
//            if (eventType == XmlPullParser.START_TAG &&
//                XML_EVENT.equals(parser.getName())) {
//                String id = parser.getAttributeValue(null, "id");
//                String caption = parser.getAttributeValue(null, "title");
//                String smallUrl = parser.getAttributeValue(null, EXTRA_SMALL_URL);
//
//                GalleryItem item = new GalleryItem();
//                item.setId(id);
//                item.setCaption(caption);
//                item.setUrl(smallUrl);
//                items.add(item);
//            }

            eventType = parser.next();
        }
}
