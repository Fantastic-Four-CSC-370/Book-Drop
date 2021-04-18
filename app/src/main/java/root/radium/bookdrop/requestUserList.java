package root.radium.bookdrop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import root.radium.bookdrop.Adapters.UserListAdapter;
import root.radium.bookdrop.SupportingClass.Users;
import root.radium.bookdrop.librarian.LibrarianDashboard;

public class requestUserList extends AppCompatActivity {

    private List<Users> userDataList = new ArrayList<>();
    DocumentReference alludocumentReference;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private UserListAdapter UlistAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_user_list);
        getSupportActionBar().hide();

        List<String> list = new ArrayList<String>();
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference BorrowStatus = DB.getReference("Borrow Status");

        UlistAdapter = new UserListAdapter(requestUserList.this, userDataList );
        ListView reqlist = findViewById(R.id.showRequest);


        BorrowStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("Children count", String.valueOf(snapshot.getChildrenCount()));
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Log.d( "onDataChange: " , data.getKey());
                    list.add(data.getKey());
                }
                userDataList.clear();
                for(String user:list){
                    Log.e("fruits: ", user );
                   alludocumentReference = firebaseFirestore.collection("User").document(user);
                    alludocumentReference.get().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Users s = documentSnapshot.toObject(Users.class);
                            Log.e( "onSuccess: ", s.getName());
                            s.setUID(user);
                            userDataList.add(s);
                        }

                    }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            reqlist.setAdapter(UlistAdapter);

                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requestUserList.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        reqlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(requestUserList.this, userDataList.get(position).getUID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requestUserList.this , BorrowRequestPage.class);
                intent.putExtra("UID" , userDataList.get(position).getUID() );
                startActivity(intent);
            }
        });


    }
}