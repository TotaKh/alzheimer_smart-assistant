package com.alzheimerapp.ui.auth.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.ui.auth.loginCG.LoginCGActivity;
import com.alzheimerapp.ui.auth.register.CG.RegisterActivity;
import com.alzheimerapp.ui.main.HomePage.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameOrNum;
    private EditText mPassword;
    private TextView mForget;
    private TextView mLogin;
    private TextView mRegister;
    private LayoutInflater inflater;
    Dialog dailogOptinal;
    View dialogView;
    TextView yes;
    TextView no;
    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference MyRef;
    private TextView mLoginCg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        MyRef = database.child("users");
        initView();
    }

    private void initView() {
        mUsernameOrNum = findViewById(R.id.username_or_num);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);
        mLoginCg = findViewById(R.id.login_cg);

        mLoginCg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsernameOrNum.getText().toString().trim().equals("") || mPassword.getText().toString().trim().equals("")) {

                    Toast.makeText(LoginActivity.this, "جميع الحقول مطلوبة ! ", Toast.LENGTH_SHORT).show();

                } else {
                    signInCG(mUsernameOrNum.getText().toString().trim(), mPassword.getText().toString().trim());
                }

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Optinal();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUsernameOrNum.getText().toString().trim().equals("") || mPassword.getText().toString().trim().equals("")) {

                    Toast.makeText(LoginActivity.this, "جميع الحقول مطلوبة ! ", Toast.LENGTH_SHORT).show();

                } else {
                    signIn(mUsernameOrNum.getText().toString().trim(), mPassword.getText().toString().trim());
                }

            }
        });


    }

    private void Optinal() {

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(this);

        dailogOptinal = new Dialog(this);
        dailogOptinal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogOptinal.setCancelable(true);
        dialogView = inflater.inflate(R.layout.dailog_optinal_auth, null);
        dailogOptinal.setContentView(dialogView);
        dailogOptinal.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dailogOptinal.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dailogOptinal.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView yes = dialogView.findViewById(R.id.yes);
        TextView no = dialogView.findViewById(R.id.no);

        dailogOptinal.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                dailogOptinal.dismiss();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailogOptinal.dismiss();
                Toast.makeText(LoginActivity.this, "عذرا لا يمكنك تسجيل الدخول، يجب على مفدم الرعاية دعوتك", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signIn(String email, String password) {

        Toast.makeText(this, "جاري التحميل ...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    AppMain.getInstance().getSharedPref().writeBoolean(SharedPref.APP_IS_LOGIN, true);

                    AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_TYPE, "user");

                    AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_UID, task.getResult().getUser().getUid());

                    Log.e("FireToken", task.getResult().getUser().getUid());

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInCG(String email, String password) {

        Toast.makeText(this, "جاري التحميل ...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, LoginCGActivity.class);
                    startActivity(intent);
                    finish();

                    AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_TYPE, "userP");

                    AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_UID, task.getResult().getUser().getUid());

                    Log.e("FireToken", task.getResult().getUser().getUid());

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
