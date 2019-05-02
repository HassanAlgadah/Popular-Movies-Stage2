package com.example.popularmoviesstage1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
@Entity
public class Movie implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    public int movieId;
    private String id;
    private String title;
    private String overview;
    private String image;
    private String vote_average;
    private String release_date;
    private String tr1;
    private String tr2;
    private String tr3;


    public Movie() {
    }

    public Movie(String id ,String title,String release_date, String overview, String image, String vote_average,String tr1,String tr2 , String tr3) {
        this.title = title;
        this.overview = overview;
        this.image = image;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.tr1 = tr1;
        this.tr2 = tr2;
        this.tr3 = tr3;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTr1() {
        return tr1;
    }

    public String getTr2() {
        return tr2;
    }

    public String getTr3() {
        return tr3;
    }

    public void setTr1(String tr1) {
        this.tr1 = tr1;
    }

    public void setTr2(String tr2) {
        this.tr2 = tr2;
    }

    public void setTr3(String tr3) {
        this.tr3 = tr3;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Movie(Parcel in){
        id = in.readString();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        image = in.readString();
        vote_average = in.readString();
        tr1 = in.readString();
        tr2 = in.readString();
        tr3 = in.readString();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(image);
        dest.writeString(vote_average);
        dest.writeString(tr1);
        dest.writeString(tr2);
        dest.writeString(tr3);

    }
}