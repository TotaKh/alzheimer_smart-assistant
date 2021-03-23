package com.alzheimerapp.ui.auth.loginCG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.auth.UserP;
import com.alzheimerapp.ui.main.HomePage.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginCGActivity extends AppCompatActivity {

    private EditText mCodeNum;
    private TextView mLoginCg;
    DatabaseReference database;
    DatabaseReference MyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_c_g);
        database = FirebaseDatabase.getInstance().getReference();
        MyRef = database.child("usersP");
        initView();
    }

    private void initView() {
        mCodeNum = findViewById(R.id.code_num);
        mLoginCg = findViewById(R.id.login_cg);

        mLoginCg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCodeNum.getText().toString().equals("")) {

                    Toast.makeText(LoginCGActivity.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();

                } else {

                    GetData();

                }
            }
        });

    }

    private void GetData() {
        MyRef.child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserP response = dataSnapshot.getValue(UserP.class);

                if (mCodeNum.getText().toString().equals(response.getUrlShare())) {

                    AppMain.getInstance().getSharedPref().writeBoolean(SharedPref.APP_IS_LOGIN, true);

                    Intent intent = new Intent(LoginCGActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(LoginCGActivity.this, "الرابط خطأ", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginCGActivity.this, "Server Error !", Toast.LENGTH_SHORT).show();
                Log.e("22222", "22222222" + databaseError.toException());
            }
        });
    }

}
