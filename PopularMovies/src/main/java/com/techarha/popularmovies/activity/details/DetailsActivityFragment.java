package com.techarha.popularmovies.activity.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.techarha.popularmovies.R;
import com.techarha.popularmovies.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Movie movie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        DetailsView detailsView = new DetailsView();

        detailsView.imageView = (ImageView) rootView.findViewById(R.id.details_image);
        detailsView.title = (TextView) rootView.findViewById(R.id.details_title);
        detailsView.releaseDate = (TextView) rootView.findViewById(R.id.details_release_date);
        detailsView.voteAverage = (TextView) rootView.findViewById(R.id.details_vote_average);
        detailsView.plotSynopsis = (TextView) rootView.findViewById(R.id.details_plot_synopsis);


        Picasso.with(getContext()).load(movie.getMoviePosterURL()).into(detailsView.imageView);
        detailsView.title.setText(movie.getMovieTitle());
        detailsView.releaseDate.setText(movie.getReleaseDate());
        detailsView.voteAverage.setText(movie.getVoteAverage() + "/10");
        detailsView.plotSynopsis.setText(movie.getPlotSynopsis());

        return rootView;
    }

    private class DetailsView{
        ImageView imageView;
        TextView title;
        TextView releaseDate;
        TextView voteAverage;
        TextView plotSynopsis;
    }
}
