package com.example.popularmoviesstage1;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;


public class RecAdapter extends RecyclerView.Adapter<RecAdapter.Viewholder> {
    private List<Movie> movieList;
    private Context con;

    private final RecAdapterClickHandler mClickHandler;

    public interface RecAdapterClickHandler {
        void onClick(Movie movie);
    }

    public RecAdapter(RecAdapterClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;

    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        con = viewGroup.getContext();
        View view = inflater.inflate(R.layout.moviedetails,viewGroup,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        Movie m = movieList.get(i);
        viewholder.title.setText(m.getTitle());
        String im = "https://image.tmdb.org/t/p/w300/"+m.getImage();
        Picasso.get()
                .load(im)
                .into(viewholder.image);
    }

    public void setMovie(List<Movie> movies) {
        movieList = movies;
    }

    @Override
    public int getItemCount() {
        if(movieList == null){
            return 0;
        }
        return movieList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView image;

        public Viewholder (View v){
        super(v);
        image = v.findViewById(R.id.m_image);
        title = v.findViewById(R.id.m_name);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie m = movieList.get(adapterPosition);
            mClickHandler.onClick(m);
        }
    }
}
