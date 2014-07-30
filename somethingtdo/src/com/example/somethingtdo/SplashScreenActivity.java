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
}
