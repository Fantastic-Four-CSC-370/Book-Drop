package root.radium.bookdrop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    //Variable
    //Splash Screen timer
    private static int splashTime = 800;

    //UI
    ImageView mBookImg, mLogoIcon, mDropImg;
    TextView mGroupName;

    //Animation
    Animation Anim;

    SharedPreferences onBordingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        //Decliner UI
        mBookImg = findViewById(R.id.book_img);
        mDropImg = findViewById(R.id.drop_img);
        mLogoIcon = findViewById(R.id.logo_icon);
        mGroupName = findViewById(R.id.group_name);

        //Decliner Animation
        Anim = AnimationUtils.loadAnimation(this,R.anim.intro_anim);

        //Set Animation to Element
        mBookImg.setAnimation(Anim);
        mDropImg.setAnimation(Anim);
        mLogoIcon.setAnimation(Anim);
        mGroupName.setAnimation(Anim);

        //Splash timer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBordingScreen = getSharedPreferences("onBordingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBordingScreen.getBoolean("firstTime", true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = onBordingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent = new Intent(SplashActivity.this, OnBoarding.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },splashTime);
    }
}
