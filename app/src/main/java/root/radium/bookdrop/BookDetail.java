package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class BookDetail extends AppCompatActivity {

    private static final String CHANNEL_ID = "SHUVO TEST";
    private static final String CHANNEL_NAME = "SHUVO TEST";
    private static final String CHANNEL_DES = "SHUVO TEST";
    String title =""
            , authors ="", description="" , publishDate=""
            ,ISBN ="",preview ="" ,thumbnail ="" ,subTitile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        //                for Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DES);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



//        //hide the default actionBar
        getSupportActionBar().hide();

        //Receive
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            thumbnail = extras.getString("book_thumbnail");
            title = extras.getString("book_title");
            authors = extras.getString("book_author");
            description = extras.getString("book_desc");
            publishDate = extras.getString("book_publish_date");
            preview = extras.getString("book_preview");
            subTitile = extras.getString("book_subTitle");
            ISBN = extras.getString("book_ISBN");
        };

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_id);
        collapsingToolbarLayout.setTitleEnabled(true);



        TextView tvTitle = findViewById(R.id.aa_book_name);
        TextView tvAuthors = findViewById(R.id.aa_author);
        TextView tvDesc = findViewById(R.id.aa_description);
        TextView tvPublishDate = findViewById(R.id.aa_publish_date);
        TextView tvPreview = findViewById(R.id.aa_preview);
        ImageView ivThumbnail = findViewById(R.id.aa_thumbnail);
        TextView tvSubTitle = findViewById(R.id.aa_book_subTitle);
        TextView tvISBN = findViewById(R.id.aa_book_ISBN);

        tvTitle.setText(title);
        tvAuthors.setText(authors);
        tvDesc.setText(description);
        tvPublishDate.setText(publishDate);
        tvSubTitle.setText(subTitile);
        tvISBN.setText(ISBN);



        findViewById(R.id.borrowThisBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetail.this, "Borrowing "  , Toast.LENGTH_SHORT).show();
                getNotification();


            }
        });


        final String finalPreview = preview;
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalPreview));
                startActivity(i);

            }
        });

        collapsingToolbarLayout.setTitle(title);

        Glide.with(this).load(thumbnail).into(ivThumbnail);
    }

    public void getNotification(){
        NotificationCompat.Builder mNotification =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText("hello world")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        mNotificationManager.notify(1,mNotification.build());
    }
}