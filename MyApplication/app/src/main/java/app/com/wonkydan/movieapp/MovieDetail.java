package app.com.wonkydan.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    ImageView mMoviePicture;
    TextView mTitle, mDescription, mReleaseDate;
    RatingBar mRating;

    String mMoviePictureUrl, mTitleString, mDescriptionString, mReleaseDateString;
    Float mRatingFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();

        //find all the views
        mMoviePicture = (ImageView) findViewById(R.id.detail_image);
        mTitle = (TextView) findViewById(R.id.detail_title);
        mDescription = (TextView) findViewById(R.id.detail_description);
        mRating = (RatingBar) findViewById(R.id.detail_rating);
        mReleaseDate = (TextView) findViewById(R.id.detail_release_date);

        //get the movie details from the intent
        mMoviePictureUrl = bundle.getString("movie_poster_path");
        mTitleString = bundle.getString("movie_title");
        mDescriptionString = bundle.getString("movie_overview");
        mReleaseDateString = "Released: " + bundle.getString("movie_release_date");
        mRatingFloat = bundle.getFloat("movie_rating") / 2;

        //set the details for al the views
        Picasso.with(this).load(mMoviePictureUrl).into(mMoviePicture);
        mTitle.setText(mTitleString);
        mDescription.setText(mDescriptionString);
        mRating.setRating(mRatingFloat);
        mReleaseDate.setText(mReleaseDateString);
    }
}
