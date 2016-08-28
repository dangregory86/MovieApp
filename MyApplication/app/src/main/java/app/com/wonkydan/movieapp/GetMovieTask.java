package app.com.wonkydan.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dan Gregory on 27/08/2016.
 */
public class GetMovieTask extends AsyncTask<String, String[], String[]> {

    public GetMovieTask() {
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
    }

    @Override
    protected void onProgressUpdate(String[]... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String[] doInBackground(String... params) {

        //declaring connection and buffer
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        //string to hold the raw json
        String rawJsonString = null;

        try {

            //http://api.themoviedb.org/3/movie/popular?api_key=20701221ae8ca609aed2d76f43516a3e
            //construct the url for the movie db
            final String BASE_URL = "http://api.themoviedb.org/3/movie";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(params[0] + "?")
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
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
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

        return new String[0];
    }
}
