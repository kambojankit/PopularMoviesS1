package com.techarha.popularmovies.activity.discover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.techarha.popularmovies.R;
import com.techarha.popularmovies.activity.details.DetailsActivity;
import com.techarha.popularmovies.backend.FetchMovies;
import com.techarha.popularmovies.model.Movie;
import com.techarha.popularmovies.model.MovieAdapter;

public class DiscoverFragment extends Fragment {
    private MovieAdapter mMoviewAdapter;
    private Movie[] movieArray;
    private GridView mGridView;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        this.mGridView = (GridView) rootView.findViewById(R.id.gridview_discover);
        mMoviewAdapter = new MovieAdapter(getActivity(),R.layout.list_item_discover);
        mGridView.setAdapter(mMoviewAdapter);

        mGridView.getWidth();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movie movie = mMoviewAdapter.getItem(position);
                Toast.makeText(getActivity(), movie.getMovieTitle(), Toast.LENGTH_LONG).show();
                Intent detailsActivityIntent = new Intent(getActivity(), DetailsActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(detailsActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_order_key), getString(R.string.default_sort_order));
        FetchMovieDetailsTask fetchMovie = new FetchMovieDetailsTask();
        fetchMovie.execute(sortOrder);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(movieArray != null){
            outState.putParcelableArray("movies", movieArray);
        }
    }

    public class FetchMovieDetailsTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPostExecute(Movie[] data) {
            if(data!=null) {
                mMoviewAdapter.clear();
                for(Movie movie : data) {
                    mMoviewAdapter.add(movie);
                }
                movieArray = data;
                mMoviewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            return new FetchMovies().fetch(params[0]);
        }
    }
}
