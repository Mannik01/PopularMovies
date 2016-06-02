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


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        ImageAdapter adapter = new ImageAdapter(getActivity());
        gridview.setAdapter(adapter);
        return rootView;
    }

    // class for optimization purpose
    class ViewHolder {

        SquareImageView thumbnail;

        public ViewHolder(View v) {
            thumbnail = (SquareImageView) v.findViewById(R.id.imageView);
        }
    }

    class ImageAdapter extends BaseAdapter {

        ArrayList<Integer> imageIds;
        Context context;

        public ImageAdapter(Context c) {
            this.context = c;
            imageIds = new ArrayList<Integer>();

            int[] testImages = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                    R.drawable.g};
            for(int i = 0; i < 7; i ++) {
                imageIds.add(testImages[i]);
            }


        }
        @Override
        public int getCount() {
            return imageIds.size();
        }

        @Override
        public Object getItem(int position) {
            return imageIds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View gridItem = convertView;
            ViewHolder holder = null;

            if(gridItem == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridItem = inflater.inflate(R.layout.grid_item_thumbnail, parent, false);
                holder = new ViewHolder(gridItem);
                gridItem.setTag(holder);
            }
            else {
                holder = (ViewHolder) gridItem.getTag();
            }

            holder.thumbnail.setImageResource(imageIds.get(position));
            return gridItem;
        }
    }


}




