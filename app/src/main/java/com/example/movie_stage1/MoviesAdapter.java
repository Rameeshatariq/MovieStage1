package com.example.movie_stage1;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<String> mData;
    private List<String> rating;
    private List<String> synopsis;
    private List<String> title;
    private List<String> released;
    private List<String> movieid;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    MoviesAdapter(Context context, List<String> data, List<String> title, List<String> synopsis, List<String> released,
                  List<String> rating, List<String> movieid) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.synopsis = synopsis;
        this.released = released;
        this.title = title;
        this.rating = rating;
        this.movieid = movieid;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_poster_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.get().load(imagePath(mData.get(position))).into(holder.poster_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent= new Intent(context, MoviesDetail.class);
                intent.putExtra("image", mData.get(position));
                intent.putExtra("title", title.get(position));
                intent.putExtra("synopsis", synopsis.get(position));
                intent.putExtra("released", released.get(position));
                intent.putExtra("rating", rating.get(position));
                intent.putExtra("movieid", movieid.get(position));

                context.startActivity(intent);

                Log.d("000999", "onClick: "+mData.get(position));
                Log.d("000999", "onClick: "+released.get(position));
                Log.d("000999", "onClick: "+rating.get(position));
                Log.d("000999", "onClick: "+synopsis.get(position));
                Log.d("000999", "onClick: "+title.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster_image;

        ViewHolder(View itemView) {
            super(itemView);
            poster_image = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private static String imagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/" + "w500" + posterPath;
    }
}
