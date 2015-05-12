package com.mh_jmcdexample.pb_mh_jmcd;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by Michael on 11/05/15.
 */
public class AcknowledgeActivity extends ActionBarActivity {

        @Override
        public void onCreate (final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_acknowledge);

            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeButtonEnabled(true);





        }



        /***************
         *
         * This code solution is to allow the user to navigate back to the previous activity/fragment that they were on
         * This solution was provided by a user called Alvin Alexander.
         * Available from http://alvinalexander.com/android/enable-android-activity-app-icon-home-actionbar-tapped
         *
         */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:
                    startActivityAfterCleanup(MainHomeFragActivity.class);
                    return true;
            }


            return super.onOptionsItemSelected(item);
        }
        private void startActivityAfterCleanup(Class<?>cls){
            Intent intent = new Intent(getApplicationContext(),cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }


