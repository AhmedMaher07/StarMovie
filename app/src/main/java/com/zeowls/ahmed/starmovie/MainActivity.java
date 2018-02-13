package com.zeowls.ahmed.starmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeowls.ahmed.starmovie.Adapter.HomeAdapter;
import com.zeowls.ahmed.starmovie.Model.MovieInfo;

public class MainActivity extends AppCompatActivity {
    private static final String ENDPOINT = "https://api.themoviedb.org/3/discover/movie?api_key=6cf9a1607b70b18336daf88dc5f25c80";
    private RequestQueue requestQueue;
    private Gson gson;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2
                , GridLayoutManager.VERTICAL,false));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));

        requestQueue = Volley.newRequestQueue(this);
        fetchPosts();

    }


    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            MovieInfo movies = gson.fromJson(response, MovieInfo.class);
            recyclerview.setAdapter(new HomeAdapter(MainActivity.this,movies.getResults()));
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
}
