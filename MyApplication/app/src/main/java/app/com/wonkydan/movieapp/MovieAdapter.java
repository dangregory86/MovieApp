package app.com.wonkydan.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dan Gregory on 28/08/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context, ArrayList<Movie> movie) {

        super(context, 0, movie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridItemView = convertView;
        ViewHolder viewHolder;

        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster_image_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) gridItemView.findViewById(R.id.movie_poster_image_item);

            gridItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) gridItemView.getTag();
        }

        //get current movie
        final Movie currentMovie = getItem(position);

        //get the movie poster path and apply it to imageview
        Picasso.with(getContext()).load(currentMovie.getmMoviePosterPath()).into(viewHolder.imageView);


        //set an onitemclicklistener to start the detail activity and show more movie information
        gridItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deatilMovieIntent = new Intent(getContext(), MovieDetail.class);

                //set the movie released to just the year
                String releasedFull = currentMovie.getmReleaseDate();
                String[] releasedSplit = releasedFull.split("-");


                //add the movie information into a bundle
                Bundle bundle = new Bundle();
                bundle.putString("movie_title", currentMovie.getmMovieTitle());
                bundle.putString("movie_poster_path", currentMovie.getmMoviePosterPath());
                bundle.putString("movie_overview", currentMovie.getmMovieOverview());
                bundle.putString("movie_release_date", releasedSplit[0]);
                bundle.putFloat("movie_rating", currentMovie.getmVoteAverage());

                //add the bundle to the intent
                deatilMovieIntent.putExtras(bundle);

                //start the activity
                getContext().startActivity(deatilMovieIntent);
            }
        });
        return gridItemView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
