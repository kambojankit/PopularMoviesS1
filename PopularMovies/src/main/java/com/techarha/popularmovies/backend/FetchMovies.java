package com.techarha.popularmovies.backend;

import android.net.Uri;
import android.util.Log;
import com.techarha.popularmovies.BuildConfig;
import com.techarha.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ankit on 19/03/16.
 */
public class FetchMovies {
    private static final String LOG_TAG = FetchMovies.class.getSimpleName();

    public Movie[] fetch(String sortOrder){
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieDbJsonStr = null;
        Movie[] movieDbData = null;

        try {
            // Construct the URL for the MovieDB  query

            String baseUrl = null;
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(sortOrder);

            baseUrl = builder.build().toString();
            String apiKey = "?api_key=" + BuildConfig.TMDB_API_KEY;
            java.net.URL url = new URL(baseUrl.concat(apiKey));

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieDbJsonStr = buffer.toString();
            Log.d(LOG_TAG, movieDbJsonStr);

            movieDbData = getMovieDataFromJson(movieDbJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return movieDbData;
    }

    private Movie[] getMovieDataFromJson(String movieDbJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TMB_LIST = "results";
        final String TMB_TITLE = "title";
        final String TMB_RELEASE_DATE = "release_date";
        final String TMB_POSTER_PATH = "poster_path";
        final String TMB_AVG_VOTE = "vote_average";
        final String TMB_PLOT_SYNOPSIS = "overview";

        JSONObject movieDbJson = new JSONObject(movieDbJsonStr);
        JSONArray movieDBArray = movieDbJson.getJSONArray(TMB_LIST);
        Movie[] moviesList = new Movie[movieDBArray.length()];
        for(int i = 0; i < movieDBArray.length(); i++) {
            Movie _movie = new Movie();
            JSONObject movieData = movieDBArray.getJSONObject(i);

            _movie.setMovieTitle(movieData.getString(TMB_TITLE));
            //TODO Ankit: choose movie poster size dynamically
            _movie.setMoviePosterURL(preparePosterPath(movieData.getString(TMB_POSTER_PATH), Movie.THUMB_SIZE.XLARGE));
            _movie.setReleaseDate(movieData.getString(TMB_RELEASE_DATE));
            _movie.setPlotSynopsis(movieData.getString(TMB_PLOT_SYNOPSIS));
            _movie.setVoteAverage(movieData.getString(TMB_AVG_VOTE));

            moviesList[i] = _movie;
        }

        return moviesList;
    }

    private String preparePosterPath(String data, Movie.THUMB_SIZE thum){
        String baseUrl = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath(thum.size)
                .appendPath(data.substring(1));

        baseUrl = builder.build().toString();
        return baseUrl;
    }
}
