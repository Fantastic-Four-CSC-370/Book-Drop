package root.radium.bookdrop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding extends AppCompatActivity {

    //variables declare
    ViewPager mViwePager;
    LinearLayout mDots;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        //assigning the view element in a variables
        mViwePager = findViewById(R.id.slider_part);
        mDots = findViewById(R.id.dots);


        //calling adapter
        sliderAdapter = new SliderAdapter(this);

        mViwePager.setAdapter(sliderAdapter);
        addDots(0);
        mViwePager.addOnPageChangeListener(changeListener);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoarding.this, LoginActivity.class));
            }
        });

    }

    private void addDots(int position){

        dots = new TextView[3];
        mDots.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            mDots.addView(dots[i]);
        }

        if (dots.length>0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                dots[position].setTextColor(getColor(R.color.primary));
            }else{
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
            addDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}