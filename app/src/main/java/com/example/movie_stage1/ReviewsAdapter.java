package com.example.movie_stage1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewsAdapter extends BaseAdapter {
    private Context ctx;
    TextView trailer;
    ArrayList<String> review_list;
    ArrayList<String> author_list;
    private LayoutInflater inflater = null;

    // Constructor
    public ReviewsAdapter(Context ctx, ArrayList<String> review_list, ArrayList<String> author_list) {
        this.ctx = ctx;
        this. review_list= review_list;
        this. author_list= author_list;

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        return review_list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View row, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (row == null) {
            holder = new ViewHolder();

            row = inflater.inflate(R.layout.reviews_layout, null);

            holder.review = row.findViewById(R.id.review);
            holder.author = row.findViewById(R.id.author_name);


            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.author.setText(author_list.get(pos)+":");
        holder.review.setText(review_list.get(pos));




        return row;
    }




    static class ViewHolder {
        TextView review;
        TextView author;

    }

}
