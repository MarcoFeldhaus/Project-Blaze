package com.mh_jmcdexample.pb_mh_jmcd;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by Michael on 5/05/15.
 */
public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate (final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}


