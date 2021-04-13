package root.radium.bookdrop.Adapters;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.BorrowDetails;

import static java.lang.String.format;


public class BorrowBooksAdapter extends RecyclerView.Adapter<BorrowBooksAdapter.ViewHolder> {
    List<BorrowDetails> borrowDetails; ;
    private Context mContext;

    public BorrowBooksAdapter(  Context mContext ,List<BorrowDetails> borrowDetails) {
        this.borrowDetails = borrowDetails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.borrowsinglebook , parent , false);
        final BorrowBooksAdapter.ViewHolder viewHolder =  new BorrowBooksAdapter.ViewHolder(view);

        //TODO
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(borrowDetails.get(position).getBorrowBookThumbnail()).into(holder.borrowBookThumbnail);
        holder.borrowReqtime.setText(setLocalTime(borrowDetails.get(position).getTimeStamp()));
        holder.returnTime.setText(returnTime(borrowDetails.get(position).getReturnTime()));
    }

    private String setLocalTime(Long timeStamp) {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timeStamp*1000);
        String date = DateFormat.format("dd:MM:yyyy", cal).toString();
        return  date;
    }
    private String returnTime(Long timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp*1000);
        String date = DateFormat.format("dd:MM:yyyy", cal).toString();
        return  date;
    }

    @Override
    public int getItemCount() {
        return borrowDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView borrowBookThumbnail ;
        TextView  borrowReqtime , returnTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrowBookThumbnail= itemView.findViewById(R.id.borrowReqbook);
            borrowReqtime = itemView.findViewById(R.id.ReqTime);
            returnTime = itemView.findViewById(R.id.retTime);
        }
    }
}
