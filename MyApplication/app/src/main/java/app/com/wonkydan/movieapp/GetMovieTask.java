package app.com.wonkydan.movieapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dan Gregory on 27/08/2016.
 */
public class GetMovieTask extends AsyncTask<String, String[], ArrayList<Movie>> {

    Context context;
    MovieAdapter movieAdapter;
    Activity activity;
    GridView gridView;
    ArrayList<Movie> movieArrayList = null;

    public GetMovieTask(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> result) {

        gridView = (GridView) activity.findViewById(R.id.main_movie_grid);

        movieAdapter = new MovieAdapter(activity, result);
        gridView.setAdapter(movieAdapter);


        super.onPostExecute(result);
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        //declaring connection and buffer
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        //string to hold the raw json
        String rawJsonString = null;

        try {

            //construct the url for the movie db
            final String BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0];
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            //set the built uri to a string
            URL url = new URL(builtUri.toString());

            //create the request and open the connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //get the input stream and place it as a string
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if (inputStream == null) {
                rawJsonString = null;
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }
            String line;
            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
            }

            if (stringBuffer.length() == 0) {
                rawJsonString = null;
            }
            rawJsonString = stringBuffer.toString();

        } catch (IOException e) {
            Log.e("Get Movie Task", "Error ", e);
            rawJsonString = null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e("Get Movie Task", "Error ", e);
                }
            }
        }

        try {
            return getMovieInformation(rawJsonString);
        } catch (JSONException e) {
            Log.e("Parsing JSON", "Parsing failed: ", e);
            return null;
        }

    }

    private ArrayList<Movie> getMovieInformation(String json) throws JSONException {

        //items required from the database
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String MOVIE_TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";

        //details for the poster image
        final String POSTER_SIZE = "w185/";
        final String POSTER_PATH_GENERAL = "http://image.tmdb.org/t/p/";

        JSONObject movieJson = new JSONObject(json);
        JSONArray movieJsonArray = movieJson.getJSONArray("results");

        //movie array
        movieArrayList = new ArrayList<>();

        movieArrayList.clear();

        for (int i = 0; i < movieJsonArray.length(); i++) {

            JSONObject movieDetailsJson = movieJsonArray.getJSONObject(i);

            String moviePosterPath = POSTER_PATH_GENERAL + POSTER_SIZE + movieDetailsJson.getString(MOVIE_POSTER_PATH);
            String movieOverview = movieDetailsJson.getString(MOVIE_OVERVIEW);
            String releaseDate = movieDetailsJson.getString(RELEASE_DATE);
            String movieTitle = movieDetailsJson.getString(MOVIE_TITLE);
            Float voteAverage = BigDecimal.valueOf(movieDetailsJson.getDouble(VOTE_AVERAGE)).floatValue();

            movieArrayList.add(new Movie(moviePosterPath, movieOverview, releaseDate, movieTitle, voteAverage));
        }
        return movieArrayList;
    }
}
