 package root.radium.bookdrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import root.radium.bookdrop.Adapters.BookAdapter;
import root.radium.bookdrop.Adapters.BorrowBooksAdapter;
import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.BorrowDetails;

public class BorrowRequestPage extends AppCompatActivity {
    RecyclerView recyclerView;
    List<BorrowDetails> borrowDetails;
    public BorrowBooksAdapter  borrowBooksAdapter ;
    String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request_page);
        getSupportActionBar().hide();
        uid = EasySharedPref.read("TestSp","");

        recyclerView = findViewById(R.id.BorrowBookRec);
        borrowDetails = new ArrayList<>();
        // Write a message to the database
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference BorrowStatus = DB.getReference("Borrow Status").child(uid);
        
        BorrowStatus.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                BorrowDetails BorDet = snapshot.getValue(BorrowDetails.class);
                Log.e("TAG", BorDet.getGBookID() );
                borrowDetails.add(BorDet);

                borrowBooksAdapter = new BorrowBooksAdapter(BorrowRequestPage.this,borrowDetails);
                recyclerView.setLayoutManager(new LinearLayoutManager(BorrowRequestPage.this));
                recyclerView.setAdapter(borrowBooksAdapter);

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });







//                addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                int i = 0;
//                for(DataSnapshot d : dataSnapshot.getChildren()) {
//
//
//                    BorrowDetails BorDet = dataSnapshot.getValue(BorrowDetails.class);
//
//                    borrowDetails.add(BorDet);
//                    Log.e("Test" , BorDet.getGBookID());
//
//
//
//                   Log.e("Test" , String.valueOf(d.getKey()));
//
//                    i++;}
//                borrowBooksAdapter = new BorrowBooksAdapter(BorrowRequestPage.this,borrowDetails);
//                recyclerView.setLayoutManager(new LinearLayoutManager(BorrowRequestPage.this));
//                recyclerView.setAdapter(borrowBooksAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//              //  Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });



    }

    @Override
    protected void onStart() {
        super.onStart();
      // borrowDetails.clear();
    }
}