package com.mh_jmcdexample.pb_mh_jmcd;

/**
 * Created by Michael on 7/04/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        ((Main_HomeFragActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }




}
