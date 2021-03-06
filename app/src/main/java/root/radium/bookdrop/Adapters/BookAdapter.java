

package root.radium.bookdrop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import root.radium.bookdrop.BookDetail;
import root.radium.bookdrop.BorrowRequestPage;
import root.radium.bookdrop.R;
import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.BorrowDetails;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    List<Book> book ;
    private Context mContext;

    public BookAdapter(Context context, List<Book> book) {

        this.book = book;
        this.mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.singlebook , parent , false);

        final ViewHolder viewHolder =  new ViewHolder(view);
        viewHolder.containe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                **Send Position Wise Data In Other Activity with Object**
                Intent i = new Intent(mContext , BookDetail.class);
                int pos = viewHolder.getAdapterPosition();
                Book b = book.get(pos);
                i.putExtra("BookDetail" ,b);
                mContext.startActivity(i);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BookTitle.setText(book.get(position).getTitle());
       // holder.BookSubtitle.setText(book.get(position).getBookSubTitle());
        holder.BookAuthor.setText(book.get(position).getAuthors());
        holder.BookPages.setText(book.get(position).getPageCount());
        holder.BookPublishDate.setText(book.get(position).getPublishedDate());
       // holder.BookISBN.setText(book.get(position).getISBN());
        Picasso.get().load(book.get(position).getThumbnail()).into(holder.BookThumbnail);

//        Log.e("thumbnail",  book.get(position).getPublishedDate() );
//        Log.e("thumbnail",  book.size() +"" );

    }
    @Override
    public int getItemCount() {
        return book.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView BookThumbnail ;
        TextView BookTitle , BookAuthor ,BookSubtitle ,BookPages ,BookPublishDate ,BookISBN;
        RelativeLayout containe;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            BookTitle = itemView.findViewById(R.id.Book_title);
            BookAuthor = itemView.findViewById(R.id.Book_Author);
            // BookSubtitle= itemView.findViewById(R.id.Book_Subtitle);
            BookPages= itemView.findViewById(R.id.Book_pages);
            BookPublishDate= itemView.findViewById(R.id.Book_PublishDate);
            BookThumbnail = itemView.findViewById(R.id.Book_Thumbnail);
            //BookISBN = itemView.findViewById(R.id.Book_ISBN);
            containe = itemView.findViewById(R.id.container);

        }
    }
}
