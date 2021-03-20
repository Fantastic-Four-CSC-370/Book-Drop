package root.radium.bookdrop.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.BorrowDetails;


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
//        Log.e("thumbnail",  borrowDetails.get(position).getBorrowBookThumbnail() );
//        Log.e("thumbnail", borrowDetails.size()+"" );

    }

    @Override
    public int getItemCount() {
        return borrowDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView borrowBookThumbnail ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrowBookThumbnail= itemView.findViewById(R.id.borrowReqbook);
        }
    }
}
