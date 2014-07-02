package com.example.preferencelist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class PreferenceListFragment extends ListFragment {

    private static final String TAG = "PreferenceListFragment";

	
	private ArrayList<Preference> mPreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        mPreferences = PreferenceLab.get(getActivity()).getPreferences();
    
        PreferenceAdapter adapter = new PreferenceAdapter(mPreferences);
        setListAdapter(adapter);
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		Preference c = ((PreferenceAdapter)getListAdapter()).getItem(position);
		c.toggleChecked();
		CheckBox cb = (CheckBox) v.findViewById(R.id.CheckBox01);
		cb.setChecked(c.isChecked());
        Log.d(TAG, c.getId() + " was clicked" + " isChecked?" + c.isChecked());
    }
	
	private class PreferenceAdapter extends ArrayAdapter<Preference> {

        public PreferenceAdapter(ArrayList<Preference> preferences) {
            super(getActivity(), 0, preferences);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            CheckBox checkBox ; 
            TextView textView ; 
        	
        	// If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_preference, null);
            }

            // Configure the view for this Crime
            Preference c = getItem(position);

            textView = (TextView) convertView.findViewById( R.id.rowTextView );
            textView.setText(c.getName());
            
            checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );
            checkBox.setChecked(c.isChecked());
            
            checkBox.setTag(c);
            
            checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Preference c = (Preference) cb.getTag();
					c.setChecked(cb.isChecked());
					
				}
			});

            return convertView;
        }
    }

}
