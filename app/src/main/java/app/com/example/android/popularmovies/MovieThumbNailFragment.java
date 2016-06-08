package app.com.example.android.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieThumbNailFragment extends Fragment {


    public MovieThumbNailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> paths = new ArrayList<String>();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        ImageAdapter adapter = new ImageAdapter(getActivity(), paths);
        gridview.setAdapter(adapter);
        return rootView;
    }


    class ImageAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> pathList;


        public ImageAdapter(Context c, ArrayList<String> paths) {
            this.pathList = paths;
            this.context = c;
            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return pathList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView == null) {
                //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item_thumbnail, parent, false);
                //holder = new ViewHolder(gridItem);
                //gridItem.setTag(holder);
            }
            //else {
               // holder = (ViewHolder) gridItem.getTag();
            //}

            //holder.thumbnail.setImageResource(imageIds.get(position));
            Picasso
            .with(context)
            //.load(imageIds.get(position))
            .load("http://api.themoviedb.org/3/movie/popular?api_key=THE_MOVIE_DB")
            .into((ImageView) convertView);
            return convertView;
        }
    }


}




