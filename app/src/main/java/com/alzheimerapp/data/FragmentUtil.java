package com.alzheimerapp.data;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentUtil {

    public static void addFragment(Context context, Fragment fragment, int layout){

        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout,fragment);
        transaction.commit();
    }
    public static void addFragment(Context context, Fragment fragment, int layout, Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout,fragment);
        transaction.commit();
    }

    public static void addFragmentWithBackStack(Context context, Fragment fragment, int layout){
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static void addFragmentWithBackStack(Context context, Fragment fragment, int layout, Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragment(Context context, Fragment fragment, int layout){
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(layout,fragment).commit();
    }

    public static void replaceFragment(Context context, Fragment fragment, int layout, Bundle bundle){
        fragment.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(layout,fragment).commit();
    }

    public static void replaceFragmentWithBackStack(Context context, Fragment fragment, int layout){
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(layout,fragment).addToBackStack(null).commit();
    }

    public static void replaceFragmentWithBackStack(Context context, Fragment fragment, int layout, Bundle bundle){
        fragment.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(layout,fragment).addToBackStack(null).commit();
    }

}
