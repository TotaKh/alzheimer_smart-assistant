package com.alzheimerapp.ui.main.UserType.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.auth.User;
import com.alzheimerapp.pojo.auth.UserP;
import com.alzheimerapp.ui.splash.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileCGFragment extends Fragment {

    private ProfileCgViewModel mViewModel;
    private TextView edit, url, url_txt, relation_txt, username_p_txt;
    private CircleImageView image;
    private EditText username, phone, address, username_p, relation;
    private ImageView share;
    private View view;

    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;
    DatabaseReference MyRef2;

    public static ProfileCGFragment newInstance() {
        return new ProfileCGFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_cg_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileCgViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        MyRef = database.child("users");
        MyRef2 = database.child("usersP");

        username = view.findViewById(R.id.username);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        username_p = view.findViewById(R.id.username_p);
        relation = view.findViewById(R.id.relation);
        share = view.findViewById(R.id.share);
        image = view.findViewById(R.id.image);
        edit = view.findViewById(R.id.edit);
        url = view.findViewById(R.id.url);

        url_txt = view.findViewById(R.id.url_txt);
        relation_txt = view.findViewById(R.id.relation_txt);
        username_p_txt = view.findViewById(R.id.username_p_txt);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMain.getInstance().getSharedPref().clearAllData();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        GetDataProfile();
        GetDataProfileP();
    }

    private void GetDataProfile() {
        MyRef.child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User response = dataSnapshot.getValue(User.class);

                if (AppMain.getInstance().getSharedPref().readString(SharedPref.APP_TYPE).equals("userP")) {

                    relation.setVisibility(View.GONE);
                    username_p.setVisibility(View.GONE);
                    url.setVisibility(View.GONE);
                    url_txt.setVisibility(View.GONE);
                    relation_txt.setVisibility(View.GONE);
                    username_p_txt.setVisibility(View.GONE);
                    share.setVisibility(View.GONE);

                } else if (AppMain.getInstance().getSharedPref().readString(SharedPref.APP_TYPE).equals("user")) {

                    username.setText(response.getUsername());
                    phone.setText(response.getPhone());
                    relation.setText(response.getRelation_type());
                    address.setText(response.getLocation());
                    url.setText("https://alzheimerapp-ebd11.firebaseio.com/" + response.getUid());

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String shareBody = url.getText().toString();
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

    private void GetDataProfileP() {
        MyRef2.child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserP response = dataSnapshot.getValue(UserP.class);

                if (AppMain.getInstance().getSharedPref().readString(SharedPref.APP_TYPE).equals("userP")) {

                    username.setText(response.getNameP());
                    phone.setText(response.getPhoneP());
                    address.setText(response.getLocationP());

                } else if (AppMain.getInstance().getSharedPref().readString(SharedPref.APP_TYPE).equals("user")) {

                    username_p.setText(response.getNameP());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

}
