package com.example.movie_stage1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviesDetail extends AppCompatActivity {
    String image,released, title, synopsis, rating;
    TextView releasedDate, movieTitle, plotSynopsis, ratings;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        ratings=findViewById(R.id.userRating_tv);
        plotSynopsis=findViewById(R.id.synopsis_tv);
        movieTitle=findViewById(R.id.txt_original_title);
        releasedDate=findViewById(R.id.releaseDate_tv);
        poster=findViewById(R.id.image);

        rating=getIntent().getExtras().getString("rating");
        synopsis=getIntent().getExtras().getString("synopsis");
        title=getIntent().getExtras().getString("title");
        released=getIntent().getExtras().getString("released");
        image=getIntent().getExtras().getString("image");

        setTitle(title);

        Picasso.get().load(imagePath(image)).into(poster);

        ratings.setText(rating);
        plotSynopsis.setText(synopsis);
        movieTitle.setText(title);
        releasedDate.setText(released);


    }

    private static String imagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/" + "w500" + posterPath;
    }
}
