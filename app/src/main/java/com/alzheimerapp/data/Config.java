package com.alzheimerapp.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alzheimerapp.R;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class Config {

    private static Config mInstance;

    public Context thisContext = AppMain.getInstance();

    public Config(Context thisContext) {
        this.thisContext = thisContext;
    }

    public boolean CheckNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) thisContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static Config getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new Config(context);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return mInstance;
    }

    public static String getAppLanguageStr() {
        return AppMain.getInstance().getSharedPref().readInt(SharedPref.APP_LANGUAGE) == 1 ? "en" : "ar";
    }

    public static void setStatusBarColorDark(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static int getAppLanguage() {
        return AppMain.getInstance().getSharedPref().readInt(SharedPref.APP_LANGUAGE);
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

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static Boolean isArabicLocale() {
        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("ar"))
            return true;
        else
            return false;
    }

    public void setArabicLocale() {
        Locale arabicLocale = new Locale("ar");
        Locale.setDefault(arabicLocale);
        Configuration anConfiguration = new Configuration();
        anConfiguration.locale = arabicLocale;
        thisContext.getResources().updateConfiguration(anConfiguration, thisContext.getResources().getDisplayMetrics());
    }

    public void setEnglishLocale() {
        Locale arabicLocale = new Locale("en");
        Locale.setDefault(arabicLocale);
        Configuration anConfiguration = new Configuration();
        anConfiguration.locale = arabicLocale;
        thisContext.getResources().updateConfiguration(anConfiguration, thisContext.getResources().getDisplayMetrics());

    }

}