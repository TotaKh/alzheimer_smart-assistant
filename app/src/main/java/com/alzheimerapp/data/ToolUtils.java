package com.alzheimerapp.data;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.collection.ArrayMap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class ToolUtils {

    public static boolean isOpen = false;

    public static boolean isValidPassword(String password) {
        return !Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$").matcher(password).matches();
    }


    public static boolean isPasswordEqual(String password, String rePassword) {
        return password.equals(rePassword);
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (imm != null) {
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public static boolean isMobileNumber(String number) {
        return Pattern.compile("^(05|5)([503649187])([0-9]{7})$").matcher(number).matches();
    }

    public static boolean isValidUserName(String userName) {
        return Pattern.compile("^[a-z0-9_.-]{3,15}$").matcher(userName).matches();
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("en"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean compareDate(String dT1, String dT2) {
        System.out.println("Date1 * " + dT1);
        System.out.println("Date2 *" + dT2);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(dT1);
            Date d2 = sdf.parse(dT2);
            String date1F = df.format(d1);
            String date2F = df.format(d2);
            Date date1 = df.parse(date1F);
            Date date2 = df.parse(date2F);
            System.out.println("Date1 " + df.format(date1));
            System.out.println("Date2 " + df.format(date2));
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            } else if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            } else {
                System.out.println("Date1 is equal Date2");
                return false;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            return true;
        }
    }


    public static String changeDateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        Date date1 = null;
        String formattedDate = date;
        try {
            date1 = df.parse(date);
            formattedDate = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String changeDateFormat1(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);

        Date date1 = null;
        String formattedDate = date;
        try {
            date1 = df.parse(date);
            formattedDate = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static long changeFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.parseLong(df.format(date1));
    }

    public static String changeFormatToRealDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(date1);
    }

    public static String changeFormatToRealTime(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa", Locale.US);
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(date1);
    }

    public static Date changeFormatToDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1;
    }


    public static String changeFormatToDateType(Date dateFrom, Date dateTo) {

        long diff = dateTo.getTime() - dateFrom.getTime();
        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);
        Log.e("test", "changeFormatToDate: " + diffDays);
        if (diffDays < 7) {
            return "يوم";
        } else {
            if (diffDays > 30) {
                return "شهر";
            } else {
                return "أسبوع";
            }
        }
    }

    public static String changeFormatToDateType(int diffDays, String type) {

        Log.e("test", "changeFormatToDate: " + diffDays + ":" + type);
        if (type.equalsIgnoreCase("يوم")) {
            if (diffDays < 7) {
                return "يوم";
            } else {
                if (diffDays > 30) {
                    return "شهر";
                } else {
                    return "أسبوع";
                }
            }
        } else {
            if (diffDays < 24) {
                return "يوم";
            } else {
                if (diffDays > 168) {
                    return "شهر";
                } else {
                    return "أسبوع";
                }
            }
        }
    }

    public static String changeFormatToDateType(String cDate) {
        Date cDateDate = changeFormatToDate(cDate);

        if (isThisDateWithin1DayRange(cDateDate)) {
            return "يوم";
        }
        if (isThisDateWithin1WeekRange(cDateDate)) {
            return "أسبوع";
        }
        if (isThisDateWithin1MonthsRange(cDateDate)) {
            return "شهر";
        }

        return "";
    }

    public static boolean isThisDateWithin1MonthsRange(Date dateToValidate) {

        // current date after 3 months
        Calendar currentDateAfter3Months = Calendar.getInstance();
        currentDateAfter3Months.add(Calendar.MONTH, 1);

        // current date before 3 months
        Calendar currentDateBefore3Months = Calendar.getInstance();
        currentDateBefore3Months.add(Calendar.MONTH, -1);

        /*************** verbose ***********************/
        System.out.println("\n\ncurrentDate : "
                + Calendar.getInstance().getTime());
        System.out.println("currentDateAfter3Months : "
                + currentDateAfter3Months.getTime());
        System.out.println("currentDateBefore3Months : "
                + currentDateBefore3Months.getTime());
        System.out.println("dateToValidate : " + dateToValidate);
        /************************************************/

        if (dateToValidate.before(currentDateAfter3Months.getTime())
                && dateToValidate.after(currentDateBefore3Months.getTime())) {

            //ok everything is fine, date in range
            return true;

        } else {

            return false;

        }

    }

    public static boolean isThisDateWithin1DayRange(Date dateToValidate) {

        // current date after 3 months
        Calendar currentDateAfter3Months = Calendar.getInstance();
        currentDateAfter3Months.add(Calendar.DATE, 1);

        // current date before 3 months
        Calendar currentDateBefore3Months = Calendar.getInstance();
        currentDateBefore3Months.add(Calendar.DATE, -1);

        /*************** verbose ***********************/
        System.out.println("\n\ncurrentDate : "
                + Calendar.getInstance().getTime());
        System.out.println("currentDateAfter3Months : "
                + currentDateAfter3Months.getTime());
        System.out.println("currentDateBefore3Months : "
                + currentDateBefore3Months.getTime());
        System.out.println("dateToValidate : " + dateToValidate);
        /************************************************/

        if (dateToValidate.before(currentDateAfter3Months.getTime())
                && dateToValidate.after(currentDateBefore3Months.getTime())) {

            //ok everything is fine, date in range
            return true;

        } else {

            return false;

        }

    }

    public static boolean isThisDateWithin1WeekRange(Date dateToValidate) {

        // current date after 3 months
        Calendar currentDateAfter3Months = Calendar.getInstance();
        currentDateAfter3Months.add(Calendar.WEEK_OF_MONTH, 1);

        // current date before 3 months
        Calendar currentDateBefore3Months = Calendar.getInstance();
        currentDateBefore3Months.add(Calendar.WEEK_OF_MONTH, -1);

        /*************** verbose ***********************/
        System.out.println("\n\ncurrentDate : "
                + Calendar.getInstance().getTime());
        System.out.println("currentDateAfter3Months : "
                + currentDateAfter3Months.getTime());
        System.out.println("currentDateBefore3Months : "
                + currentDateBefore3Months.getTime());
        System.out.println("dateToValidate : " + dateToValidate);
        /************************************************/

        if (dateToValidate.before(currentDateAfter3Months.getTime())
                && dateToValidate.after(currentDateBefore3Months.getTime())) {

            //ok everything is fine, date in range
            return true;

        } else {

            return false;

        }

    }

    public static String changeFormatToDate(Date dateFrom, Date dateTo) {

        long diff = dateTo.getTime() - dateFrom.getTime();
        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long diffWeek = diffDays / 7;
        long diffMonth = diffWeek / 4;
        Log.e("test", "diffDays: " + diffDays);
        Log.e("test", "diffWeek: " + diffWeek);
        Log.e("test", "diffMonth: " + diffMonth);
        if (diffDays < 7) {
            return diffDays + " يوم ";
        } else {
            if (diffDays > 30) {
                return diffMonth + " شهر ";
            } else {
                return diffWeek + " اسبوع ";
            }
        }
    }

    public static String changeDateFormatToString(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }


    public static String convTo2Digtes(int number) {
        NumberFormat formatter = new DecimalFormat("00");
        return formatter.format(number);
    }

    public static boolean compareDateToCurrent(String d2) {
        System.out.println("Date2 *" + d2);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = sdf.parse(getDateTime());
            Date date2 = sdf.parse(d2);
            System.out.println("Date1 " + sdf.format(date1));
            System.out.println("Date2 " + sdf.format(date2));
            System.out.println();
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            } else if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            } else {// if(date1.equals(date2)){
                System.out.println("Date1 is equal Date2");
                return true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public static Bundle toBundle(ArrayMap<String, String> input) {
        Bundle output = new Bundle();
        for (String key : input.keySet()) {
            output.putString(key, input.get(key));
        }
        return output;
    }

    public static <T extends String> ArrayMap<String, String> fromBundle(Bundle input) {
        ArrayMap<String, String> output = new ArrayMap<String, String>();
        for (String key : input.keySet()) {
            output.put(key, (input.getString(key)));
        }
        return output;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

}
