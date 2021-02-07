package root.radium.bookdrop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupForm extends AppCompatActivity {

    //Variables
    TextView mLogin;
    Button mregbtn;
    EditText mregEmail, mregPass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        auth = FirebaseAuth.getInstance();
        mregEmail = findViewById(R.id.regEmail);
        mregPass = findViewById(R.id.regPass);
        mregbtn = findViewById(R.id.regbtn);
        mLogin = findViewById(R.id.loginhere);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupForm.this, LoginActivity.class));
                finish();
            }
        });
        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    protected void signup() {
        //TODO
        String RegEmail = mregEmail.getText().toString().trim();
        String Regpass = mregPass.getText().toString().trim();
        if (TextUtils.isEmpty(RegEmail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Regpass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Regpass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(RegEmail, Regpass)
                .addOnCompleteListener(signupForm.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(signupForm.this, "createUserWithEmail:onComplete:" +
                                task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(signupForm.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

//TODO

                            startActivity(new Intent(signupForm.this, TakeInformationForm.class));
                            finish();
                        }
                    }
                });

        Toast.makeText(getApplicationContext(), " Successfully Register", Toast.LENGTH_SHORT).show();
    }
}