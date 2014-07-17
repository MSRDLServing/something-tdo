package com.android.eventchaser;

import android.support.v4.app.Fragment;

public class ListViewActivity extends SingleFragmentActivity {

	@Override
    protected Fragment createFragment() {
		return new ListViewFragment();
	}

}
