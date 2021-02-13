package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import root.radium.bookdrop.SupportingClass.Book;
import root.radium.bookdrop.SupportingClass.BookAdapter;

public class BookSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Book> books;
    BookAdapter bookAdapter;
    private String API_KEY = "&key=AIzaSyDtnPYZJxLuF-NI-VW_pUU_rdAXvBthWWc";
    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public String Title ,SubTitle,Author,PublishDate, BookDescription,ISBNInfo,Page,smallThumbnail,PreviewLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.book_search);
        recyclerView =findViewById(R.id.BookRecView);
        books = new ArrayList<>();
        getDataFromApi();

    }


    private void getDataFromApi() {

       // String url = API_URL +"isbn:9783319255576" + API_KEY;
        String url ="https://www.googleapis.com/books/v1/volumes?q=java&key=AIzaSyDtnPYZJxLuF-NI-VW_pUU_rdAXvBthWWc";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("Responce", response.toString());
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i =0 ; i<items.length() ; i++ ){
                                JSONObject item =items.getJSONObject(i);
                                JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                                Title =volumeInfo.getString("title");
                                Log.e("Response", Title );


                                if( volumeInfo.optString("subtitle") == ""){
                                    SubTitle ="Not Found";
                                    Log.e("SubTitle", SubTitle);
                                }else{
                                    SubTitle =volumeInfo.optString("subtitle");
                                    Log.e("SubTitle",SubTitle) ;
                                }


                                if( volumeInfo.optJSONArray("authors") == null){
                                     Author = " Not Found";
                                    Log.e("Author","notfound");
                                }else{
                                    if(volumeInfo.optJSONArray("authors").length() == 1){
                                         Author =volumeInfo.optJSONArray("authors").getString(0);
                                        Log.e("Author",Author) ;
                                    }else {
                                         Author =volumeInfo.optJSONArray("authors").getString(0) + ", "
                                                +volumeInfo.optJSONArray("authors").getString(1);
                                        Log.e("Author", Author);
                                    }
                                }

                                if( volumeInfo.optString("publishedDate") == ""){
                                     PublishDate ="Not Found";
                                    Log.e("PublishDate", PublishDate);
                                }else{
                                     PublishDate =volumeInfo.optString("publishedDate");
                                    Log.e("PublishDate", PublishDate);
                                }

                                if(volumeInfo.optString("description") == ""){
                                    BookDescription = "Not Found";
                                    Log.e("BookDescription", BookDescription);
                                }else{
                                    BookDescription =volumeInfo.optString("description") ;
                                    Log.e("BookDescription", BookDescription);
                                }




                                if( volumeInfo.optJSONArray("industryIdentifiers") == null){
                                    ISBNInfo = " Not Found";
                                    Log.e("Author","ISBNInfo");
                                }else{
                                    if(volumeInfo.optJSONArray("industryIdentifiers").length() == 1){
                                        JSONObject isbns0  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(0);
                                        ISBNInfo = isbns0.getString("type") + ":" + isbns0.getString("identifier");
                                        Log.e("Author",ISBNInfo) ;
                                    }else {
                                        JSONObject isbns0  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(0);
                                        JSONObject isbns1  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(1);
                                        ISBNInfo = isbns0.getString("type") + ":" + isbns0.getString("identifier") +"\n"+ isbns1.getString("type") + ":" + isbns1.getString("identifier") ;
                                        Log.e("Author",ISBNInfo) ;
                                        Log.e("Author", Author);
                                    }
                                }

                                int PageCount = volumeInfo.optInt("pageCount");
                                if(PageCount == 0){
                                     Page = "Undefine pages";
                                    Log.e("PageCount", "Undefine pages " );
                                }else{
                                    Log.e("PageCount", PageCount +" pages " );
                                     Page =PageCount +" pages ";
                                }

                                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                                if (imageLinks == null) {
                                    Log.e("imageLinks", "not Found");
                                     smallThumbnail = "Not Found";
                                } else {
                                    if(imageLinks.optString("smallThumbnail") == ""){
                                        Log.e("smallThumbnail","not Found" );
                                         smallThumbnail = "Not Found";
                                    }else{
                                        Log.e("smallThumbnail", imageLinks.optString("smallThumbnail") );
                                         smallThumbnail = imageLinks.optString("smallThumbnail");
                                    }
                                }

                                if(volumeInfo.optString("previewLink") == ""){
                                    Log.e("PreviewLink", "Undefine pages " );
                                     PreviewLink = "Undefine pages ";
                                }else{
                                    Log.e("PreviewLink",volumeInfo.optString("previewLink"));
                                     PreviewLink = volumeInfo.getString("previewLink");
                                }
                                Book B = new Book();
                                B.setTitle(Title);
                                B.setAuthors(Author);
                                B.setBookDescription(BookDescription);
                                B.setBookSubTitle(SubTitle);
                                B.setISBN(ISBNInfo);
                                B.setPageCount(Page);
                                B.setPublishedDate(PublishDate);
                                B.setThumbnail(smallThumbnail);
                                B.setPreview(PreviewLink);
                                 books.add(B);


                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(BookSearch.this));
                            bookAdapter = new BookAdapter(BookSearch.this,books);
                            recyclerView.setAdapter(bookAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjReq);
    }
}