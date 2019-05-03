package com.example.popularmoviesstage1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieViewModel extends AndroidViewModel{

    Database database;
    MutableLiveData<List <Movie>> movieLiveData = new MutableLiveData<>();
    LiveData<List<Movie>> liveData;

    public MovieViewModel(Application application) {
        super (application);
        //Get database instance:
        database = Database.getDatabase(application);
        liveData = database.moviesDAO().getAllMovies();
    }

    public LiveData <List<Movie>> getMovies (boolean fav, URL url) {
        if (fav){
            System.out.println("database");

            return liveData;

        } else {
            new MovieTask().execute(url);
            return movieLiveData;
        }
    }

    public class MovieTask extends AsyncTask<URL, Void, Void> {
        @Override
        protected Void doInBackground(URL... params) {
            URL searchUrl = params[0];
            List<Movie> movie = null;
            try {
                String url = Json.getResponseFromHttpUrl(searchUrl);
                movie = Json.ParaseMoviepop(url);
                movieLiveData.postValue(movie);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
