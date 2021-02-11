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
        String url = API_URL +"isbn:9783319255576" + API_KEY;
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

                            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                            String Title =volumeInfo.getString("title");
                            bookTitle.setText("Book Title :" +Title);
                            String SubTitle =volumeInfo.getString("subtitle");
                            bookSubTitle.setText("Book Subtitle :" +SubTitle);
                            JSONArray Authors = volumeInfo.getJSONArray("authors");
                            for(int i = 0 ; i<Authors.length() ; i++ ) {
                                Log.e("Authors ", Authors.getString(i) );
                            }


                            Log.e("Response", Title.toString() );
                            Log.e("Response", SubTitle.toString() );


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