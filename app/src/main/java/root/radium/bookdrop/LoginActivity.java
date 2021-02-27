package root.radium.bookdrop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import root.radium.bookdrop.SupportingClass.Users;

public class LoginActivity extends AppCompatActivity {
    final String TAG = "loginForm";
    Button lbtn;
    EditText mid, mpass;
    TextView mcreatenew;
    private FirebaseAuth auth;
    private RadioGroup degSelectorGroup;
    private RadioButton degSelectorBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //Firebase Auth;
        auth = FirebaseAuth.getInstance();
        lbtn = findViewById(R.id.lbtn);
        mid = findViewById(R.id.sid);
        mpass = findViewById(R.id.spass);
        mcreatenew = findViewById(R.id.createnew);



        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String LoginEmail = mid.getText().toString().trim();
                String LoginPass = mpass.getText().toString().trim();

                if (TextUtils.isEmpty(LoginEmail)) {
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(LoginPass)) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (LoginPass.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Too short Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(LoginEmail, LoginPass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();

                                    DriveUser(uid);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Email id or Password",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed!! \n Try again",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mcreatenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, signupForm.class));
                finish();
            }
        });
    }

    private void DriveUser(String uid){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection(
                "User").document(uid);

        documentReference.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Users s = documentSnapshot.toObject(Users.class);

                Toast.makeText(LoginActivity.this, s.getRole(), Toast.LENGTH_SHORT).show();
                switch(s.getRole()){

                    case "TEACHER":
                        startActivity(new Intent(LoginActivity.this, TeacherDashboard.class));

                        break;
                    case "STUDENT":
                        startActivity(new Intent(LoginActivity.this, StudentDashboard.class));

                        break;
                    case "LIBRARIAN":
                        startActivity(new Intent(LoginActivity.this, LibrarianDashboard.class));

                        break;
                    default:

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });
    }

}