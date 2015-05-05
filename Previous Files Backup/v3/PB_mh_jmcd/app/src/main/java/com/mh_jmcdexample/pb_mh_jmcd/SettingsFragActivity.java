package com.mh_jmcdexample.pb_mh_jmcd;

/**
 * Created by Michael on 7/04/15.
 */
import android.app.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragActivity extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SettingsFragActivity newInstance(int sectionNumber) {
        SettingsFragActivity fragment = new SettingsFragActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public SettingsFragActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container,
                false);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainHomeFragActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null){

            //getActivity().getSupportFragmentManager().beginTransaction()

            //Fragment newFragment = InfoFragment.newInstance(0);

            getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MyPreferenceFragment())
                    .commit();
        }
       // getFragmentManager().beginTransaction()
       // getActivity().getFragmentManager().beginTransaction()

                //replace R.id.Container does the Preferences on About Page
                //replace android.R.id.content
                        //  .replace(android.R.id.content, new MyPreferenceFragment())
                //.addToBackStack(null)
                        // .commit();


    }

    public class MyPreferenceFragment extends PreferenceFragment {
        public MyPreferenceFragment(){}

        @Override
        public void onCreate (final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }


    }




}
