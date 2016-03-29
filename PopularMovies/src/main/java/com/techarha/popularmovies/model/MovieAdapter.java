package com.techarha.popularmovies.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.techarha.popularmovies.R;

/**
 * Created by ankit on 18/03/16.
 */
public class MovieAdapter  extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_discover, parent, false);
            image = (ImageView) convertView.findViewById(R.id.item_list_discover_image);

            convertView.setTag(image);
        }
        image= (ImageView) convertView.getTag();
        Movie movie=getItem(position);
        Picasso.with(getContext()).load(movie.getMoviePosterURL()).noFade().into(image);

        return convertView;
    }
}
