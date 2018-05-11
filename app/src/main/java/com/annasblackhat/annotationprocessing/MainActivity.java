package com.annasblackhat.annotationprocessing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookLog.Log(new Book("12345", "C++ Programming"));

        AuthorLog.Log(new Author("","",20));
    }
}
