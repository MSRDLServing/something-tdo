package com.example.preferencelist;

import android.support.v4.app.Fragment;

public class EventActivity extends SingleFragmentActivity {

	@Override
    public Fragment createFragment() {
        return new EventFragment();
    }

}
