package com.example.preferencelist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class EventFragment extends Fragment {
	
    private static final String TAG = "EventFragment";
	
	GridView mGridView;
	
	EventLab mEventLab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        
        mEventLab = EventLab.get(getActivity());
        
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mGridView = (GridView)v.findViewById(R.id.gridView);

        return v;
    }
    
    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
//        	new EventFetcher(mEventLab).fetchItems();
        	new EventFetcher(mEventLab).getEventsData(1, "Columbus", "Today", "music");
//        	try {
//        		String data = (new EventHttpClient()).getTestData();
//        		Log.d(TAG, "retrieved data:" + data);
//        	} catch (IOException e) {
//        		Log.e(TAG, " IO exception!");
//        	}
        	
            return null;
        }
    }
}
