package com.mh_jmcdexample.projectblaze_mh.helper;

/**
 * Created by Michael on 5/05/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Michael on 5/05/15.
 */

/*********************************************
 * To program this Login/Register System, the following code is based off the following Android Hive Tutorial Available at
 * http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/
 */
 /*
    Session Manager monitors and controls the user session accross the whole application, by checking the Boolean statement
    If Boolean == true, user is logged in
  */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ProjectBlaze";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session altered");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}