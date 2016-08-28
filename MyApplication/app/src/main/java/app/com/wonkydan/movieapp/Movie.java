package app.com.wonkydan.movieapp;

/**
 * Created by Dan Gregory on 28/08/2016.
 */
public class Movie {

    private String mMoviePosterPath;
    private String mMovieOverview;
    private String mReleaseDate;
    private String mMovieTitle;
    private Float mVoteAverage;

    public Movie(String mMoviePosterPath, String mMovieOverview, String mReleaseDate, String mMovieTitle, Float mVoteAverage) {
        this.mMoviePosterPath = mMoviePosterPath;
        this.mMovieOverview = mMovieOverview;
        this.mReleaseDate = mReleaseDate;
        this.mMovieTitle = mMovieTitle;
        this.mVoteAverage = mVoteAverage;
    }

    public String getmMoviePosterPath() {
        return mMoviePosterPath;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public Float getmVoteAverage() {
        return mVoteAverage;
    }
}
