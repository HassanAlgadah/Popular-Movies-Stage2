package com.example.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecAdapter.RecAdapterClickHandler {

    private RecAdapter ad;
    private RecyclerView rv;
    private boolean FavPage = false;
    private int page = 1;
    private int where = 1;
    private final String TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US&page=";
    private final String POP = "https://api.themoviedb.org/3/movie/popular?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US&page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.Rec);
        GridLayoutManager lm = new GridLayoutManager(this,3);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        ad = new RecAdapter(this);
        start(TOP_RATED+page);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("name",movie.getTitle());
        intentToStartDetailActivity.putExtra("release date",movie.getRelease_date());
        intentToStartDetailActivity.putExtra("vote average",movie.getVote_average());
        intentToStartDetailActivity.putExtra("overviow",movie.getOverview());
        intentToStartDetailActivity.putExtra("Image",movie.getImage());
        intentToStartDetailActivity.putExtra("id",movie.getId());
        startActivity(intentToStartDetailActivity);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(FavPage){
            new FavTask().execute();
        }

    }

    public void start(String url) {
        Uri u = Uri.parse(url);
        try {
            URL u2 = new URL(u.toString());
            new MovieTask().execute(u2);
        } catch (Exception e) {
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getSupportActionBar().setTitle("");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.high){
            FavPage = false;
            start(TOP_RATED+page);
            where = 1;
            return true;
        }
        if(item.getItemId()==R.id.popular){
            FavPage = false;
            start(POP+page);
            where = 2 ;
            return true;
        }
        if(item.getItemId()==R.id.next){
            FavPage = false;
            page++;
            start(POP+page);
        }
        if(item.getItemId()==R.id.perv){
            FavPage = false;
            if(page !=1) {
                page--;
            }
            start(POP+page);
        }
        if(item.getItemId()==R.id.fav){
            FavPage = true;
            new FavTask().execute();
        }

        return true;
    }

    public class MovieTask extends AsyncTask<URL, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(URL... params) {
            URL searchUrl = params[0];
            List<Movie> movie = null;
            try {
                String url = Json.getResponseFromHttpUrl(searchUrl);
                movie = Json.ParaseMoviepop(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return movie;
        }

        @Override
        protected void onPostExecute(List<Movie> movie) {
            rv.setAdapter(ad);
            ad.setMovie(movie);
        }
    }

    public class FavTask extends AsyncTask<List<Movie>,Void,List<Movie>>{
        @Override
        protected List<Movie> doInBackground(List<Movie>... lists) {
            Database database = Database.getDatabase(MainActivity.this);
            return database.moviesDAO().getAllMovies();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            rv.setAdapter(ad);
            ad.setMovie(movies);
        }
    }

}
