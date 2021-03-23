package com.alzheimerapp.ui.auth.register.P;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.auth.UserP;
import com.alzheimerapp.ui.main.HomePage.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPActivity extends AppCompatActivity {

    private ImageView mViewPhoto;
    private TextView mAddPhoto;
    private EditText mUsername;
    private EditText mLocation , url;
    private EditText mUsernameOrNum;
    private TextView mShowSuccess;
    private ImageView copy_url;
    private LayoutInflater inflater;
    Dialog dailogSuccess , dailogCopy;
    View dialogViewSuccess , dialogViewCopy;
    private FirebaseAuth mAuth;
    DatabaseReference FireDR;
    TextView show_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_p);
        mAuth = FirebaseAuth.getInstance();
        FireDR = FirebaseDatabase.getInstance().getReference();
        initView();
    }

    private void initView() {
        mViewPhoto = findViewById(R.id.view_photo);
        mAddPhoto = findViewById(R.id.add_photo);
        mUsername = findViewById(R.id.username);
        mLocation = findViewById(R.id.location);
        mUsernameOrNum = findViewById(R.id.username_or_num);
        mShowSuccess = findViewById(R.id.show_success);

        mShowSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsername.getText().toString().equals("")|| mLocation.getText().toString().equals("")|| mUsernameOrNum.getText().toString().equals("")) {
                    Toast.makeText(RegisterPActivity.this, "جميع الحقول مطلوبة !", Toast.LENGTH_SHORT).show();
                } else {

                    signupP("",mUsername.getText().toString(),mLocation.getText().toString(),mUsernameOrNum.getText().toString());

                }
            }
        });

    }

    private void Success() {

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(this);

        dailogSuccess = new Dialog(this);
        dailogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogSuccess.setCancelable(true);
        dialogViewSuccess = inflater.inflate(R.layout.dailog_success, null);
        dailogSuccess.setContentView(dialogViewSuccess);
        dailogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dailogSuccess.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dailogSuccess.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dailogSuccess.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dailogSuccess.dismiss();
                Copy();
            }
        }, 2500);

    }

    private void Copy() {

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(this);

        dailogCopy = new Dialog(this);
        dailogCopy.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogCopy.setCancelable(true);
        dialogViewCopy = inflater.inflate(R.layout.dailog_copy, null);
        dailogCopy.setContentView(dialogViewCopy);
        dailogCopy.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dailogCopy.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dailogCopy.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dailogCopy.show();

        copy_url = dialogViewCopy.findViewById(R.id.share);
        url = dialogViewCopy.findViewById(R.id.url);
        show_main = dialogViewCopy.findViewById(R.id.show_main);

        url.setText("https://alzheimerapp-ebd11.firebaseio.com/"+AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID));

        copy_url.setOnClickListener(new View.OnClickListener() {
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

        show_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                AppMain.getInstance().getSharedPref().writeBoolean(SharedPref.APP_IS_LOGIN,true);

            }
        });

    }

    private void signupP(final String image , final String username , final String location , final String phone) {

        UserP user = new UserP(image, username, location, phone,"https://alzheimerapp-ebd11.firebaseio.com/"+AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID));

        FireDR.child("usersP").child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).setValue(user);

        Success();

    }

}
