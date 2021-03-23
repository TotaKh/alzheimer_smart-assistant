package com.alzheimerapp.ui.auth.register.CG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.pojo.auth.User;
import com.alzheimerapp.ui.auth.register.P.RegisterPActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ImageView mViewPhoto;
    private TextView mAddPhoto;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mRepassword;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mLocation;
    private Spinner mRelation;
    private TextView mNextReg;
    private FirebaseAuth mAuth;
    DatabaseReference FireDR;
    ArrayAdapter<String> spinnerArrayAdapter;
    private ArrayList<String> spinnerArray = new ArrayList<>();
    public static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        FireDR = FirebaseDatabase.getInstance().getReference();

        spinnerArray.add("ابن");
        spinnerArray.add("ابنة");
        spinnerArray.add("اب");
        spinnerArray.add("ام");

        initView();
    }

    private void initView() {
        mViewPhoto = findViewById(R.id.view_photo);
        mAddPhoto = findViewById(R.id.add_photo);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mRepassword = findViewById(R.id.repassword);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        mLocation = findViewById(R.id.location);
        mRelation = findViewById(R.id.relation);
        mNextReg = findViewById(R.id.next_reg);

        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mRelation.setAdapter(spinnerArrayAdapter);

        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        mNextReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUsername.getText().toString().trim().equals("") ||
                        mEmail.getText().toString().trim().equals("") ||
                        mLocation.getText().toString().trim().equals("") ||
                        mPhone.getText().toString().trim().equals("") ||
                        mPassword.getText().toString().trim().equals("") ||
                        mRepassword.getText().toString().trim().equals("") ||
                        !mRepassword.getText().toString().trim().equals(mPassword.getText().toString().trim())) {

                    Toast.makeText(RegisterActivity.this, "جميع الحقول مطلوبة ! ", Toast.LENGTH_SHORT).show();

                } else {

                    signUp("", mUsername.getText().toString().trim(),
                            mPassword.getText().toString().trim(), mPhone.getText().toString().trim(),
                            mEmail.getText().toString().trim(), mLocation.getText().toString(),mRelation.getSelectedItem().toString());
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Bitmap bitmap = BitmapFactory.decodeFile(data.getData().getEncodedPath());
            mViewPhoto.setImageBitmap(bitmap);
        }
    }

    private void signUp(final String image, final String username, final String password, final String phone, String email, final String location, final String relation_type) {

        Toast.makeText(this, "جاري التحميل ...", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                final FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();

                if (userFirebase != null) {

                    User user = new User(userFirebase.getUid(), image, username, password, phone, userFirebase.getEmail(), location, relation_type);

                    FireDR.child("users").child(userFirebase.getUid()).setValue(user);

                    AppMain.getInstance().getSharedPref().writeString(SharedPref.APP_UID,userFirebase.getUid());

                }

                Toast.makeText(RegisterActivity.this, "تم تسجيل المستخدم بنجاح ^_^", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, RegisterPActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
