package com.example.somethingtdo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnItemClickListener {

	private static final String TAG = "MAINACTIVITY";
	
	private static final String[] ITEMS =
        {"Open Map", "Open List", "Set Preferences", "View Profile", "Set Date", "Set Location"};
    private static final int[] COLORS =
        {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};
     
    private DrawerLayout mDrawerContainer;
    /* Root content pane in layout */
    private View mMainContent;
    /* Main (left) sliding drawer */
    private ListView mDrawerContent;
    /* Toggle object for ActionBar */
    private ActionBarDrawerToggle mDrawerToggle;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        mDrawerContainer =
            (DrawerLayout) findViewById(R.id.container_drawer);
        mDrawerContent =
            (ListView) findViewById(R.id.drawer_main);
        mMainContent = findViewById(R.id.container_root);
         
        //Toggle indicator must also be the drawer listener,
        // so we extend it to listen for the events ourselves.
        mDrawerToggle  = new ActionBarDrawerToggle(
                this,                 //Host Activity
                mDrawerContainer,     //Container to use
                R.drawable.ic_menu_friendslist, //Drawable for action icon
                0, 0) {               //Content descriptions
 
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Update the options menu
                invalidateOptionsMenu();
            }
             
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                //Update the options menu
                invalidateOptionsMenu();
            }
             
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Update the options menu
                invalidateOptionsMenu();
            }
        };
        //Set the toggle as the drawer's event listener
        mDrawerContainer.setDrawerListener(mDrawerToggle);
         
        //Enable home button actions in the ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        //Our application uses Holo.Light, which defaults to
        // dark text; ListView also has a dark background.
        // Create a custom context so the views inflated by
        // ListAdapter use Holo, to display them with light text
        ContextThemeWrapper wrapper =
                new ContextThemeWrapper(this,
                        android.R.style.Theme_Holo);
        ListAdapter adapter = new ArrayAdapter<String>(wrapper,
                android.R.layout.simple_list_item_1, ITEMS);
        mDrawerContent.setAdapter(adapter);
        mDrawerContent.setOnItemClickListener(this);
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Synchronize the state of the drawer after any instance
        // state has been restored by the framework
        mDrawerToggle.syncState();
    }
     
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Update state on any configuration changes
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create the ActionBar actions
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
     
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Display the action options based on main drawer state
        boolean isOpen =
                mDrawerContainer.isDrawerVisible(mDrawerContent);
        menu.findItem(R.id.action_settings).setVisible(!isOpen);
         
        return super.onPrepareOptionsMenu(menu);
    }
     
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Let the drawer have a crack at the event first
        // to handle home button events
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            //If this was a drawer toggle, we need to update the
            // options menu, but we have to wait until the next
            // loop iteration for the drawer state to change.
            mDrawerContainer.post(new Runnable() {
                @Override
                public void run() {
                    //Update the options menu
                    invalidateOptionsMenu();
                }
            });
            return true;
        }
         
        //...Handle other options selections here as normal...
        switch (item.getItemId()) {
            case R.id.action_settings:
            	Intent i = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
     
    //Handle click events from items in the main drawer list
    @Override
    public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
    	
    	if (position == 0) { //Map view
        	Toast.makeText(getApplicationContext(), "Map view", Toast.LENGTH_SHORT).show();
        	Intent i = new Intent(this, MapActivity.class);
        	startActivity(i);
    	}
    	
    	if (position == 1) { //List view
        	Toast.makeText(getApplicationContext(), "List view", Toast.LENGTH_SHORT).show();
        	String selectedInterests = PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(PreferenceListFragment.SELECTED_INTEREST, null);
        	Log.d(TAG, " selectedInterests retrieved:" + selectedInterests);
           Intent i = new Intent(this, ListViewActivity.class);
//            i.putExtra(PreferenceFragment.EXTRA_CRIME_ID, crime.getId());
            startActivity(i);
    	}
    	
    	if (position == 2) { //Settings
    		
    		Intent i = new Intent(this, PreferenceActivity.class);
    		startActivity(i);
    		
    	}
    	
    	if (position == 3) { //Settings
    		
    		Intent i = new Intent(this, ProfileActivity.class);
    		startActivity(i);
    		
    	}
    	
    	if (position == 4) {  //Settings
    		
    		Intent i = new Intent(this, SetDateActivity.class);
    		startActivity(i);
    		
    	}
    	
    	if (position == 5) { //Settings
    		
    		Intent i = new Intent(this, SetLocationActivity.class);
    		startActivity(i);
    		
    	}
    	
        //Update the background color of the main content
//        mMainContent.setBackgroundColor(COLORS[position]);
         
        //Manually close the drawer
        mDrawerContainer.closeDrawer(mDrawerContent);
    }

}