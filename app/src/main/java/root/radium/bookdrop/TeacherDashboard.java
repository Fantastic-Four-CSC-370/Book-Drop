package root.radium.bookdrop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import root.radium.bookdrop.SupportingClass.Users;

public class TeacherDashboard extends AppCompatActivity {

    static ImageView TsetImg;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    TextView Tsetname, Tsetid, TsetDepartment, TsetPhoneNo;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference = firebaseFirestore.collection("User").document(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        TsetImg = findViewById(R.id.Tsetimg);
        Tsetname = findViewById(R.id.Tsetname);
        Tsetid = findViewById(R.id.Tsetid);
        TsetDepartment = findViewById(R.id.Tsetdepartmet);
        TsetPhoneNo = findViewById(R.id.TsetMobilename);

        documentReference.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users s = documentSnapshot.toObject(Users.class);

                Tsetname.setText("Name : " + s.getName());
                TsetPhoneNo.setText("Mobile no : " + s.getMobileNo());
                TsetDepartment.setText("Semester : " + s.getDepartment().toUpperCase());
                Tsetid.setText("ID : " + s.getId());
                String img = s.getImg();
                ShowImg(img);

            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });


        findViewById(R.id.TSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TeacherDashboard.this , LoginActivity.class));
                finish();
            }
        });

    }
    private void ShowImg(String img) {
        Picasso.with(this).load(img).into(TsetImg);
    }
}