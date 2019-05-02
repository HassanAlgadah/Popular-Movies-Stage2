package com.example.popularmoviesstage1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private TextView name;
    private ImageView img;
    private TextView ratings;
    private TextView overview;
    private TextView rels;
    private TextView auth1;
    private TextView rev1;
    private TextView auth2;
    private TextView rev2;
    private TextView auth3;
    private TextView rev3;
    private FavWorker favWorker;
    private ImageView fav;
    private ImageView tr1;
    private ImageView tr2;
    private ImageView tr3;
    private boolean clicked = false;
    private Movie currmovie;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent in = getIntent();
        id = in.getStringExtra("id");

        Uri Trailer = Uri.parse("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US");
        try {
            URL u2 = new URL(Trailer.toString());
            new trilerTask().execute(u2);
        } catch (Exception e) { }

        Uri Reviews = Uri.parse("https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US&page=1");
        try {
            URL u3 = new URL(Reviews.toString());
            new ReviewTask().execute(u3);
        } catch (Exception e) { }



        favWorker = new FavWorker(DetailsActivity.this);

        currmovie = new Movie(id, in.getStringExtra("name"), in.getStringExtra("release date"),
                in.getStringExtra("overviow"), in.getStringExtra("Image"), in.getStringExtra("vote average"), null, null, null);

        name = findViewById(R.id.Name);
        img = findViewById(R.id.image_iv);
        ratings = findViewById(R.id.rat);
        overview = findViewById(R.id.Over);
        rels = findViewById(R.id.rd);
        fav = findViewById(R.id.star);
        tr1 = findViewById(R.id.tr1);
        tr2 = findViewById(R.id.tr2);
        tr3 = findViewById(R.id.tr3);
        auth1 = findViewById(R.id.auth1);
        auth2 = findViewById(R.id.auth2);
        auth3 = findViewById(R.id.auth3);
        rev1 = findViewById(R.id.rev1);
        rev2 = findViewById(R.id.rev2);
        rev3 = findViewById(R.id.rev3);


        name.setText(currmovie.getTitle());
        overview.setText(currmovie.getOverview());
        rels.setText(currmovie.getRelease_date());
        ratings.setText(currmovie.getVote_average());
        String im = "https://image.tmdb.org/t/p/w300/" + currmovie.getImage();
        Picasso.get()
                .load(im)
                .into(img);
        new StarAsyncTask().execute(currmovie);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StarAsyncTask().execute(currmovie);
                clicked = true;
            }
        });


    }


    private void markAsFavorite(Movie movie) {
        favWorker.insertFave(movie);
    }

    private void unfavoriteMovie(Movie movie) {
        favWorker.deletFav(movie);
    }

    public class StarAsyncTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movies) {
            Database database = Database.getDatabase(DetailsActivity.this);
            Movie smovie = database.moviesDAO().getSingleMovie(movies[0].getImage());
            return smovie;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            if (clicked) {
                if (movie != null) {
                    unfavoriteMovie(movie);
                    fav.setImageResource(R.drawable.unfav_star);
                } else {
                    markAsFavorite(currmovie);
                    fav.setImageResource(R.drawable.fav_star);
                }
            } else {
                if (movie != null) {
                    fav.setImageResource(R.drawable.fav_star);
                } else {
                    fav.setImageResource(R.drawable.unfav_star);
                }
            }
        }
    }

    public class trilerTask extends AsyncTask<URL, Void, List<String>> {

        @Override
        protected List<String> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            List<String> Trailers = null;
            try {
                String url = Json.getResponseFromHttpUrl(searchUrl);
                Trailers = Json.ParaseTrailers(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return Trailers;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
           setupTrailers(strings);
        }
    }


    public class ReviewTask extends AsyncTask<URL, Void, List<Reviwer>> {

        @Override
        protected List<Reviwer> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            List<Reviwer> reviews = null;
            try {
                String url = Json.getResponseFromHttpUrl(searchUrl);
                reviews = Json.ParaseReviwes(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(List<Reviwer> strings) {
            if(strings.size()==3) {
                auth1.append(strings.get(0).getAuth());
                rev1.setText(strings.get(0).getRev());
                auth2.append(strings.get(1).getAuth());
                rev2.setText(strings.get(1).getRev());
                auth3.append(strings.get(2).getAuth());
                rev3.setText(strings.get(2).getRev());
            }else if (strings.size()==2){
                auth1.append(strings.get(0).getAuth());
                rev1.setText(strings.get(0).getRev());
                auth2.append(strings.get(1).getAuth());
                rev2.setText(strings.get(1).getRev());
                auth3.setVisibility(View.INVISIBLE);
                rev3.setVisibility(View.INVISIBLE);
            }else if (strings.size()==1){
                auth1.append(strings.get(0).getAuth());
                rev1.setText(strings.get(0).getRev());
                auth2.setVisibility(View.INVISIBLE);
                rev2.setVisibility(View.INVISIBLE);
                auth3.setVisibility(View.INVISIBLE);
                rev3.setVisibility(View.INVISIBLE);
            }else {
                auth1.setVisibility(View.INVISIBLE);
                rev1.setVisibility(View.INVISIBLE);
                auth2.setVisibility(View.INVISIBLE);
                rev2.setVisibility(View.INVISIBLE);
                auth3.setVisibility(View.INVISIBLE);
                rev3.setVisibility(View.INVISIBLE);

            }
        }
    }

    public void setupTrailers(final List<String> Trailers) {
        System.out.println(Trailers.size());
        if (Trailers.size() > 2) {
            tr1.setVisibility(View.VISIBLE);
            tr2.setVisibility(View.VISIBLE);
            tr3.setVisibility(View.VISIBLE);
            tr1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(0));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
            tr2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(1));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
            tr3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(2));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
        }else if (Trailers.size()==2){
            tr1.setVisibility(View.VISIBLE);
            tr2.setVisibility(View.VISIBLE);
            tr3.setVisibility(View.INVISIBLE);
            tr1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(0));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
            tr2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(1));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
        }else if (Trailers.size()==1){
            tr1.setVisibility(View.VISIBLE);
            tr2.setVisibility(View.INVISIBLE);
            tr3.setVisibility(View.INVISIBLE);
            tr1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri u = Uri.parse(Trailers.get(0));
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                }
            });
        }else{
            tr1.setVisibility(View.INVISIBLE);
            tr3.setVisibility(View.INVISIBLE);
            tr2.setVisibility(View.INVISIBLE);
        }
    }

}


