package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    }

//    private void addDots(){
//
//        dots = new TextView[3];
//        for(int i=0;i<dots.length;i++){
//
//        }
//    }
}