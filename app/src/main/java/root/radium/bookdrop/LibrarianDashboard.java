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

public class LibrarianDashboard extends AppCompatActivity {
    static ImageView LsetImg;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    TextView Lsetname, Lsetid, LsetDepartment, LsetPhoneNo;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference = firebaseFirestore.collection("User").document(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_dashboard);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        LsetImg = findViewById(R.id.Lsetimg);
        Lsetname = findViewById(R.id.Lsetname);
        Lsetid = findViewById(R.id.Lsetid);
        LsetDepartment = findViewById(R.id.Lsetdepartmet);
        LsetPhoneNo = findViewById(R.id.LsetMobilename);

        documentReference.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users s = documentSnapshot.toObject(Users.class);

                Lsetname.setText("Name : " + s.getName());
                LsetPhoneNo.setText("Mobile no : " + s.getMobileNo());
                LsetDepartment.setText("Semester : " + s.getDepartment().toUpperCase());
                Lsetid.setText("ID : " + s.getId());
                String img = s.getImg();
                ShowImg(img);

            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });


        findViewById(R.id.LSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LibrarianDashboard.this , LoginActivity.class));
                finish();
            }
        });

    }
    private void ShowImg(String img) {
        Picasso.with(this).load(img).into(LsetImg);
    }
}