package root.radium.bookdrop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        //Firebase Auth;
        auth = FirebaseAuth.getInstance();

        lbtn = findViewById(R.id.lbtn);
        mid = findViewById(R.id.sid);
        mpass = findViewById(R.id.spass);

        mcreatenew = findViewById(R.id.createnew);

        degSelectorGroup = findViewById(R.id.degSelector);


        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String LoginEmail = mid.getText().toString().trim();
                String LoginPass = mpass.getText().toString().trim();

                if (degSelectorGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Are you Librarian or teacher or student",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
//                Radio group here
                    int selectedId = degSelectorGroup.getCheckedRadioButtonId();
                    degSelectorBtn = findViewById(selectedId);
                    Toast.makeText(LoginActivity.this, degSelectorBtn.getText(), Toast.LENGTH_SHORT).show();
//                Radio group here
                }

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
                                    startActivity(new Intent(LoginActivity.this, StudentDashboard.class));
                                    finish();
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
            }
        });


    }
}