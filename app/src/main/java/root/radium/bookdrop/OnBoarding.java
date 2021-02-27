package root.radium.bookdrop;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding extends AppCompatActivity {

    //variables declare
    ViewPager mViwePager;
    LinearLayout mDots;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button mStart;
    Animation animation;
    int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //used for hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);
        getSupportActionBar().hide();

        //assigning the view element in a variables
        mViwePager = findViewById(R.id.slider_part);
        mDots = findViewById(R.id.dots);
        mStart = findViewById(R.id.start_btn);

        //calling adapter
        sliderAdapter = new SliderAdapter(this);
        mViwePager.setAdapter(sliderAdapter);

        //Dots
        addDots(0);
        mViwePager.addOnPageChangeListener(changeListener);

    }
    //work of skip button
    public void skip(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    //work of next button
//    public void next(View view) {
//        mViwePager.setCurrentItem(currentPosition + 1);
//    }

    //work of get started button
    public void getStarted(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    //adding dot to indicate the page
    private void addDots(int position) {

        //assigning the dost
        dots = new TextView[3];
        mDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            mDots.addView(dots[i]);
        }

        //changing the current page indicator dot color both for below sdk26 and up
        if (dots.length > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[position].setTextColor(getColor(R.color.primary));
            } else {
                dots[position].setTextColor(getResources().getColor(R.color.primary));
            }
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //current position of dots
            addDots(position);
            currentPosition = position;

            //visiblity of the get started button
            if (position == 0) {
                mStart.setVisibility(View.INVISIBLE);
                findViewById(R.id.skip_btn).setVisibility(View.VISIBLE);

            } else if (position == 1) {
                mStart.setVisibility(View.INVISIBLE);
                findViewById(R.id.skip_btn).setVisibility(View.VISIBLE);

            } else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.btn_anim);
                mStart.setAnimation(animation);
                mStart.setVisibility(View.VISIBLE);
                findViewById(R.id.skip_btn).setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}