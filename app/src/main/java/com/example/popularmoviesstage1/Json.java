package com.example.popularmoviesstage1;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Json {

    public static List<Movie> ParaseMoviepop(String json) {
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            List<Movie> listmovie = new ArrayList<>(results.length());
            String id = null;

            for (int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();
                JSONObject firstob = results.getJSONObject(i);
                movie.setTitle(firstob.getString("original_title"));
                movie.setImage(firstob.getString("poster_path"));
                id = firstob.getString("id");
                movie.setId(id);
                movie.setVote_average(firstob.getString("vote_average"));
                movie.setOverview(firstob.getString("overview"));
                movie.setRelease_date(firstob.getString("release_date"));
                listmovie.add(i,movie);
            }

            return listmovie;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> ParaseTrailers(String json) {
        List<String> Trailers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < 3; i++) {
                JSONObject tr = results.getJSONObject(i);
                Trailers.add("https://www.youtube.com/watch?v="+tr.getString("key"));
            }
        }catch (Exception e){
        }
        return Trailers;
    }

    public static List<Reviwer> ParaseReviwes(String json) {
        List<Reviwer> reviews = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < 3; i++) {
                JSONObject tr = results.getJSONObject(i);
                Reviwer r = new Reviwer();
                r.setAuth(tr.getString("author"));
                r.setRev(tr.getString("content"));
                reviews.add(r);
            }

        }catch (Exception e){

        }
        return reviews;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
