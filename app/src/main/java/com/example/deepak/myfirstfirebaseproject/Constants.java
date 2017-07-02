package com.example.deepak.myfirstfirebaseproject;

/**
 * Created on 12/4/17.
 */


public class Constants {
    static class Config {
        //Keys to send username, password, phone and otp
        public static final String KEY_USERNAME = "username";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_OTP = "otp";
    }

    public static class SharedPreferences {
        public static final String SHARED_PREFERENCES_FILE_NAME = "smart_agriculture";
        public static final String FARMER_ID = "farmer_id";
        public static final String FCM_TOKEN = "fcm_token";
        public static final String LOGIN_FLAG = "login_flag";
    }
}
