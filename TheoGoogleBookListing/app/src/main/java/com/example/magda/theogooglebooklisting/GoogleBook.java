package com.example.magda.theogooglebooklisting;

/**
 * Created by Magda on 24.06.2017.
 */

public class GoogleBook {
    private String bookTitle;
    private String bookAuthor;

    public GoogleBook(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }
}
