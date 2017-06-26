package com.example.magda.theogooglebooklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GoogleBook>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String DO_NOT_TELL = "&key=AIzaSyD4M3PVsNXSA7RAfU121hIoN-4aXDIYt74";

    private static final int LOADER_ID = 1;
    private GoogleBookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View loadingIndicator;
    private EditText key_word;
    private boolean first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list_view);
        key_word = (EditText) findViewById(R.id.edit_textbox);


        loadingIndicator = findViewById(R.id.loading_indicator);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new GoogleBookAdapter(this, new ArrayList<GoogleBook>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(mAdapter);
        if(first){
            mEmptyStateTextView.setText(R.string.first_search);
            first = false;
        }
        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, MainActivity.this);
        } else {
            // Otherwise, display error
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        Button button = (Button) findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there is a network connection, fetch data
                if (isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    mAdapter.clear();
                    loadingIndicator.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(LOADER_ID,null,MainActivity.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    loadingIndicator.setVisibility(View.GONE);
                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });


    }

    @Override
    public Loader<List<GoogleBook>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        String key = key_word.getText().toString();
        String googleQuery = REQUEST_URL + StringToKey(key) + DO_NOT_TELL;

        return new BooksLoader(this, googleQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<GoogleBook>> loader, List<GoogleBook> books) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "nothing to show."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous books data
        mAdapter.clear();

        // If there is a valid list of {@link GoogleBook}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<GoogleBook>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public String StringToKey(String string) {
        return string.toLowerCase().replace(" ", "+");
    }
    public boolean isConnected(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
