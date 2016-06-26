package app.com.example.android.popularmovies;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.prefs.PreferenceChangeListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieThumbNailFragment extends Fragment {

    public static final String LOG_TAG = MovieThumbNailFragment.class.getSimpleName();

    ArrayList<String> pathList;
    ImageAdapter imageAdapter;
    GridView gridView;
    String sortType;
    SharedPreferences prefs;
    PreferenceChangeListener listener;
    String prevSort;

    public MovieThumbNailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prevSort = null;
        pathList = new ArrayList<String>();
        imageAdapter = new ImageAdapter(getActivity(), pathList);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        sortType = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity));

    }

    @Override
    public void onResume() {
//        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String sortfield = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity));
//
//        if(prevSort!=null && !sortfield.equals(prevSort)) {
//            populateView();
//
//        }
//        else {
//            prevSort = sortfield;
//            populateView();
//        }
        populateView();
        if(sortType.equals("popularity")) {
            getActivity().setTitle("Popular Movies");
        }
        else {
            getActivity().setTitle("Highly Rated Movies");
        }
        super.onResume();
    }

    private void populateView() {
        FetchImageTask imageTask = new FetchImageTask();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String sortType = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_label_popularity));
        imageTask.execute(sortType);
    }


    public class FetchImageTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String url = "http://api.themoviedb.org/3/discover/movie/?sort_by=" + params[0] + ".desc&api_key=" + BuildConfig.THE_MOVIE_DB;

            try {
                //pathList.clear();
                pathList = new ArrayList<String>(Arrays.asList(getMoviesPaths(url)));
                return pathList;
            } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception in doInBackground method");
            }
            return null;
        }

        private String[] getMoviesPaths(String builtUrl) {

            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String moviesInfo = null;
            URL url = null;

            try {
                url = new URL(builtUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    moviesInfo = null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                if (stringBuilder.length() == 0) {
                    return null;
                }

                moviesInfo = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {

                return getMoviesPathsJSON(moviesInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String[] getMoviesPathsJSON(String moviesInfo) throws JSONException {
            JSONObject jsonReader = new JSONObject(moviesInfo);

            JSONArray jsonArray = jsonReader.getJSONArray("results");
            int jsonArrayLen = jsonArray.length();
            String[] pathsArr = new String[jsonArrayLen];

            JSONObject aMovie;
            for (int i = 0; i < jsonArrayLen; i++) {
                aMovie = jsonArray.getJSONObject(i);
                pathsArr[i] = aMovie.getString("poster_path");
            }
            return pathsArr;
        }

        @Override
        protected void onPostExecute(ArrayList<String> pathsResult) {
            if (pathsResult != null && getActivity() != null) {
//                imageAdapter = new ImageAdapter(getActivity(), pathsResult);
//                gridView = (GridView) getView().findViewById(R.id.gridView);
//                gridView.setAdapter(imageAdapter);
                //imageAdapter.notifyDataSetChanged();

                imageAdapter.replace(pathsResult);
           }

        }


    }

}




