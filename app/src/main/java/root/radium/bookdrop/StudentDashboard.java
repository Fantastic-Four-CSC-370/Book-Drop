package root.radium.bookdrop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

import root.radium.bookdrop.SupportingClass.student;

public class StudentDashboard extends AppCompatActivity {

    static ImageView SsetImg;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    TextView Ssetname, Ssetid, SsetDepartment, SsetPhoneNo;
    Button signOut ;

    private void ShowImg(String img) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        SsetImg.setImageBitmap(decodedImage);
        Glide.with(this).load(decodedImage).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(SsetImg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        databaseReference = FirebaseDatabase.getInstance().getReference("student");
        SsetImg = findViewById(R.id.Ssetimg);
        Ssetname = findViewById(R.id.setname);
        Ssetid = findViewById(R.id.setid);
        SsetDepartment = findViewById(R.id.setdepartmet);
        SsetPhoneNo = findViewById(R.id.setMobilename);

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Ssetname , Ssetid ,SsetDepartment ,SsetPhoneNo;

                student user = dataSnapshot.getValue(student.class);
                Ssetname.setText("Name : " + user.getName());
                SsetPhoneNo.setText("Mobile no : " + user.getMobileNo());
                SsetDepartment.setText("Semester : " + user.getDepartment());
                Ssetid.setText("ID : " + user.getId());
                String img = user.getImg();

                ShowImg(img);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("StudentDashboard", "not working");
            }
        });

        findViewById(R.id.SignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentDashboard.this , LoginActivity.class));
                finish();
            }
        });


    }


}