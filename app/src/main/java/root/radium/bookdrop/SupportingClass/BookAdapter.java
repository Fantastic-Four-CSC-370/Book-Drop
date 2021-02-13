package root.radium.bookdrop.SupportingClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import root.radium.bookdrop.R;
import root.radium.bookdrop.recyclerview.FoodModel;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Book> book ;

    public BookAdapter(Context context, List<Book> book) {
        this.layoutInflater = LayoutInflater.from(context);
        this.book = book;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.singlebook, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BookTitle.setText(book.get(position).getTitle());
        holder.BookSubtitle.setText(book.get(position).getBookSubTitle());
        holder.BookAuthor.setText(book.get(position).getAuthors());
        holder.BookPages.setText(book.get(position).getPageCount());
        holder.BookPublishDate.setText(book.get(position).getPublishedDate());
        holder.BookISBN.setText(book.get(position).getISBN());
        Picasso.get().load(book.get(position).getThumbnail()).into(holder.BookThumbnail);

    }
    @Override
    public int getItemCount() {
        return book.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView BookThumbnail ;
        TextView BookTitle , BookAuthor ,BookSubtitle ,BookPages ,BookPublishDate ,BookISBN;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            BookTitle = itemView.findViewById(R.id.Book_title);
            BookAuthor = itemView.findViewById(R.id.Book_Author);
            BookSubtitle= itemView.findViewById(R.id.Book_Subtitle);
            BookPages= itemView.findViewById(R.id.Book_pages);
            BookPublishDate= itemView.findViewById(R.id.Book_PublishDate);
            BookThumbnail = itemView.findViewById(R.id.Book_Thumbnail);
            BookISBN = itemView.findViewById(R.id.Book_ISBN);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
