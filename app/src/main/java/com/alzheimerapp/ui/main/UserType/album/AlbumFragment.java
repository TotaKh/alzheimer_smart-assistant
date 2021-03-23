package com.alzheimerapp.ui.main.UserType.album;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alzheimerapp.R;
import com.alzheimerapp.adapters.AlbumPicAdapter;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.faceRec.Activities.MainActivity;
import com.alzheimerapp.pojo.gallery.GalleryImg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class AlbumFragment extends Fragment {

    private AlbumViewModel mViewModel;
    private RecyclerView rv_person;
    private View view;

    private AlbumPicAdapter homePicAdapter;
    private ArrayList<GalleryImg> galleryImgs = new ArrayList<>();
    private AlphaInAnimationAdapter alphaAdapter2;

    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;
    DatabaseReference MyRef2;
    TextView upload_fast;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.album_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        rv_person = view.findViewById(R.id.rv_person);
        upload_fast = view.findViewById(R.id.upload_fast);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        MyRef2 = database.child("album");

        GetDataImg();

        upload_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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
                rv_person.setLayoutManager(new GridLayoutManager(getActivity(),2));
                homePicAdapter = new AlbumPicAdapter(getActivity(), galleryImgs);
                alphaAdapter2 = new AlphaInAnimationAdapter(homePicAdapter);
                rv_person.setAdapter(new ScaleInAnimationAdapter(alphaAdapter2));
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
