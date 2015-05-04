package com.mh_jmcdexample.pb_mh_jmcd;

/**
 * Created by Michael on 7/04/15.
 */
import android.app.Activity;
import android.content.Intent;

import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class AboutFragActivity extends Fragment {


    private Button btnTwitter;
    private Button btnFacebook;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public static AboutFragActivity newInstance(int sectionNumber) {
        AboutFragActivity fragment = new AboutFragActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public AboutFragActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container,
                false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnTwitter = (Button) getView().findViewById(R.id.btnTwitter);
        btnFacebook = (Button) getView().findViewById(R.id.btnFacebook);


        btnTwitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                twitterPost();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                facebookPost();
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();


    }
/*
    public void twitterPost () {
        // This piece of code is to allow users to access Twitter to post tweets about our application with a default present text
        // This solution was from a user called Jonik at http://stackoverflow.com/questions/2077008/android-intent-for-twitter-application


        String tweetUrl =
                String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("Project Blaze - Hey Guys"), urlEncode(""));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        // Narrow down to official Twitter app, if available:
        List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        startActivity(intent);

    }
*/
    public void twitterPost () {

        // This piece of code is to allow users to access Twitter to post tweets about our application with a default present text
        // This solution was from a user called Jonik at http://stackoverflow.com/questions/2077008/android-intent-for-twitter-application

        //String urlToShare = "http://stackoverflow.com/questions/7545254";
        //String urlToShare = "http://www.AppStoreAppLink.com";
        String postShare = "Project Blaze - Hey Guys";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, postShare);

        // See if official Facebook app is found
        boolean twitterAppFound = false;
        List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
                twitterAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!twitterAppFound) {


            String tweetUrl =
                    String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                            urlEncode("Project Blaze - Hey Guys"), urlEncode(""));
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        }

        startActivity(intent);

    }
    public void facebookPost () {

        // This piece of code is to allow users to access Twitter to post tweets about our application with a default present text
        // This solution was from a user called Jonik at http://stackoverflow.com/questions/2077008/android-intent-for-twitter-application

        //String urlToShare = "http://stackoverflow.com/questions/7545254";
        String urlToShare = "http://www.AppStoreAppLink.com";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);

    }


// This piece of code is to allow users to access Twitter to post tweets about our application with a default present text
    // This Encoder solution was from a user called Jonik at http://stackoverflow.com/questions/2077008/android-intent-for-twitter-application

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {

            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }
    /* End of Jonik's Solution*/


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainHomeFragActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }



}



