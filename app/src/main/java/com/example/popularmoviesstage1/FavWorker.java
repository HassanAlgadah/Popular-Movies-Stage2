package com.example.popularmoviesstage1;

import android.content.Context;
import android.os.AsyncTask;

public class FavWorker {
    private boolean isFav = true;
    private Database database;

    public FavWorker (Context context){
        database = Database.getDatabase(context);
    }

    public void insertFave(Movie movie){
        isFav = true;
        new FavAsyncTask().execute(movie);
    }
    public void deletFav(Movie movie){
        isFav = false;
        new FavAsyncTask().execute(movie);
    }

    public class FavAsyncTask extends AsyncTask<Movie,Void,Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(isFav){
                database.moviesDAO().insertMovie(movies[0]);
            }else {
                database.moviesDAO().deleteMovie(movies[0].getImage());
            }
            return null;
        }
    }

}
