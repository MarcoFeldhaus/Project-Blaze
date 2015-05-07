package com.mh_jmcdexample.pb_mh_jmcd;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Michael on 5/05/15.
 */
public class MyPreferenceActivity extends PreferenceActivity
{



    public MyPreferenceActivity() {}

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }


}


