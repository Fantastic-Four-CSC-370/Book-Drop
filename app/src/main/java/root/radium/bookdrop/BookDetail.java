package root.radium.bookdrop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import code.fortomorrow.easysharedpref.EasySharedPref;
import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.LSData;

public class BookDetail extends AppCompatActivity {


    private static final String CHANNEL_ID = "SHUVO TEST";
    private static final String CHANNEL_NAME = "SHUVO TEST";
    private static final String CHANNEL_DES = "SHUVO TEST";

    FirebaseFirestore firebaseFirestore;
     String uid;

    String title
            , authors, description, publishDate
            ,ISBN ,preview,thumbnail ,subTitile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        uid = EasySharedPref.read("TestSp","");
        
//                for Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,
                                            NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DES);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//      hide the default actionBar
        getSupportActionBar().hide();
//      Receive object from other activity
        Intent intent = getIntent();
        Book B = (Book) intent.getSerializableExtra("BookDetail");

        if(B != null){
            thumbnail = B.getThumbnail();
            title = B.getTitle();
            authors = B.getAuthors();
            description = B.getBookDescription();
            publishDate = B.getPublishedDate();
            preview = B.getPreview();
            subTitile = B.getBookSubTitle();
            ISBN = B.getISBN();

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
                firebaseFirestore =FirebaseFirestore.getInstance();

                firebaseFirestore.collection("Borrow Status")
                        .document(uid).set(B).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookDetail.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BookDetail.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });

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



    @Override
    protected void onStart() {


        super.onStart();
    }
}