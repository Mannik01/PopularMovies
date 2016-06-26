package app.com.example.android.popularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MandipSilwal on 6/13/16.
 */
public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> paths;

    public ImageAdapter(Context c, ArrayList<String> pathsAPI) {
        this.context = c;
        paths = pathsAPI;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        ImageView mImage;

        public ViewHolder(ImageView view) {
            mImage = view;
        }

        public ImageView getmImage() {
            return mImage;
        }
    }

    public void setData(ArrayList<String> urls) {
        paths.clear();
        paths.addAll(urls);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        ViewHolder holder = null;

        if(imageView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView = (ImageView) inflater.inflate(R.layout.grid_item_thumbnail,parent,false);
            holder = new ViewHolder(imageView);
            imageView.setTag(holder);
        }
        else {
            holder = (ViewHolder) imageView.getTag();
        }

        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/" + getItem(position))
                .into(holder.getmImage());
        return imageView;
    }

    // method to update adapter for gridview
    public void replace(ArrayList<String> urls) {
        this.paths.clear();
        this.paths.addAll(urls);
        notifyDataSetChanged();
    }

}