package com.example.appdatvemaybay.fragment.FragmentAccount;

import static com.example.appdatvemaybay.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdatvemaybay.Account_User.GetUserFirebase;
import com.example.appdatvemaybay.Account_User.User;
import com.example.appdatvemaybay.MainActivity;
import com.example.appdatvemaybay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    GetUserFirebase getUserFirebase;
    //khai báo URI
    Uri mUri;
    Bitmap bitmap;
    //Khai báo màn hình thông báo người dùng
    ProgressDialog progressDialog;
    //Mở Lấy dữ liệu ảnh cho phép back khum bị lỗi
    public  ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            mUri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),mUri);
                                img_avatar.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

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
        edtEmail.setText(user.getEmail());;
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_baseline_account_circle_24).into(img_avatar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myData = database.getReference("ListUser");
        myData.child(user.getUid()).child("phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phones = snapshot.getValue(String.class);
                edtPhone.setText(phones);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myData.child(user.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String names = snapshot.getValue(String.class);
                edtFullname.setText(names);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initUI() {
        img_avatar = mView.findViewById(R.id.img_avatar_profile);
        edtFullname = mView.findViewById(R.id.editName);
        edtEmail = mView.findViewById(R.id.edit_email);
        edtPhone = mView.findViewById(R.id.edit_phone);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);
    }


    private void initListenr() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUID = user.getUid();
                String strEmail = user.getEmail();
                String strPhone = edtPhone.getText().toString().trim();
                String strName = edtFullname.getText().toString().trim();
                User user= new User(strUID,strEmail,strName,strPhone,"null");
                if (bitmap == null){
                    Toast.makeText(mainActivity, "Bạn chưa chọn ảnh mới để cập nhật", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    onClickUpdateProfile(user,strUID);
                }

            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }else if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            //Nếu như người dùng cho phép
            openGallery();
        }
        else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }


    private void onClickUpdateProfile(User user_local,String strUID) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog.setMessage("Xin vui lòng đợi giây lát");
        progressDialog.show();

        String strFullname = edtFullname.getText().toString().trim();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullname)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            mainActivity.showUserInformation();
                        }
                    }
                });
        //Đẩy thông tin người dùng lên ReadlTimeDB

        handleUpload(bitmap);
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(user.getUid() + ".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        getDownUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mainActivity, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Đang tải ảnh vui lòng đợi "+(int)progress+"%");
                    }
                });
    }

    private void getDownUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }
    private void setUserProfileUrl(Uri uri){
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        User user_local = new User(user.getUid(),user.getEmail(),user.getDisplayName(),edtPhone.getText().toString().trim(),user.getPhotoUrl().toString());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myData = database.getReference("ListUser");
                        //Định danh user
                        myData.child(user.getUid()).setValue(user_local, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(mainActivity, "Cập nhật USER thành công ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(mainActivity, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mainActivity, "Tải ảnh lên thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void openGallery() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                mActivityResultLauncher.launch(intent);
    }

}

