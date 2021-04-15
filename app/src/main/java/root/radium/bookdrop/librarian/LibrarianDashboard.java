package root.radium.bookdrop.librarian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import root.radium.bookdrop.Adapters.BorrowBooksAdapter;
import root.radium.bookdrop.Adapters.UserListAdapter;
import root.radium.bookdrop.BorrowRequestPage;
import root.radium.bookdrop.LoginActivity;
import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.BorrowDetails;
import root.radium.bookdrop.SupportingClass.Users;

public class LibrarianDashboard extends AppCompatActivity {
    static ImageView LsetImg;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    TextView Lsetname, Lsetid, LsetDepartment, LsetPhoneNo;
    Button LibSeeAll ;
    private List<Users> userDataList = new ArrayList<>();
    private UserListAdapter UlistAdapter ;


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference = firebaseFirestore.collection("User").document(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_dashboard);
        getSupportActionBar().hide();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        LsetImg = findViewById(R.id.Lsetimg);
        Lsetname = findViewById(R.id.Lsetname);
        Lsetid = findViewById(R.id.Lsetid);
        LsetDepartment = findViewById(R.id.Lsetdepartmet);
        LsetPhoneNo = findViewById(R.id.LsetMobilename);
        LibSeeAll = findViewById(R.id.LseeAllRequest);

        UlistAdapter = new UserListAdapter(LibrarianDashboard.this, userDataList );

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
                Picasso.get().load(img).into(LsetImg);

            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });

        LibSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeAllRequest();
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

    private void seeAllRequest() {
        List<String> list = new ArrayList<String>();
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference BorrowStatus = DB.getReference("Borrow Status");
        BorrowStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("Children count", String.valueOf(snapshot.getChildrenCount()));
                for(DataSnapshot data : snapshot.getChildren()){
                    Log.d( "onDataChange: " , data.getKey());
                    list.add(data.getKey());
                }
                userDataList.clear();
                for(String user:list){
                    Log.d("fruits: ", user );
                    DocumentReference alludocumentReference = firebaseFirestore.collection("User").document(user);
                    alludocumentReference.get().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Users s = documentSnapshot.toObject(Users.class);
                            Log.d( "onSuccess: ", s.getName());
                            userDataList.add(s);

                        }
                    }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LibrarianDashboard.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}