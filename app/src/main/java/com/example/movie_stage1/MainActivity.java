package com.example.movie_stage1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.movie_stage1.RoomDatabase.FavouriteMovies;
import com.example.movie_stage1.RoomDatabase.MoviesRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    MoviesAdapter moviesAdapter;
    static  ArrayList<String> image;
    static  ArrayList<String> title;
    static  ArrayList<String> releasedate;
    static  ArrayList<String> synopsis;
    static  ArrayList<String> rating;
    static  ArrayList<String> movieid;
    static  String TITLE_KEY="title";
    static  String IMAGE_KEY="poster_path";
    static  String SYNOPSIS_KEY="overview";
    static  String RATING_KEY="vote_average";
    static  String RELEASED_KEY="release_date";
    static  String ID_KEY="id";

    Context ctx= MainActivity.this;

    String apikey = "";

    RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,  menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            case R.id.toprated:
                setTitle(R.string.toprated_movie);
                sendPostRequest();
                break;

            case R.id.popular:
                setTitle(R.string.popular_movie);
                sendPostRequestPopular();
                break;

            case R.id.favmovies:
                setTitle(R.string.fav_movie);
                sendPostRequestFav();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movies_poster);
        recyclerView.setLayoutManager(new GridLayoutManager(this,calculateNoOfColumns(ctx)));


        image = new ArrayList<>();
        title = new ArrayList<>();
        synopsis = new ArrayList<>();
        releasedate = new ArrayList<>();
        rating = new ArrayList<>();
        movieid = new ArrayList<>();

        sendPostRequestPopular();
        setTitle("Popular Movies");

    }


    private void sendPostRequest() {

        image.clear();
        title.clear();
        synopsis.clear();
        rating.clear();
        releasedate.clear();
        movieid.clear();

        String url = "http://api.themoviedb.org/3/movie/top_rated?api_key="+apikey;

        Log.d("000862", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000888", "Response:    " + response);


                try{

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray m_jArry = obj.getJSONArray("results");


                    Log.d("000888", "Result:    " + m_jArry);


                    for (int i = 0; i < m_jArry.length(); i++) {

                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        image.add(jo_inside.getString(IMAGE_KEY));
                        title.add(jo_inside.getString(TITLE_KEY));
                        releasedate.add(jo_inside.getString(RELEASED_KEY));
                        rating.add(jo_inside.getString(RATING_KEY));
                        synopsis.add(jo_inside.getString(SYNOPSIS_KEY));
                        movieid.add(jo_inside.getString(ID_KEY));

                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, image,title,synopsis,releasedate,rating,movieid);
                    recyclerView.setAdapter(moviesAdapter);

                    Log.d("000999", "onResponse: "+title.toString());

                } catch (Exception e) {
                    Log.d("000862", "Err:    " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("000862", "error    " + error.getMessage());


            }
        }) {


        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }




    private void sendPostRequestPopular() {

        image.clear();
        title.clear();
        synopsis.clear();
        rating.clear();
        releasedate.clear();
        movieid.clear();

        String url = "http://api.themoviedb.org/3/movie/popular?api_key="+apikey;

        Log.d("000777", "mURL " + url);

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000777", "Response:    " + response);


                try{

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray m_jArry = obj.getJSONArray("results");


                    Log.d("000777", "Result:    " + m_jArry);


                    for (int i = 0; i < m_jArry.length(); i++) {

                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        image.add(jo_inside.getString(IMAGE_KEY));
                        title.add(jo_inside.getString(TITLE_KEY));
                        releasedate.add(jo_inside.getString(RELEASED_KEY));
                        rating.add(jo_inside.getString(RATING_KEY));
                        synopsis.add(jo_inside.getString(SYNOPSIS_KEY));
                        movieid.add(jo_inside.getString(ID_KEY));

                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, image,title,synopsis,releasedate,rating,movieid);
                    recyclerView.setAdapter(moviesAdapter);



                } catch (Exception e) {
                    Log.d("000777", "Err:    " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("000862", "error    " + error.getMessage());


            }
        }) {


        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }

    private void sendPostRequestFav() {

        image.clear();
        title.clear();
        synopsis.clear();
        rating.clear();
        releasedate.clear();
        movieid.clear();

        MoviesRepository noteRepository = new MoviesRepository(getApplicationContext());

        noteRepository.getTasks().observe(MainActivity.this, new Observer<List<FavouriteMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovies> notes) {
                for(FavouriteMovies note : notes) {

                    title.add(note.getMovie_name());
                    image.add(note.getImage());
                    synopsis.add(note.getSynopsis());
                    movieid.add(note.getMovie_id());
                    releasedate.add(note.getReleased_date());
                    rating.add(note.getRating());

                    System.out.println("-----------------------");
                    System.out.println(note.getId());
                    System.out.println(note.getMovie_id());
                    System.out.println(note.getMovie_name());
                    System.out.println(note.getReleased_date());
                    System.out.println(note.getSynopsis());
                    System.out.println(note.getRating());

                }

                moviesAdapter = new MoviesAdapter(MainActivity.this, image,title,synopsis,releasedate,rating,movieid);
                recyclerView.setAdapter(moviesAdapter);

            }
        });



    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }



}
