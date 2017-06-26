package com.example.magda.theogooglebooklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Magda on 24.06.2017.
 */

public class GoogleBookAdapter extends ArrayAdapter<GoogleBook> {
    public GoogleBookAdapter(Context context, List<GoogleBook> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        GoogleBook currentBook = getItem(position);

        // Find the TextView with title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getBookTitle());

        // Find the TextView with author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentBook.getBookAuthor());

        return listItemView;
    }
}
