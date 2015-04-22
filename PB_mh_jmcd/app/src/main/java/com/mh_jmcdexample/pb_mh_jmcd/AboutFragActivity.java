package com.mh_jmcdexample.pb_mh_jmcd;

/**
 * Created by Michael on 7/04/15.
 */
import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;



public class AboutFragActivity extends Fragment implements GoogleMap.OnMarkerClickListener, ConnectionCallbacks, OnConnectionFailedListener{
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

    private GoogleMap map;
    private SupportMapFragment fragment;

    private static final String HTTP_URL = "http://10.0.0.5/markerTest/index.php";
    private static final String LOG_TAG = "Project Blaze Log Tag";
    private static final String TAG = "basic-location-sample";


    protected GoogleApiClient mGoogleApiClient;

    protected Location mLastLocation;

    protected float mLatitude;
    protected float mLongitude;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container,
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


        //buildGoogleApiClient();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();
            Toast.makeText(getActivity(), "Map was successfully created with AsyncTask", Toast.LENGTH_SHORT).show();
           // map.setOnMarkerClickListener(this);

            if (map !=null) {
                //ProjectBlazeMapSetup();
                new pindropretrieve().execute();
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setCompassEnabled(true);
                //getCurrentLocation();

            }
        }

    }

    public boolean onMarkerClick(Marker marker){
        Toast.makeText(getActivity(), "Pindrop Clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main_HomeFragActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

/*
    void getCurrentLocation() {
        Location myLocation = map.getMyLocation();

        if(myLocation!=null)
        {
            double myLatitude = myLocation.getLatitude();
            double myLongitude = myLocation.getLongitude();

            Log.i("PB_lat",":"+myLatitude);
            Log.i("PB_lng",":"+myLongitude);

        }
        else
        {
            Toast.makeText(getActivity(), "Failed to get User Location", Toast.LENGTH_SHORT).show();
        }


    }

*/


    @Override
    public void onConnected(Bundle connectionHint){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null){

            Log.i("PB_lat",":"+mLastLocation.getLatitude());
            Log.i("PB_lat",":"+mLastLocation.getLongitude());
        }
        else
        {
            Toast.makeText(getActivity(), "Failed to get User Location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class pindropretrieve extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String HTTP_URL = "http://10.0.0.11/markerTest/index.php";
            URL url = null;
            HttpURLConnection connect = null;
            StringBuilder json = new StringBuilder();
            try{
                url = new URL(HTTP_URL);
                connect = (HttpURLConnection) url.openConnection();
                InputStreamReader input = new InputStreamReader(connect.getInputStream());
                BufferedReader Reader = new BufferedReader(input);

                String Line = "";
                while ((Line = Reader.readLine()) !=null){
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

        protected void onPostExecute(String json){
            try{
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
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pindrop)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




    }
}



