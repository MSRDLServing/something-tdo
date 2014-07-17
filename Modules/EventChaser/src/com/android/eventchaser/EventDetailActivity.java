package com.android.eventchaser;

import android.support.v4.app.Fragment;

public class EventDetailActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new EventDeatilFragment();
	}
}
