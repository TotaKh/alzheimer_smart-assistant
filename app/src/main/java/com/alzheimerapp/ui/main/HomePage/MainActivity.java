package com.alzheimerapp.ui.main.HomePage;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.FragmentUtil;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.auth.User;
import com.alzheimerapp.pojo.auth.UserP;
import com.alzheimerapp.ui.main.UserType.addPhoto.AddPhotoFragment;
import com.alzheimerapp.ui.main.UserType.location.MapFragment;
import com.alzheimerapp.ui.main.UserType.profile.ProfileCGFragment;
import com.alzheimerapp.ui.main.UserType.album.AlbumFragment;
import com.alzheimerapp.ui.main.UserType.home.homePFragment;
import com.alzheimerapp.ui.main.UserType.reminder.CalenderFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLocation;
    private LinearLayout mAddCalender;
    private LinearLayout mHome;
    private LinearLayout mAddPhoto;
    private LinearLayout mAlbum;
    private FrameLayout mReplaceFragments;
    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;
    DatabaseReference MyRef2;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        MyRef = database.child("users");
        MyRef2 = database.child("usersP");
        type = AppMain.getInstance().getSharedPref().readString(SharedPref.APP_TYPE);

        if (type.equals("user")) {

            GetData(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID));

        } else if (type.equals("userP")) {

            GetDataP(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID));

        }

        initView();
    }

    private void initView() {
        mLocation = findViewById(R.id.location);
        mAddCalender = findViewById(R.id.add_calender);
        mHome = findViewById(R.id.home);
        mAddPhoto = findViewById(R.id.addPhoto);
        mAlbum = findViewById(R.id.album);
        mReplaceFragments = findViewById(R.id.replace_fragments);

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLocation(MainActivity.this);
            }
        });

        mAddCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalender(MainActivity.this);
            }
        });

        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddPhoto(MainActivity.this);
            }
        });

        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbum(MainActivity.this);
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(MainActivity.this);
            }
        });

        goToHome(MainActivity.this);

    }


    public void goToHome(Context context) {
        FragmentUtil.replaceFragment(context, homePFragment.newInstance(), R.id.replace_fragments);
    }

    public void goToLocation(Context context) {
        FragmentUtil.replaceFragment(context, MapFragment.newInstance(), R.id.replace_fragments);
    }

    public void goToAddPhoto(Context context) {
        FragmentUtil.replaceFragment(context, AddPhotoFragment.newInstance(), R.id.replace_fragments);
    }

    public void goToAlbum(Context context) {
        FragmentUtil.replaceFragment(context, AlbumFragment.newInstance(), R.id.replace_fragments);
    }

    public void goToCalender(Context context) {
        FragmentUtil.replaceFragment(context, CalenderFragment.newInstance(), R.id.replace_fragments);
    }

    private void GetData(String token) {

        MyRef.child(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User response = dataSnapshot.getValue(User.class);

                Toast.makeText(MainActivity.this, "مرحبا بك : "+response.getUsername(), Toast.LENGTH_SHORT).show();

                AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_FULL_NAME,response.getUsername());
                AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_MOBILE,response.getPhone());
                AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_EMAIL,response.getEmail());
                AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_COUNTRY,response.getLocation());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

    private void GetDataP(String token) {

        MyRef2.child(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserP response = dataSnapshot.getValue(UserP.class);

                Toast.makeText(MainActivity.this, "مرحبا بك : "+response.getNameP(), Toast.LENGTH_SHORT).show();

                AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_FULL_NAME,response.getNameP());

                mLocation.setVisibility(View.GONE);
                mAddPhoto.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

}
