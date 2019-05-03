package com.example.popularmoviesstage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecAdapter.RecAdapterClickHandler {

    private Parcelable mListState;
    private RecAdapter ad;
    private List<Movie> movieso;
    private Movie[] m = new Movie[20];
    private LiveData<List<Movie>> livedata;
    MovieViewModel viewModel;
    private RecyclerView rv;
    private GridLayoutManager lm;
    private boolean FavPage = false;
    private int page = 1;
    private final String TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US&page=";
    private final String POP = "https://api.themoviedb.org/3/movie/popular?api_key=1df55aa821b8db7c0701f47894fe0082&language=en-US&page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieso = new ArrayList<>();
        rv = findViewById(R.id.Rec);
        lm = new GridLayoutManager(this, 3);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        ad = new RecAdapter(this);
        rv.setAdapter(ad);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        if (savedInstanceState == null) {
            start(TOP_RATED + page);
        } else {
            movieso.clear();
            lm.onRestoreInstanceState(mListState);
            m = (Movie[]) savedInstanceState.getParcelableArray("movieList");
            for (int i = 0; i < m.length; i++) {
                movieso.add(m[i]);
            }
            ad.setMovie(movieso);
        }

    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("name", movie.getTitle());
        intentToStartDetailActivity.putExtra("release date", movie.getRelease_date());
        intentToStartDetailActivity.putExtra("vote average", movie.getVote_average());
        intentToStartDetailActivity.putExtra("overviow", movie.getOverview());
        intentToStartDetailActivity.putExtra("Image", movie.getImage());
        intentToStartDetailActivity.putExtra("id", movie.getId());
        startActivity(intentToStartDetailActivity);
    }

    public void start(String url) {
        FavPage=false;
        Uri u = Uri.parse(url);
        try {
            URL u2 = new URL(u.toString());
            viewModel.getMovies(false, u2).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if(movies!=null) {
                        movieso.clear();
                        movieso.addAll(movies);
                        rv.setAdapter(ad);
                        ad.setMovie(movieso);
                        FavPage = false;
                    }
                }
            });

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
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
        if (item.getItemId() == R.id.high) {
            FavPage = false;
            start(TOP_RATED + page);
            return true;
        }
        if (item.getItemId() == R.id.popular) {
            FavPage = false;
            start(POP + page);
            return true;
        }
        if (item.getItemId() == R.id.next) {
            FavPage = false;
            page++;
            start(POP + page);
        }
        if (item.getItemId() == R.id.perv) {
            FavPage = false;
            if (page != 1) {
                page--;
            }
            start(POP + page);
        }
        if (item.getItemId() == R.id.fav) {
            FavPage = true;
            if (movieso != null) {
                movieso.clear();
            }
            viewModel.getMovies(true, null).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if(movies!=null&&FavPage) {
                        movieso.clear();
                        movieso.addAll(movies);
                        rv.setAdapter(ad);
                        ad.setMovie(movieso);
                        FavPage = true;
                    }
                }
            });

        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Movie[] m = new Movie[movieso.size()];
        for (int i = 0; i < movieso.size(); i++) {
            m[i] = movieso.get(i);
        }
        outState.putParcelableArray("movieList", m);
        mListState = lm.onSaveInstanceState();
        outState.putParcelable("scroll", mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        m = (Movie[]) savedInstanceState.getParcelableArray("movieList");
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable("scroll");
        }
    }


}
