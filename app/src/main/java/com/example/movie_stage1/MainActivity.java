package com.example.movie_stage1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    MoviesAdapter moviesAdapter;
    static  ArrayList<String> image;
    static  ArrayList<String> title;
    static  ArrayList<String> releasedate;
    static  ArrayList<String> synopsis;
    static  ArrayList<String> rating;
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
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        image = new ArrayList<>();
        title = new ArrayList<>();
        synopsis = new ArrayList<>();
        releasedate = new ArrayList<>();
        rating = new ArrayList<>();

        sendPostRequestPopular();
        setTitle("Popular Movies");

    }


    private void sendPostRequest() {

        image.clear();
        title.clear();
        synopsis.clear();
        rating.clear();
        releasedate.clear();

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

                        image.add(jo_inside.getString("backdrop_path"));
                        title.add(jo_inside.getString("title"));
                        releasedate.add(jo_inside.getString("release_date"));
                        rating.add(jo_inside.getString("vote_average"));
                        synopsis.add(jo_inside.getString("overview"));

                        Log.d("000888", "onResponse: "+jo_inside.getString("backdrop_path"));
                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, image,title,synopsis,releasedate,rating);
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

                        image.add(jo_inside.getString("backdrop_path"));
                        title.add(jo_inside.getString("title"));
                        releasedate.add(jo_inside.getString("release_date"));
                        rating.add(jo_inside.getString("vote_average"));
                        synopsis.add(jo_inside.getString("overview"));

                        Log.d("000777", "onResponse: "+jo_inside.getString("backdrop_path"));
                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, image,title,synopsis,releasedate,rating);
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

}
