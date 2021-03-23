package com.alzheimerapp.ui.main.UserType.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.alzheimerapp.R;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private View view;
    private SupportMapFragment mapFragment;
    private FragmentTransaction fragmentTransaction;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            SupportMapFragment  mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }


    }

}
