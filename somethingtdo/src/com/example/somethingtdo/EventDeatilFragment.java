package com.example.somethingtdo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EventDeatilFragment extends Fragment {

    public static final String EXTRA_EVENT_ID = "com.android.eventchaser.eventId";
    public static final String EXTRA_EVENT_TITLE = "com.android.eventchaser.event_title";
    public static final String EXTRA_EVENT_DATE = "com.android.eventchaser.event_date";
    public static final String EXTRA_EVENT_LOC = "com.android.eventchaser.event_location";
    public static final String EXTRA_EVENT_DETAIL = "com.android.eventchaser.event_details";
    public static final String EXTRA_VENUE_URL = "com.android.eventchaser.event_url";
    
	private static final String TAG = "EventDetailFragment";

	private Event mEvent;

	
	private TextView mTitleText;
	private TextView mDateText;
	private TextView mLocationText;
	private TextView mDetailText;
	private Button mLinkUrlButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String eventId = getActivity().getIntent().getSerializableExtra(EXTRA_EVENT_ID).toString();
		Log.d(TAG, " retrieved event id:" + eventId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_event_detail, container, false);
		
		String title = getActivity().getIntent().getSerializableExtra(EXTRA_EVENT_TITLE).toString();
		mTitleText = (TextView)v.findViewById(R.id.event_title);
		mTitleText.setText(title);
		
		String date = getActivity().getIntent().getSerializableExtra(EXTRA_EVENT_DATE).toString();
		mDateText = (TextView)v.findViewById(R.id.event_date);
		mDateText.setText(date);
		
		String loc = getActivity().getIntent().getSerializableExtra(EXTRA_EVENT_LOC).toString();
		mLocationText = (TextView)v.findViewById(R.id.event_location);
		mLocationText.setText(loc);
		
		String details = getActivity().getIntent().getSerializableExtra(EXTRA_EVENT_DETAIL).toString();
		mDetailText = (TextView)v.findViewById(R.id.event_detail);
		mDetailText.setText(details);
		
		mLinkUrlButton = (Button)v.findViewById(R.id.event_url);
		mLinkUrlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String event_url = getActivity().getIntent().getSerializableExtra(EXTRA_VENUE_URL).toString();
				Uri eventPageUri = Uri.parse(event_url);
	            Intent i = new Intent(Intent.ACTION_VIEW, eventPageUri);
	            Log.d(TAG, " event uri:" + event_url);
	            startActivity(i);				
			}
		});
		
		
		return v;
	}
}
