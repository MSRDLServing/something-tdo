package com.example.somethingtdo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.somethingtdo.DatePickerDialogFragment;
import com.example.somethingtdo.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SetDateActivity extends FragmentActivity {
	
	SharedPreferences date = null;
	public String setDate;
	TextView savedDate;
	final Calendar c= Calendar.getInstance();
	int mYear = c.get(Calendar.YEAR);
	int mMonth = c.get(Calendar.MONTH);
	int mDay = c.get(Calendar.DAY_OF_MONTH);
	private DatabaseHelper dh;
	
	/** This handles the message send from DatePickerDialogFragment on setting date */
	Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){   
        	/** Creating a bundle object to pass currently set date to the fragment */
        	Bundle b = m.getData();
        	
        	/** Getting the day of month from bundle */
    		mDay = b.getInt("set_day");
    		
    		/** Getting the month of year from bundle */
    		mMonth = b.getInt("set_month");
    		
    		/** Getting the year from bundle */
    		mYear = b.getInt("set_year");

    		savingPreference(Integer.toString(mDay),Integer.toString(mMonth+1),Integer.toString(mYear));
    		savedDate.setText(retrievePreference());
    		/** Displaying a short time message containing date set by Date picker dialog fragment */
    		Toast.makeText(getBaseContext(), b.getString("set_date"), Toast.LENGTH_SHORT).show();
        }
	};
	
	public void savingPreference(String day,String month, String year){
	date = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	SharedPreferences.Editor date_editor = date.edit();
	date_editor.putString("Day", day);
	date_editor.putString("Month", month);
	date_editor.putString("Year", year);
	String set_date = month+"/"+day+"/"+year;
	
	date_editor.commit();
	
	String date = retrievePreference();
	
	//updating in database
	String user = LoginActivity.retrieveUsername();
	this.dh = new DatabaseHelper(this);
	this.dh.updateDate(user,set_date);
	
	}
	
	public String retrievePreference(){
		
		setDate = (date.getString("Year","n/a"));
		setDate += (date.getString("Month","n/a"));
		setDate += (date.getString("Day","n/a"));
		setDate += "00";
		
		
		return setDate;
	}

	// return 7 days from the date set

		public String getEndDate(){
			String endDate= null;
			Calendar endCal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd00");
			SharedPreferences date =  getSharedPreferences("date",0);
			//String startDay = (date.getString("Day","n/a"));
			int mEndDay = (Integer.parseInt(date.getString("Day","n/a")));
			int mEndMonth = (Integer.parseInt(date.getString("Month", "n/a")));
			int mEndYear = (Integer.parseInt(date.getString("Year", "n/a")));
			endCal.set(mEndYear, mEndMonth, mEndDay);
			endCal.add(Calendar.DAY_OF_YEAR, 7);
			
			endDate = dateFormat.format(endCal.getTime());
			
			return endDate;
	}
			
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_date);

		savedDate= (TextView)findViewById(R.id.textView1);
		if(date!= null)
			savedDate.setText(retrievePreference());
		  /** Click Event Handler for button */
        OnClickListener listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				/** Creating a bundle object to pass currently set date to the fragment */
				Bundle b = new Bundle();
				
				/** Adding currently set day to bundle object */
				b.putInt("set_day", mDay);
				
				/** Adding currently set month to bundle object */
				b.putInt("set_month", mMonth);
				
				/** Adding currently set year to bundle object */
				b.putInt("set_year", mYear);
				
				/** Instantiating DatePickerDialogFragment */
				DatePickerDialogFragment datePicker = new DatePickerDialogFragment(mHandler);
				
				/** Setting the bundle object on datepicker fragment */
				datePicker.setArguments(b);				
				
				/** Getting fragment manger for this activity */
				FragmentManager fm = getSupportFragmentManager();				
				
				/** Starting a fragment transaction */
				FragmentTransaction ft = fm.beginTransaction();
				
				/** Adding the fragment object to the fragment transaction */
				ft.add(datePicker, DatePickerDialogFragment.TAG);
				
				/** Opening the DatePicker fragment */
				ft.commit();
				
			}
		};
        
		/** Getting an instance of Set Date button */
        Button btnSet = (Button)findViewById(R.id.btnSet);
        
        /** Setting click event listener for the button */
        btnSet.setOnClickListener(listener);
	}
}
