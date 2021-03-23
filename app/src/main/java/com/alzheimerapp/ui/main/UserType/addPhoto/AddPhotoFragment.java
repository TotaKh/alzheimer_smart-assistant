package com.alzheimerapp.ui.main.UserType.addPhoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.alzheimerapp.R;
import com.alzheimerapp.data.AppMain;
import com.alzheimerapp.data.SharedPref;
import com.alzheimerapp.faceRec.Activities.MainActivity;
import com.alzheimerapp.pojo.gallery.GalleryImg;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddPhotoFragment extends Fragment {

    private AddPhotoViewModel mViewModel;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView add_photo, upload , upload_fast;
    private ImageView view_photo;
    private Spinner location;
    private EditText username;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private View view;

    private FirebaseAuth mAuth;
    DatabaseReference FireDR;

    ArrayAdapter<String> spinnerArrayAdapter;
    private ArrayList<String> spinnerArray = new ArrayList<>();

    public static AddPhotoFragment newInstance() {
        return new AddPhotoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_photo_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPhotoViewModel.class);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();
        FireDR = FirebaseDatabase.getInstance().getReference();

        spinnerArray.add("ابن");
        spinnerArray.add("ابنة");
        spinnerArray.add("اب");
        spinnerArray.add("ام");

        add_photo = view.findViewById(R.id.add_photo);
        upload = view.findViewById(R.id.upload);
        view_photo = view.findViewById(R.id.view_photo);
        username = view.findViewById(R.id.username);
        location = view.findViewById(R.id.location);
        upload_fast = view.findViewById(R.id.upload_fast);

        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(spinnerArrayAdapter);

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        upload_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                view_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null || !username.getText().toString().equals("")) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("جاري رفع الصورة ...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("albums/" + AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID) +"/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "تم عملية رفع الصورة بنجاح", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    sendImagesGallary(uri.toString(),
                                            username.getText().toString(),location.getSelectedItem().toString());
                                }
                            });




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "حدثت مشكلة اثناء الرفع", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage(" جاري الرفع " + (int) progress + "%");
                        }
                    });
        } else {

            Toast.makeText(getActivity(), "الرجاء اضافة الصورة و اكمال باقي الحقول !", Toast.LENGTH_SHORT).show();

        }
    }

    private void sendImagesGallary(final String urlImg , final String name , final String relation) {

        GalleryImg galleryImg = new GalleryImg(urlImg,name,relation);

        FireDR.child("album").child(AppMain.getInstance().getSharedPref().readString(SharedPref.APP_UID)).push().setValue(galleryImg);


    }

}
