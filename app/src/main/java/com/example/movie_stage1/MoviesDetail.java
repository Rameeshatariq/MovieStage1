package com.example.movie_stage1;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.movie_stage1.RoomDatabase.FavouriteMovies;
import com.example.movie_stage1.RoomDatabase.FavouriteMoviesDao;
import com.example.movie_stage1.RoomDatabase.MoviesRepository;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;



import java.util.ArrayList;
import java.util.List;

import static java.time.chrono.ThaiBuddhistChronology.INSTANCE;

public class MoviesDetail extends AppCompatActivity {
    String image,released, title, synopsis, rating,movieid;
    TextView releasedDate, movieTitle, plotSynopsis, ratings;
    ImageView poster;
    String apikey = "";
    static  ArrayList<String> trailer_id;
     ArrayList<String> author;
     ArrayList<String> review;

    Context ctx=MoviesDetail.this;
    Button favourite;
    static ListView trailer_list;
    static ListView review_list;
    TrailersAdapter trailersAdapter;
    ReviewsAdapter reviewsAdapter;
    MoviesRepository noteRepository;
    static String TRAILER_KEY = "key";
    static String Author_KEY = "author";
    static String Review_KEY = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        ratings=findViewById(R.id.userRating_tv);
        plotSynopsis=findViewById(R.id.synopsis_tv);
        movieTitle=findViewById(R.id.txt_original_title);
        releasedDate=findViewById(R.id.releaseDate_tv);
        poster=findViewById(R.id.image);
        favourite= findViewById(R.id.favourite);
        trailer_list= findViewById(R.id.trailer_list);
        review_list = findViewById(R.id.review_list);

        assert getSupportActionBar() != null;
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        trailer_id= new ArrayList<>();
        author = new ArrayList<>();
        review = new ArrayList<>();

        noteRepository = new MoviesRepository(getApplicationContext());

        rating=getIntent().getExtras().getString("rating");
        synopsis=getIntent().getExtras().getString("synopsis");
        title=getIntent().getExtras().getString("title");
        released=getIntent().getExtras().getString("released");
        image=getIntent().getExtras().getString("image");
        movieid=getIntent().getExtras().getString("movieid");

        sendPostRequestPopular();
        sendPostRequestReview();
       // setTitle(title);


        Picasso.get().load(imagePath(image)).into(poster);

        ratings.setText(rating);
        plotSynopsis.setText(synopsis);
        movieTitle.setText(title);
        releasedDate.setText(released);



      // setListViewHeightBasedOnItems(trailer_list);



        noteRepository.getMovie(movieid).observe(MoviesDetail.this, new Observer<FavouriteMovies>() {
            @Override
            public void onChanged(FavouriteMovies favouriteMovies) {

                if(favouriteMovies != null){
                    favourite.setText("Favourited");
                }
                else{
                    favourite.setText("Mark as Favourite");
                }
                Log.d("000999", "onChanged: "+favouriteMovies);


                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(favourite.getText().toString().equals("Favourited")){
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    noteRepository.delete(movieid);
                                }
                            });
                            favourite.setText("Mark as Favourite");

                        }
                        else{
                            String movie_id = movieid;
                            String name = title;
                            noteRepository.insertTask(movie_id, name,image,released,rating,synopsis);

                        }

                    }
                });
            }
        });





    }

    private static String imagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/" + "w500" + posterPath;
    }

    private void sendPostRequestPopular() {

        trailer_id.clear();

        String url = "http://api.themoviedb.org/3/movie/"+movieid+"/videos?api_key="+apikey;

        Log.d("000777", "mURL " + url);

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000777", "Response:    " + response);


                try{
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray m_jArry = obj.getJSONArray("results");


                    Log.d("000777", "Result:    " + m_jArry);


                    for (int i = 0; i < m_jArry.length(); i++) {

                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        trailer_id.add(jo_inside.getString(TRAILER_KEY));

                       // Log.d("000777", "onResponse: "+jo_inside.getString("name"));
                    }



                    trailersAdapter = new TrailersAdapter(MoviesDetail.this, trailer_id);
                    trailersAdapter.notifyDataSetChanged();
                    trailer_list.setAdapter(trailersAdapter);
                    setListViewHeightBasedOnItems(trailer_list);



                } catch (Exception e) {
                    Log.d("000777", "Err:    " + e.getMessage());
                    Toast.makeText(MoviesDetail.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
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

    private void sendPostRequestReview() {

        author.clear();
        review.clear();

        String url = "http://api.themoviedb.org/3/movie/"+movieid+"/reviews?api_key="+apikey;

        Log.d("000777", "mURL " + url);

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000777", "Response:    " + response);


                try{
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray m_jArry = obj.getJSONArray("results");


                    Log.d("000777", "Result:    " + m_jArry);


                    for (int i = 0; i < m_jArry.length(); i++) {

                        JSONObject jo_inside = m_jArry.getJSONObject(i);

                        author.add(jo_inside.getString(Author_KEY));
                        review.add(jo_inside.getString(Review_KEY));


                        // Log.d("000777", "onResponse: "+jo_inside.getString("name"));
                    }



                    reviewsAdapter= new ReviewsAdapter(MoviesDetail.this, review,author);
                    reviewsAdapter.notifyDataSetChanged();
                    review_list.setAdapter(reviewsAdapter);
                    setListViewHeightBasedOnItems(review_list);



                } catch (Exception e) {
                    Log.d("000777", "Err:    " + e.getMessage());
                    Toast.makeText(MoviesDetail.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
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


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }

    }

}
