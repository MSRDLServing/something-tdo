package com.example.somethingtdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.somethingtdo.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrefsActivity extends Activity {
  
  private static final String TAG = "Interest";	
	
  private ListView mainListView ;
  private Interest[] interests ;
  private ArrayAdapter<Interest> listAdapter ;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    // Find the ListView resource. 
    mainListView = (ListView) findViewById( R.id.inteListView );
    
    // When item is tapped, toggle checked properties of CheckBox and Planet.
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick( AdapterView<?> parent, View item, 
                               int position, long id) {
    	  //If the item/position equals to the "all" check box, then for every item/row in the list view, set them checked.
        Interest planet = listAdapter.getItem( position );
        planet.toggleChecked();
        PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
        viewHolder.getCheckBox().setChecked( planet.isChecked() );
      }
    });

    
    // Create and populate planets.
    interests = (Interest[]) getLastNonConfigurationInstance() ;
    if ( interests == null ) {
      interests = new Interest[] { 
    		  new Interest("Concert","music"), new Interest("Comedy","comedy"), new Interest("Performing Arts","performing_arts"), 
	          new Interest("Sports","sports"), new Interest("Film","movies-film"), new Interest("Galleries","art"), 
	          new Interest("Literary","books"), new Interest("Food","food"), new Interest("Festivals","festivals_parades")
	
      };  
    }
    ArrayList<Interest> planetList = new ArrayList<Interest>();
    planetList.addAll( Arrays.asList(interests) );
    
    // Set our custom array adapter as the ListView's adapter.
    listAdapter = new PlanetArrayAdapter(this, planetList);
    mainListView.setAdapter( listAdapter );      
  }
  
  public void onPickInterestClick(View v) {
	int len = interests.length;
	String msg = "";
	for (int i = 0; i < len; i++) {
		if (interests[i].isChecked()) {
			Log.d(TAG, interests[i].getId());
			msg += (interests[i].getId() + ":");
		}
	}
	Log.d(TAG, "All selected:" + msg);
	Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }
  
  public void onSelectAllClick(View v) {
	int len = interests.length;
	String msg = "";
	for (int i = 0; i < len; i++) {
		interests[i].setChecked(true);
//		if (planets[i].isChecked()) {
//			Log.d(TAG, planets[i].getName());
//			msg += (planets[i].getName() + ":");
//		}
	}
	int num = mainListView.getChildCount();
	CheckBox checkBox;
	for (int i = 0; i < num; i++) {
		View row = mainListView.getChildAt(i);
		checkBox = (CheckBox)row.findViewById( R.id.CheckBox01);
		checkBox.setChecked(true);
	}
//	Log.d(TAG, "All selected 2:" + msg);
//	Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	
  }
  
  public void onUnselectAllClick(View v) {
	int len = interests.length;
	String msg = "";
	for (int i = 0; i < len; i++) {
		interests[i].setChecked(false);
//		if (planets[i].isChecked()) {
//			Log.d(TAG, planets[i].getName());
//			msg += (planets[i].getName() + ":");
//		}
	}
	int num = mainListView.getChildCount();
	CheckBox checkBox;
	for (int i = 0; i < num; i++) {
		View row = mainListView.getChildAt(i);
		checkBox = (CheckBox)row.findViewById( R.id.CheckBox01);
		checkBox.setChecked(false);
	}
//	Log.d(TAG, "All selected 2:" + msg);
//	Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	
  }
  
  /** Holds planet data. */
  private static class Interest {
    private String name = "" ;
    private String id;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private boolean checked = false ;
    public Interest() {}
    public Interest( String name, String id) {
      this.name = name ;
      this.id = id;
    }
    public Interest( String name, boolean checked ) {
      this.name = name ;
      this.checked = checked ;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public boolean isChecked() {
      return checked;
    }
    public void setChecked(boolean checked) {
      this.checked = checked;
    }
    public String toString() {
      return name ; 
    }
    public void toggleChecked() {
      checked = !checked ;
    }
  }
  
  /** Holds child views for one row. */
  private static class PlanetViewHolder {
    private CheckBox checkBox ;
    private TextView textView ;
    public PlanetViewHolder() {}
    public PlanetViewHolder( TextView textView, CheckBox checkBox ) {
      this.checkBox = checkBox ;
      this.textView = textView ;
    }
    public CheckBox getCheckBox() {
      return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
      this.checkBox = checkBox;
    }
    public TextView getTextView() {
      return textView;
    }
    public void setTextView(TextView textView) {
      this.textView = textView;
    }    
  }
  
  /** Custom adapter for displaying an array of Planet objects. */
  private static class PlanetArrayAdapter extends ArrayAdapter<Interest> {
    
    private LayoutInflater inflater;
    
    public PlanetArrayAdapter( Context context, List<Interest> planetList ) {
      super( context, R.layout.interest_row, R.id.rowTextView, planetList );
      // Cache the LayoutInflate to avoid asking for a new one each time.
      inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // Planet to display
      Interest planet = (Interest) this.getItem( position ); 

      // The child views in each row.
      CheckBox checkBox ; 
      TextView textView ; 
      
      // Create a new row view
      if ( convertView == null ) {
        convertView = inflater.inflate(R.layout.interest_row, null);
        
        // Find the child views.
        textView = (TextView) convertView.findViewById( R.id.rowTextView );
        checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );
        
        // Optimization: Tag the row with it's child views, so we don't have to 
        // call findViewById() later when we reuse the row.
        //Corresponding to line 188, which retrieves the TAG from the convertView.
        convertView.setTag( new PlanetViewHolder(textView,checkBox) );

        // If CheckBox is toggled, update the planet it is tagged with.
        checkBox.setOnClickListener( new View.OnClickListener() {
          public void onClick(View v) {
            CheckBox cb = (CheckBox) v ;
            Interest planet = (Interest) cb.getTag();
            planet.setChecked( cb.isChecked() );
          }
        });        
      }
      // Reuse existing row view
      else {
        // Because we use a ViewHolder, we avoid having to call findViewById().
        PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
        checkBox = viewHolder.getCheckBox() ;
        textView = viewHolder.getTextView() ;
      }

      // Tag the CheckBox with the Planet it is displaying, so that we can
      // access the planet in onClick() when the CheckBox is toggled.
      //Corresponding to line 180, which gets the TAG(planet) from the checkbox. So the getView really maps to each row.
      checkBox.setTag( planet ); 
      
      // Display planet data
      checkBox.setChecked( planet.isChecked() );
      textView.setText( planet.getName() );      
      
      return convertView;
    }
    
  }
  
  public Object onRetainNonConfigurationInstance() {
    return interests ;
  }
}