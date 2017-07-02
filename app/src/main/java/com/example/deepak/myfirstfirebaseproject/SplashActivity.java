package com.example.deepak.myfirstfirebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class SplashActivity extends Activity implements CallActivityInterface {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new SplashPresenter(this).holdScreen(this);
    }

    /**
     * Call the next activity to be displayed,
     * depending upon whether the user is logged in or not
     */
    @Override
    public void callActivity() {
        Boolean isLoggedIn;             //True when user is logged in
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        isLoggedIn = pref.getBoolean(Constants.SharedPreferences.LOGIN_FLAG, false);
        if (isLoggedIn) {
            startAnotherActivity(MainActivity.class);
        } else {
            startAnotherActivity(LoginActivity.class);
        }
    }

    public void startAnotherActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
