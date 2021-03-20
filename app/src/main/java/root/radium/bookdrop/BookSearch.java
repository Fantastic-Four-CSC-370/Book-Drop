package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import root.radium.bookdrop.Adapters.BookAdapter;

public class  BookSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Book> books;
    BookAdapter bookAdapter;
    private String API_KEY = "&key=AIzaSyDtnPYZJxLuF-NI-VW_pUU_rdAXvBthWWc";
    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public String ID,Title ,SubTitle,Author,PublishDate, BookDescription,ISBNInfo,Page,smallThumbnail,PreviewLink;
    EditText searchText;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.book_search);

        searchText =findViewById(R.id.searchText);
        recyclerView =findViewById(R.id.BookRecView);
        books = new ArrayList<>();

        getDataFromApi("Software Engineering");

        findViewById(R.id.btnsrc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                books.clear();
                String query =  searchText.getText().toString().trim();
                getDataFromApi(query);

            }
        });
    }

    private void getDataFromApi(String query) {

        String url = API_URL + query + API_KEY;

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
                                ID = item.getString("id");
                             //   Log.e("ID" ,ID);
                                JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                                Title =volumeInfo.getString("title");

                                if( volumeInfo.optString("subtitle") == ""){
                                    SubTitle ="Not Found";

                                }else{
                                    SubTitle =volumeInfo.optString("subtitle");
                                }

                                if( volumeInfo.optJSONArray("authors") == null){
                                     Author = " Not Found";

                                }else{
                                    if(volumeInfo.optJSONArray("authors").length() == 1){
                                         Author =volumeInfo.optJSONArray("authors").getString(0);

                                    }else {
                                         Author =volumeInfo.optJSONArray("authors").getString(0) + ", "
                                                +volumeInfo.optJSONArray("authors").getString(1);

                                    }
                                }

                                if( volumeInfo.optString("publishedDate") == ""){
                                     PublishDate ="Not Found";

                                }else{
                                     PublishDate =volumeInfo.optString("publishedDate");

                                }

                                if(volumeInfo.optString("description") == ""){
                                    BookDescription = "Not Found";

                                }else{
                                    BookDescription =volumeInfo.optString("description") ;

                                }

                                if( volumeInfo.optJSONArray("industryIdentifiers") == null){
                                    ISBNInfo = " Not Found";

                                }else{
                                    if(volumeInfo.optJSONArray("industryIdentifiers").length() == 1){
                                        JSONObject isbns0  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(0);
                                        ISBNInfo = isbns0.getString("type") + ":" + isbns0.getString("identifier");

                                    }else {
                                        JSONObject isbns0  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(0);
                                        JSONObject isbns1  = volumeInfo.optJSONArray("industryIdentifiers").getJSONObject(1);
                                        ISBNInfo = isbns0.getString("type") + ":" + isbns0.getString("identifier") +"\n"+ isbns1.getString("type") + ":" + isbns1.getString("identifier") ;
                                    }
                                }

                                int PageCount = volumeInfo.optInt("pageCount");
                                if(PageCount == 0){
                                     Page = "Undefine pages";
                                }else{

                                     Page =PageCount +" pages ";
                                }



                                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                                if (imageLinks == null) {

                                     smallThumbnail = "Not Found";
                                } else {
                                    if(imageLinks.optString("smallThumbnail") == ""){

                                         smallThumbnail = "Not Found";
                                    }else{

                                         smallThumbnail = imageLinks.optString("smallThumbnail");
                                    }
                                }

                                if(volumeInfo.optString("previewLink") == ""){

                                     PreviewLink = "Undefine pages ";
                                }else{
                                     PreviewLink = volumeInfo.getString("previewLink");
                                }
                                Book B = new Book();
                                B.setID(ID);
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
                            bookAdapter = new BookAdapter(BookSearch.this,books);
                            recyclerView.setLayoutManager(new LinearLayoutManager(BookSearch.this));
                            recyclerView.setAdapter(bookAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookSearch.this, "Error! Restart your app", Toast.LENGTH_SHORT).show();

            }

        });
        queue.add(jsonObjReq);

    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}