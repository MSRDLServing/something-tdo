package com.example.preferencelist;

import android.support.v4.app.Fragment;

public class PreferenceActivity extends SingleFragmentActivity {

	@Override
    protected Fragment createFragment() {
		return new PreferenceListFragment();
	}

}
