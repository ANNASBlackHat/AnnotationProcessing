package com.annasblackhat.annotationprocessing;

import com.annasblackhat.amazing_annotation.AmazingCreate;

/**
 * Created by annasblackhat on 11/05/18
 */

@AmazingCreate
public class Book {
    String isbn;
    String title;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
}
