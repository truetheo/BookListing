package com.example.magda.theogooglebooklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Magda on 25.06.2017.
 */

public class BooksLoader extends AsyncTaskLoader<List<GoogleBook>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BooksLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BooksLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<GoogleBook> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<GoogleBook> books = Utils.fetchBookData(mUrl);
        return books;
    }

}
