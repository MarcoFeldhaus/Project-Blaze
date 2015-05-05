package com.mh_jmcdexample.pb_mh_jmcd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;


public class MainHomeFragActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction()
        //      .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
        //    .commit();

        switch (position + 1) {
            case 1:
                fragmentManager.beginTransaction().replace(R.id.container,
                        PlaceholderFragment.newInstance(position + 1)).commit();
                break;

            case 2:
                fragmentManager.beginTransaction().replace(R.id.container,
                        AboutFragActivity.newInstance(position + 1)).commit();
                break;

            case 3:
                fragmentManager.beginTransaction().replace(R.id.container,
                        SettingsFragActivity.newInstance(position + 1)).commit();
                break;


        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        private GoogleMap map;
        private SupportMapFragment fragment;

        private static final String HTTP_URL = "http://10.0.0.5/markerTest/index.php";
        private static final String LOG_TAG = "Project Blaze Log Tag";

    /*
     * Start of Google Update Location Code - Available at (Website) - Modified to fit App requirements

     */


        protected static final String TAG = "location-updates-sample";
        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
        /**
         * The fastest rate for active location updates. Exact. Updates will never be more frequent
         * than this value.
         */
        public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
                UPDATE_INTERVAL_IN_MILLISECONDS / 2;
        // Keys for storing activity state in the Bundle.
        protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
        protected final static String LOCATION_KEY = "location-key";
        protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
        /**
         * Provides the entry point to Google Play services.
         */
        protected GoogleApiClient mGoogleApiClient;
        /**
         * Stores parameters for requests to the FusedLocationProviderApi.
         */
        protected LocationRequest mLocationRequest;
        /**
         * Represents a geographical location.
         */
        protected Location mCurrentLocation;
        /**
         * Tracks the status of the location updates request. Value changes when the user presses the
         * Start Updates and Stop Updates buttons.
         */
        protected Boolean mRequestingLocationUpdates;

        /**
         * Time when the location was updated represented as a String.
         */
        protected String mLastUpdateTime;
        private TextView status, role, method;



    /*
     * End of Google Update Location Code - Available at (Website) - Modified to fit App requirements

     */


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_homemain, container,
                    false);
            //App will crash if anything methods/functions are in here

            return rootView;


        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            FragmentManager fm = getChildFragmentManager();

            new pindropretrieve().execute();


            if (fragment == null) {
                fragment = SupportMapFragment.newInstance();
                fm.beginTransaction().replace(R.id.map_container, fragment).commit();
            }


            mRequestingLocationUpdates = true;
            mLastUpdateTime = "";

            // Update values using data stored in the Bundle.
            updateValuesFromBundle(savedInstanceState);

            // Kick off the process of building a GoogleApiClient and requesting the LocationServices
            // API.
            buildGoogleApiClient();






        }

        @Override
        public void onResume() {
            super.onResume();


            if (map == null) {
                map = fragment.getMap();
                //Toast.makeText(getActivity(), "Map was successfully created with AsyncTask", Toast.LENGTH_SHORT).show();
                // map.setOnMarkerClickListener(this);

                if (map != null) {
                    //ProjectBlazeMapSetup();
                    //new pindropretrieve().execute();
                    //Change to where user location updates for real-time update


                    //new pindropupload().execute();
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    map.getUiSettings().setZoomControlsEnabled(true);
                    map.getUiSettings().setCompassEnabled(true);
                    //getCurrentLocation();


                    // Within {@code onPause()}, we pause location updates, but leave the
                    // connection to GoogleApiClient intact.  Here, we resume receiving
                    // location updates if the user has requested them.

                    if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                        startLocationUpdates();
                    }

                }
            }

        }

        //check for Network Connection.

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainHomeFragActivity) activity).onSectionAttached(getArguments().getInt(
                    ARG_SECTION_NUMBER));

        }




        /**
         * Updates fields based on data stored in the bundle.
         *
         * @param savedInstanceState The activity state saved in the Bundle.
         */
        private void updateValuesFromBundle(Bundle savedInstanceState) {
            Log.i(TAG, "Updating values from bundle");
            if (savedInstanceState != null) {
                // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
                // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
                if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                    mRequestingLocationUpdates = savedInstanceState.getBoolean(
                            REQUESTING_LOCATION_UPDATES_KEY);
                    //setButtonsEnabledState();
                }

                // Update the value of mCurrentLocation from the Bundle and update the UI to show the
                // correct latitude and longitude.
                if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                    // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                    // is not null.
                    mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
                }

                // Update the value of mLastUpdateTime from the Bundle and update the UI.
                if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                    mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
                }

            }
        }

        /**
         * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
         * LocationServices API.
         */
        protected synchronized void buildGoogleApiClient() {
            Log.i(TAG, "Building GoogleApiClient");
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }

        /**
         * Sets up the location request. Android has two location request settings:
         * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
         * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
         * the AndroidManifest.xml.
         * <p/>
         * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
         * interval (5 seconds), the Fused Location Provider API returns location updates that are
         * accurate to within a few feet.
         * <p/>
         * These settings are appropriate for mapping applications that show real-time location
         * updates.
         */
        protected void createLocationRequest() {
            mLocationRequest = new LocationRequest();

            // Sets the desired interval for active location updates. This interval is
            // inexact. You may not receive updates at all if no location sources are available, or
            // you may receive them slower than requested. You may also receive updates faster than
            // requested if other applications are requesting location at a faster interval.
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates faster than this value.
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        /**
         * Requests location updates from the FusedLocationApi.
         */
        protected void startLocationUpdates() {
            // The final argument to {@code requestLocationUpdates()} is a LocationListener
            // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }


        /**
         * Removes location updates from the FusedLocationApi.
         */
        protected void stopLocationUpdates() {
            // It is a good practice to remove location requests when the activity is in a paused or
            // stopped state. Doing so helps battery performance and is especially
            // recommended in applications that request frequent location updates.

            // The final argument to {@code requestLocationUpdates()} is a LocationListener
            // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        @Override
        public void onStart() {
            super.onStart();
            mGoogleApiClient.connect();
        }


        @Override
        public void onPause() {
            super.onPause();
            // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
            }
        }

        @Override
        public void onStop() {
            super.onStop();
            mGoogleApiClient.disconnect();
        }

        /**
         * Runs when a GoogleApiClient object successfully connects.
         */
        @Override
        public void onConnected(Bundle connectionHint) {
            Log.i(TAG, "Connected to GoogleApiClient");

            // If the initial location was never previously requested, we use
            // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
            // its value in the Bundle and check for it in onCreate(). We
            // do not request it again unless the user specifically requests location updates by pressing
            // the Start Updates button.
            //
            // Because we cache the value of the initial location in the Bundle, it means that if the
            // user launches the activity,
            // moves to a new location, and then changes the device orientation, the original location
            // is displayed as the activity is re-created.
            if (mCurrentLocation == null) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                //Toast.makeText(getActivity(), "Unable to retrieve current location", Toast.LENGTH_SHORT).show();


            }

            // If the user presses the Start Updates button before GoogleApiClient connects, we set
            // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
            // the value of mRequestingLocationUpdates and if it is true, we start location updates.
            if (mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        }


        /**
         * Callback that fires when the location changes.
         */
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

            Toast.makeText(getActivity(), "Location update", Toast.LENGTH_SHORT).show();

            new pindropretrieve().execute();
            mapClear();
            u_latlngPost();

        }

        public void mapClear (){
            if (map != null){
                map.clear();
            }
        }


        @Override
        public void onConnectionSuspended(int cause) {
            // The connection to Google Play services was lost for some reason. We call connect() to
            // attempt to re-establish the connection.
            Log.i(TAG, "Connection suspended");

            mGoogleApiClient.connect();
        }

        @Override
        public void onConnectionFailed(ConnectionResult result) {
            // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
            // onConnectionFailed.
            Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

        }


        /**
         * Stores activity data in the Bundle.
         */
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
            savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
            savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
            super.onSaveInstanceState(savedInstanceState);
        }


        public void u_latlngPost() {

            double latField = mCurrentLocation.getLatitude();
            double lngField = mCurrentLocation.getLongitude();

            String u_lat = Float.toString((float) latField);
            String u_lng = Float.toString((float) lngField);

            //new pindropupload().execute(u_lat, u_lng);
            new pindropretrieve().execute();
        }

        private class pindropupload extends AsyncTask<String, Void, String> {


            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... arg0) {
                try {
                    String u_lat = (String) arg0[0];
                    String u_lng = (String) arg0[1];
                    String link = "http://10.0.0.8/updateUserLoc/index.php";
                    String data = URLEncoder.encode("u_lat", "UTF-8")
                            + "=" + URLEncoder.encode(u_lat, "UTF-8");
                    data += "&" + URLEncoder.encode("u_lng", "UTF-8")
                            + "=" + URLEncoder.encode(u_lng, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();

                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());

                }

            }

        }

        //@Override
        protected void onPostExecute() {
            //this.statusField.setText("User Update Successful");
            //this.roleField.setText(result);
            //Toast.makeText(this, "Users Location was Successfully Uploaded to Database", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "Map was successfully created with AsyncTask", Toast.LENGTH_SHORT).show();

        }

        private class pindropretrieve extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String HTTP_URL = "http://10.0.0.6/markerTest/index.php";
                URL url = null;
                HttpURLConnection connect = null;
                StringBuilder json = new StringBuilder();
                try {
                    url = new URL(HTTP_URL);
                    connect = (HttpURLConnection) url.openConnection();
                    InputStreamReader input = new InputStreamReader(connect.getInputStream());
                    BufferedReader Reader = new BufferedReader(input);

                    String Line = "";
                    while ((Line = Reader.readLine()) != null) {
                        json.append(Line);
                    }
                    Reader.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return json.toString();
                //return null;
            }

            protected void onPostExecute(String json) {
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        //Add markers from json
                        map.addMarker(new MarkerOptions()
                                .title(jsonObj.getString("marker_title"))
                                .snippet(jsonObj.getString("snippet"))
                                .position(new LatLng(
                                        jsonObj.getJSONArray("latlng").getDouble(0),
                                        jsonObj.getJSONArray("latlng").getDouble(1)
                                ))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vectorpindrop)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

        /**
         * Copyright 2014 Google Inc. All Rights Reserved.
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         * http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */


    }
}
