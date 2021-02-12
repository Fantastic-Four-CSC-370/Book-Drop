package root.radium.bookdrop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookSearch extends AppCompatActivity {
    private String API_KEY = "&key=AIzaSyDtnPYZJxLuF-NI-VW_pUU_rdAXvBthWWc";
    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    TextView bookTitle , bookSubTitle , bookAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.book_search);
        bookTitle = findViewById(R.id.BookTitle);
        bookSubTitle = findViewById(R.id.BookSubTitle);
        getDataFromApi();
    }


    private void getDataFromApi() {
       // String url = API_URL +"isbn:9783319255576" + API_KEY;
        String url ="https://www.googleapis.com/books/v1/volumes?q=list&key=AIzaSyDtnPYZJxLuF-NI-VW_pUU_rdAXvBthWWc";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("Responce", response.toString());
                        try {
                            JSONArray items = response.getJSONArray("items");
                            JSONObject item =items.getJSONObject(0);

                            JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                            String Title =volumeInfo.getString("title");
                            Log.e("Response", Title.toString() );
                            bookTitle.setText("Book Title :" +Title);

                            String SubTitle =volumeInfo.optString("subtitle");
                            if( SubTitle == ""){
                                Log.e("SubTitle","notfound");
                            }else{
                                    Log.e("SubTitle",SubTitle) ;
                            }

                            JSONArray Authors = volumeInfo.optJSONArray("authors");
                            if( Authors == null){
                                Log.e("Author","notfound");
                            }else{
                                if(Authors.length() == 1){
                                    Log.e("Author",Authors.getString(0)) ;
                                }else {
                                    Log.e("Author", Authors.getString(0) + ", "
                                            +Authors.getString(1));
                                }
                            }

                            String PublishDate = volumeInfo.optString("publishedDate").replace("",
                                    "Not Found");
                            Log.e("PublishDate", PublishDate);

                            String BookDescription = volumeInfo.optString("description").replace("",
                                    "Not Found");
                            Log.e("BookDescription", BookDescription);

                            JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                            for (int i=0; i<industryIdentifiers.length(); i++)
                            {
                                JSONObject isbns  = industryIdentifiers.getJSONObject(i);
                                String isbnType = isbns.getString("type");
                                String isbn = isbns.getString("identifier");
                                Log.e("ISBN", isbnType + ":" + isbn  );
                            }

                            int PageCount = volumeInfo.optInt("pageCount");
                            if(PageCount == 0){
                                Log.e("PageCount", "Undefine pages " );
                            }else{
                                Log.e("PageCount", PageCount +" pages " );
                            }

                            JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                            if (imageLinks == null) {
                                Log.e("imageLinks", "not Found");
                            } else {
                                String smallThumbnail = imageLinks.optString("smallThumbnail");
                                if(smallThumbnail == ""){
                                    Log.e("smallThumbnail","not Found" );
                                }else{
                                    Log.e("smallThumbnail", smallThumbnail );
                                }
                            }
                            String PreviewLink = volumeInfo.optString("previewLink");
                            if(PreviewLink == ""){
                                Log.e("PreviewLink", "Undefine pages " );
                            }else{
                                Log.e("PreviewLink", PreviewLink );
                            }

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