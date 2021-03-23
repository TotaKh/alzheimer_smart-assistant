package com.alzheimerapp.data;

import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import java.util.Locale;

public class AppMain extends MultiDexApplication {

    public static AppMain mInstance;

    private static Context context;

    boolean verif = false;

    SharedPref sharedPref;

    public static String sDefSystemLanguage;

    public static final String TAG = AppMain.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mInstance = this;
        sharedPref = new SharedPref(SharedPref.MAIN_FILENAME, this);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/font.ttf");
        setAppLocale();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized AppMain getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }


    public void setAppLocale() {
        if (AppMain.getInstance().getSharedPref().readInt(SharedPref.APP_LANGUAGE) == 1) {
            setEnglishLocale();
        } else if (AppMain.getInstance().getSharedPref().readInt(SharedPref.APP_LANGUAGE) == 2) {
            setArabicLocale();
        } else if (AppMain.getInstance().getSharedPref() == null) {
            AppMain.getInstance().getSharedPref().writeInt(SharedPref.APP_LANGUAGE, 1);
            setEnglishLocale();
        } else {
            AppMain.getInstance().getSharedPref().writeInt(SharedPref.APP_LANGUAGE, 1);
            setEnglishLocale();
        }
    }

    public void setArabicLocale() {
        Locale arabicLocale = new Locale("ar");
        Locale.setDefault(arabicLocale);
        Configuration anConfiguration = new Configuration();
        anConfiguration.locale = arabicLocale;
        context.getResources().updateConfiguration(anConfiguration, context.getResources().getDisplayMetrics());
    }

    public SharedPref getSharedPref() {
        if (sharedPref != null)
            return sharedPref;
        else
            return new SharedPref(SharedPref.MAIN_FILENAME, this);
    }

    public void setEnglishLocale() {
        Locale arabicLocale = new Locale("en");
        Locale.setDefault(arabicLocale);
        Configuration anConfiguration = new Configuration();
        anConfiguration.locale = arabicLocale;
        context.getResources().updateConfiguration(anConfiguration, context.getResources().getDisplayMetrics());

    }

}
