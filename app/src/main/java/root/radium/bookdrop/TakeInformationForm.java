package root.radium.bookdrop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import root.radium.bookdrop.SupportingClass.student;

public class TakeInformationForm extends AppCompatActivity {
    private static final int IMAGE_REQUEST = 1;
    ImageView mSselectedPic;
    EditText mSName, mSid, msPhoneNo, mSDepartment;
    Uri imageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    //    firebase
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storagePermission();
        setContentView(R.layout.activity_take_information_form);

        mSselectedPic = findViewById(R.id.selectedPic);
        mSName = findViewById(R.id.SName);
        mSid = findViewById(R.id.stuId);
        msPhoneNo = findViewById(R.id.stuPhone);
        mSDepartment = findViewById(R.id.stuDepart);

        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        storageReference = FirebaseStorage.getInstance().getReference("Student's picture");

        findViewById(R.id.SelectPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileToSelectImg();
            }

        });
        findViewById(R.id.sSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveUserData();

            }
        });

    }

    public void openFileToSelectImg() {
        Intent intent = new Intent();
        intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public void saveUserData() {
        String name = mSName.getText().toString().trim();
        String id = mSid.getText().toString().trim();
        String phoneNo = msPhoneNo.getText().toString().trim();
        String department = mSDepartment.getText().toString().trim();
        StorageReference ref = storageReference.child(uid + ".s");

        try {

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            UploadTask uploadTask = ref.putBytes(imageBytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(! uriTask.isSuccessful());
                        Uri downloadUrl = uriTask.getResult();

                    student s = new student(name, phoneNo, id, department, downloadUrl.toString());
                    databaseReference.child(uid).setValue(s);
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {
                    startActivity(new Intent(TakeInformationForm.this, StudentDashboard.class));
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e,
                    Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Glide.with(this).load(imageUri).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mSselectedPic);
        }

    }

    private void storagePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission Grant", "Permission is granted");

            } else {

                Log.v("Permission denied", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission Grant", "Permission is granted");

        }
        ReadstoragePermission();
    }

    private void ReadstoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission Grant", "Permission is granted");

            } else {

                Log.v("Permission denied", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission Grant", "Permission is granted");

        }
    }


}