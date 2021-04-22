package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import code.fortomorrow.easysharedpref.EasySharedPref;

public class singleBorroRequest extends AppCompatActivity {

    TextView Dkey;
    String dkey ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_borro_request);
        getSupportActionBar().hide();

        Intent i =getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            dkey = extras.getString("DocumentKey");
        }
        else {
          //  uid = EasySharedPref.read("TestSp","");
        }


        Dkey = findViewById(R.id.documentId);

        Dkey.setText(dkey);
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference BorrowStatus = DB.getReference("Borrow Status").child(dkey);

        findViewById(R.id.deleteBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowStatus.removeValue();
            }
        });

    }
}