package root.radium.bookdrop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


    //initialize the view
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    //change the view according to the needs
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        /* Point the elements of the slide layout */
        ImageView imageView = view.findViewById(R.id.slider_img);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView dics = view.findViewById(R.id.slider_dis);

        //changing the pointed element by that array
        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        dics.setText(dis[position]);

        container.addView(view);

        return view;
    }

    //destroy the view
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}

