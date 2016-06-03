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

        ArrayList<Integer> imageIds = new ArrayList<Integer>();

        int[] testImages = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                R.drawable.g};
        for(int i = 0; i < 7; i ++) {
            imageIds.add(testImages[i]);
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        ImageAdapter adapter = new ImageAdapter(getActivity(), imageIds);
        gridview.setAdapter(adapter);
        return rootView;
    }

//    // class for optimization purpose
//    class ViewHolder {
//
//        SquareImageView thumbnail;
//
//        public ViewHolder(View v) {
//            thumbnail = (SquareImageView) v.findViewById(R.id.imageView);
//        }
//    }

    class ImageAdapter extends ArrayAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<Integer> imageIds;

        public ImageAdapter(Context c, ArrayList<Integer> imageIds) {
            super(c, R.layout.grid_item_thumbnail, imageIds);
            this.imageIds = imageIds;
            this.context = c;
            inflater = LayoutInflater.from(c);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;

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
            .load(imageIds.get(position))
            .into((ImageView) convertView);
            return convertView;
        }
    }


}




