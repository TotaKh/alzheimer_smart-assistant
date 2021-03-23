package com.alzheimerapp.data;

import android.content.Context;

public class SharedPref {

    public static final String MAIN_FILENAME = "data";

    private String fileName;
    private Context context;

    private final long defValueLong = 0;

    public static final String APP_LANGUAGE = "app_language";
    public static final String APP_IS_LOGIN = "app_is_login";
    public static final String APP_FULL_NAME = "app_full_name";
    public static final String APP_EMAIL = "app_email";
    public static final String APP_MOBILE = "app_mobile";
    public static final String APP_COUNTRY = "app_country";
    public static final String APP_TYPE = "app_type";
    public static final String APP_UID = "app_uid";

    android.content.SharedPreferences sharedPreferences;
    android.content.SharedPreferences.Editor editor;

    public SharedPref(String fileName, Context context) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.fileName = fileName;
        this.context = context;
    }

    public void writeLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public long readLong(String key) {
        return sharedPreferences.getLong(key, defValueLong);
    }

    public void writeString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String readString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void writeBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public int readInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void writeInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public Boolean readBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void clearAllData() {
        sharedPreferences.edit().clear().commit();
    }

}