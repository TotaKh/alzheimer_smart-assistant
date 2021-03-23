package com.alzheimerapp.ui.main.UserType.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alzheimerapp.R;
import com.alzheimerapp.adapters.HomeNotifyAdapter;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.alert.Alert;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class CalenderFragment extends Fragment {

    private CalenderViewModel mViewModel;
    private View view;
    private TextView add_reminder_calender;
    private LayoutInflater inflater;
    Dialog dailogSuccess;
    View dialogViewSuccess;

    private TextView add_alert, date_time;
    private EditText alert_name;

    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;

    private Calendar myCalendar;

    private RecyclerView rv_events;
    private HomeNotifyAdapter viewUserAdapter;
    private ArrayList<Alert> viewUserArrayList = new ArrayList<>();
    private AlphaInAnimationAdapter alphaAdapter;

    public static CalenderFragment newInstance() {
        return new CalenderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.calender_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        MyRef = database.child("alerts");
        mViewModel = ViewModelProviders.of(this).get(CalenderViewModel.class);
        myCalendar = Calendar.getInstance();

        GetData();

        rv_events = view.findViewById(R.id.rv_events);
        add_reminder_calender = view.findViewById(R.id.add_reminder_calender);

        add_reminder_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlert();
            }
        });


    }

    private void GetData() {
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

                rv_events.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                viewUserAdapter = new HomeNotifyAdapter(getActivity(), viewUserArrayList);
                alphaAdapter = new AlphaInAnimationAdapter(viewUserAdapter);
                rv_events.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                viewUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

    private void addAlert() {

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(getActivity());

        dailogSuccess = new Dialog(getActivity());
        dailogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogSuccess.setCancelable(true);
        dialogViewSuccess = inflater.inflate(R.layout.dailog_add_alert, null);
        dailogSuccess.setContentView(dialogViewSuccess);
        dailogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dailogSuccess.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dailogSuccess.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dailogSuccess.show();

        add_alert = dialogViewSuccess.findViewById(R.id.add_alert);
        alert_name = dialogViewSuccess.findViewById(R.id.alert_name);
        date_time = dialogViewSuccess.findViewById(R.id.date_time);


        date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "YYYY-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        Toast.makeText(getActivity(), sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
                        date_time.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        add_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert_name.getText().toString().trim().equals("") || date_time.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "جميع الحقول مطلوبة ! ", Toast.LENGTH_SHORT).show();

                } else {
                    AddAlert(alert_name.getText().toString().trim(), date_time.getText().toString().trim());
                }
            }
        });

    }

    private void AddAlert(final String name, final String dateTime) {

        Toast.makeText(getActivity(), "جاري التحميل ...", Toast.LENGTH_SHORT).show();

        Alert alert = new Alert(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID), name, dateTime);

        database.child("alerts").child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).push().setValue(alert);

        Toast.makeText(getActivity(), "تم الاضافة بنجاح ...", Toast.LENGTH_SHORT).show();

        dailogSuccess.dismiss();

        GetData();

    }
}
