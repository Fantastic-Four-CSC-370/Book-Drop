package root.radium.bookdrop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //array of slider image
    int images[] = {
            R.drawable.welcome_screen1,
            R.drawable.welcome_screen2,
            R.drawable.welcome_screen3
    };

    //array of slider title text
    int headings[] ={
            R.string.first_slider_titel,
            R.string.second_slider_titel,
            R.string.third_slider_titel
    };

    //array of slider dis text
    int dis[]= {
      R.string.first_slider_dis,
      R.string.second_slider_dis,
      R.string.third_slider_dis
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        return super.instantiateItem(container, position);
    }
}

