package root.radium.bookdrop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.Users;

public class UserListAdapter extends ArrayAdapter<Users> {
    private Activity context;
    private List<Users> userDataList;

    public UserListAdapter(Activity context, List<Users> userDataList) {
        super(context, R.layout.single_user,userDataList);
        this.context = context;
        this.userDataList = userDataList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       LayoutInflater l = context.getLayoutInflater();

        View view = l.inflate(R.layout.single_user,null,true);
        Users user = userDataList.get(position);

        TextView ID = view.findViewById(R.id.suID);
        TextView Name = view.findViewById(R.id.suName);
        TextView role = view.findViewById(R.id.suRole);
        ImageView Picture = view.findViewById(R.id.suUserPic);




        Name.setText("Name :" + user.getName());
        ID.setText("ID " + user.getId());
        role.setText(user.getRole());
        Picasso.get().load(user.getImg()).into(Picture);

//        Glide.with(context).load(user.getImg()).into(Picture);
        Log.e("TAG", user.getName());

        return view;
    }
}
