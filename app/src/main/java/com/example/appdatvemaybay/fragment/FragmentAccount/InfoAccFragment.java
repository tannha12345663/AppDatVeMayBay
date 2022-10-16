package com.example.appdatvemaybay.fragment.FragmentAccount;

import static com.example.appdatvemaybay.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdatvemaybay.MainActivity;
import com.example.appdatvemaybay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoAccFragment extends Fragment {
    View mView;
    //MailActivity
    MainActivity mainActivity;
    // Các thành phần trong layout
    CircleImageView img_avatar;
    EditText edtFullname, edtEmail, edtPhone;
    Button btnUpdateProfile;
    //firebase
    FirebaseUser user;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    //khai báo URI
    Uri mUri;
    //Khai báo màn hình thông báo người dùng
    ProgressDialog progressDialog;
    //Mở Lấy dữ liệu ảnh cho phép back khum bị lỗi (Đã fix Decorated)
    public  ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            mUri = data.getData();
                            img_avatar.setImageURI(mUri);

                            final StorageReference reference = storage.getReference().child("profile_picture")
                                    .child(FirebaseAuth.getInstance().getUid());
                            reference.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_info_acc, container, false);


        initUI();
        mainActivity = (MainActivity) getActivity();
        //Thông báo phản hồi ứng dụng Progress Diaglog
        progressDialog = new ProgressDialog(getActivity());
        //Load dữ liệu người dùng
        initListenr();
        setUserInformation();

        return mView;
    }


    private void setUserInformation() {
        //get data firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        edtFullname.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhoneNumber());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.ic_baseline_account_circle_24).into(img_avatar);


    }


    private void initUI() {
        img_avatar = mView.findViewById(R.id.img_avatar_profile);
        edtFullname = mView.findViewById(R.id.edit_full_name);
        edtEmail = mView.findViewById(R.id.edit_email);
        edtPhone = mView.findViewById(R.id.edit_phone);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfile();
            }
        });
    }


    private void initListenr() {
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                mActivityResultLauncher.launch(intent);

            }
        });
    }

    private void onClickUpdateProfile() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        progressDialog.setMessage("Xin vui lòng đợi giây lát");
        progressDialog.show();
        String strFullname = edtFullname.getText().toString().trim();
        String strPhone = edtPhone.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullname)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            mainActivity.showUserInformation();
                        }
                    }
                });
        };
}

