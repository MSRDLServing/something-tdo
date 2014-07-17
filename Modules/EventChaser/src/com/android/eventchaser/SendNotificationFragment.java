package com.android.eventchaser;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SendNotificationFragment extends Fragment {

    private static final int REQUEST_CONTACT = 2;
	
	private EditText mEvent;
	
	private Button mChooseContact;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_send_notification, container, false);
		
		mEvent = (EditText)v.findViewById(R.id.event_title);
		
		Button sendNotificationButton = (Button)v.findViewById(R.id.send_notify_Button);
		sendNotificationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT, mEvent.getText().toString());
				i.putExtra(Intent.EXTRA_SUBJECT, "New Events!!");
				startActivity(i);
			}
		});
		
		mChooseContact = (Button) v.findViewById(R.id.choose_contact_tButton);
		mChooseContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, REQUEST_CONTACT);
			}
		});
		
		return v;
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		
		if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();
			
			String[] queryFields = new String[] {
		            ContactsContract.Contacts.DISPLAY_NAME
		        };
			
			Cursor c = getActivity().getContentResolver()
		            .query(contactUri, queryFields, null, null, null);
			
			if (c.getCount() == 0) {
	            c.close();
	            return;
	        }
			
			c.moveToFirst();
	        String contact = c.getString(0);
	        mChooseContact.setText(contact);
	        c.close();
		}
	}
}
