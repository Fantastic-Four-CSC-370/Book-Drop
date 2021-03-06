package root.radium.bookdrop.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import root.radium.bookdrop.BookSearch;
import root.radium.bookdrop.BorrowRequestPage;
import root.radium.bookdrop.LoginActivity;
import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.Users;

public class StudentDashboard extends AppCompatActivity {


    static ImageView SsetImg;
    DatabaseReference databaseReference;

    TextView Ssetname, Ssetid, SsetDepartment, SsetPhoneNo ,setRole;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getSupportActionBar().hide();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("User").document(uid);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        SsetImg = findViewById(R.id.Ssetimg);
        Ssetname = findViewById(R.id.setname);
        Ssetid = findViewById(R.id.setid);
        SsetDepartment = findViewById(R.id.setdepartmet);
        SsetPhoneNo = findViewById(R.id.setMobilename);
        setRole = findViewById(R.id.setRole);



        documentReference.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users s = documentSnapshot.toObject(Users.class);

                Ssetname.setText("Name : " + s.getName());
                SsetPhoneNo.setText("Mobile no : " + s.getMobileNo());
                SsetDepartment.setText("Semester : " + s.getDepartment().toUpperCase());
                Ssetid.setText("ID : " + s.getId());
                String img = s.getImg();
                Picasso.get().load(img).into(SsetImg);
                setRole.setText("Role : " +s.getRole());


            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });



        findViewById(R.id.ShowBooks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboard.this , BookSearch.class));

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

        findViewById(R.id.ShowBorrowBooks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboard.this, BorrowRequestPage.class));

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}