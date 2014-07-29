package com.example.somethingtdo;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.somethingtdo.R;

public class SplashScreenActivity extends Activity {

	protected boolean active =true;
	protected int splashTime = 3000;
	protected int timeIncrement=100;
	protected int sleepTime = 100;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

      /*  if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
 //thread for displaying the SplashScreen
        
        Thread splashThread = new Thread(){
        @Override
        public void run() {
        	try{
        		int elapsedTime= 0;
        		while(active && (elapsedTime < splashTime)){
        			
        			sleep(sleepTime);
        			if(active)
        				elapsedTime = elapsedTime + timeIncrement;
        		}
        	}catch (InterruptedException e){
        			//do nothing
        		}finally {
        			finish();
        			startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
        			}
        	}
        }; 
        splashThread.start();
        
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }
*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_splash_screen, container, false);
            return rootView;
        }
    }*/

}
