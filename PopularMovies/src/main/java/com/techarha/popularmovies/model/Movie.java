package com.techarha.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ankit on 18/03/16.
 */
public class Movie implements Parcelable{
    private String movieTitle;
    private String releaseDate;
    private String moviePosterURL;
    private String voteAverage;
    private String plotSynopsis;

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {

            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // parcel construtor
    protected Movie(Parcel p) {
        this.movieTitle = p.readString();
        this.releaseDate = p.readString();
        this.moviePosterURL = p.readString();
        this.voteAverage = p.readString();
        this.plotSynopsis = p.readString();
    }

    public Movie() {

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate.substring(0,4);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.movieTitle);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.moviePosterURL);
        parcel.writeString(this.voteAverage);
        parcel.writeString(this.plotSynopsis);
    }


    public enum THUMB_SIZE{
        XSMALL("w92"), SMALL("w154"), MEDIUM("w185"), LARGE("w342"), XLARGE("w500"), XXLARGE("w780");

        public String size;
        THUMB_SIZE(String size){
            this.size = size;
        }
    }
}
