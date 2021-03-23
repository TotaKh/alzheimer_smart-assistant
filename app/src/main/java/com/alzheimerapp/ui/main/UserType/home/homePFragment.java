package com.alzheimerapp.ui.main.UserType.home;

import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alzheimerapp.R;
import com.alzheimerapp.adapters.HomeNotifyAdapter;
import com.alzheimerapp.adapters.HomePicAdapter;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.FragmentUtil;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.alert.Alert;
import com.alzheimerapp.pojo.gallery.GalleryImg;
import com.alzheimerapp.ui.main.UserType.profile.ProfileCGFragment;
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

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class homePFragment extends Fragment {

    private HomeViewModel mViewModel;
    private View view;
    private RecyclerView rv_notify , rv_photos;
    private TextView fullname;
    private LinearLayout click_row;

    private HomeNotifyAdapter viewUserAdapter;
    private ArrayList<Alert> viewUserArrayList = new ArrayList<>();
    private AlphaInAnimationAdapter alphaAdapter;

    private HomePicAdapter homePicAdapter;
    private ArrayList<GalleryImg> galleryImgs = new ArrayList<>();
    private AlphaInAnimationAdapter alphaAdapter2;

    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;
    DatabaseReference MyRef2;

    public static homePFragment newInstance() {
        return new homePFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);


        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        MyRef = database.child("alerts");
        MyRef2 = database.child("album");

        rv_notify = view.findViewById(R.id.rv_notify);
        fullname = view.findViewById(R.id.fullname);
        rv_photos = view.findViewById(R.id.rv_photos);
        click_row  = view.findViewById(R.id.click_row);

        fullname.setText(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_FULL_NAME));

        click_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.replaceFragment(getActivity(), ProfileCGFragment.newInstance(),R.id.replace_fragments);
            }
        });

        GetDataReminder();
        GetDataImg();
    }

    private void GetDataReminder() {
        viewUserArrayList.clear();

        MyRef.child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("key", child.getKey());
                    Log.d("ref", child.getRef().toString());
                    Log.d("val", child.getValue().toString());
                    Alert response = child.getValue(Alert.class);
                    viewUserArrayList.add(response);

                }

                rv_notify.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                viewUserAdapter = new HomeNotifyAdapter(getActivity(), viewUserArrayList);
                alphaAdapter = new AlphaInAnimationAdapter(viewUserAdapter);
                rv_notify.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                viewUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }
    private void GetDataImg() {
        galleryImgs.clear();

        MyRef2.child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("key", child.getKey());
                    Log.d("ref", child.getRef().toString());
                    Log.d("val", child.getValue().toString());
                    GalleryImg response = child.getValue(GalleryImg.class);
                    galleryImgs.add(response);

                }

                rv_photos.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                homePicAdapter = new HomePicAdapter(getActivity(), galleryImgs);
                alphaAdapter2 = new AlphaInAnimationAdapter(homePicAdapter);
                rv_photos.setAdapter(new ScaleInAnimationAdapter(alphaAdapter2));
                homePicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

}
