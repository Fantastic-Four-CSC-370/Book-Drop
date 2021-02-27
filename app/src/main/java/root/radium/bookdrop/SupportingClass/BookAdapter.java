

package root.radium.bookdrop.SupportingClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.util.List;

import root.radium.bookdrop.BookDetail;
import root.radium.bookdrop.R;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Book> book ;
    private Context mContext;

    public BookAdapter(Context context, List<Book> book) {
      //  this.layoutInflater = LayoutInflater.from(context);
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
                Intent i = new Intent(mContext , BookDetail.class);
                int pos = viewHolder.getAdapterPosition();
                i.putExtra("book_title" ,book.get(pos).getTitle());
                i.putExtra("book_author" ,book.get(pos).getAuthors());
                i.putExtra("book_desc" ,book.get(pos).getBookDescription());
                i.putExtra("book_subTitle" ,book.get(pos).getBookSubTitle());
                i.putExtra("book_publish_date" ,book.get(pos).getPublishedDate());
                i.putExtra("book_preview" ,book.get(pos).getPreview());
                i.putExtra("book_thumbnail" ,book.get(pos).getThumbnail());
                i.putExtra("book_ISBN" ,book.get(pos).getISBN());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BookTitle.setText(book.get(position).getTitle());
      //  holder.BookSubtitle.setText(book.get(position).getBookSubTitle());
        holder.BookAuthor.setText(book.get(position).getAuthors());
        holder.BookPages.setText(book.get(position).getPageCount());
        holder.BookPublishDate.setText(book.get(position).getPublishedDate());
       // holder.BookISBN.setText(book.get(position).getISBN());
        Picasso.get().load(book.get(position).getThumbnail()).into(holder.BookThumbnail);

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
         //   BookISBN = itemView.findViewById(R.id.Book_ISBN);
            containe = itemView.findViewById(R.id.container);

        }
    }
}
