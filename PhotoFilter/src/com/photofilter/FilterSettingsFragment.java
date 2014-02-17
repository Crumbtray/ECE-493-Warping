package com.photofilter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FilterSettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Load the preferences from an XML Resource
		addPreferencesFromResource(R.xml.filter_settings);
	}

}
