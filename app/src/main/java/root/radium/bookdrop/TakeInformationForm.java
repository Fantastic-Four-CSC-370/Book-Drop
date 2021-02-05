package root.radium.bookdrop;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        setContentView(R.layout.activity_take_information_form);

        mSselectedPic = findViewById(R.id.selectedPic);
        mSName = findViewById(R.id.SName);
        mSid = findViewById(R.id.stuId);
        msPhoneNo = findViewById(R.id.stuPhone);
        mSDepartment = findViewById(R.id.stuDepart);

        databaseReference = FirebaseDatabase.getInstance().getReference("student");
        storageReference = FirebaseStorage.getInstance().getReference("student's pic");

        findViewById(R.id.SelectPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileToSelectImg();
            }

        });
        findViewById(R.id.sSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    saveUserData();
                } else {

                }
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
//            encode start here
//            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//            imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            Glide.with(this).load(decodedImage).into(mSselectedPic);

            student s = new student(name, phoneNo, id, department, imageBytes.toString().trim());
            databaseReference.child(uid).setValue(s);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public String getExtension(Uri imageUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap m = MimeTypeMap.getSingleton();
        return m.getExtensionFromMimeType(c.getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Glide.with(this).load(imageUri).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mSselectedPic);
        }

    }


}