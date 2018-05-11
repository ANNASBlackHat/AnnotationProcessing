package com.annasblackhat.annotationprocessing;

import com.annasblackhat.amazing_annotation.AmazingCreate;

/**
 * Created by annasblackhat on 11/05/18
 */

@AmazingCreate
public class Author {
    String name;
    String phone;
    int age;

    public Author(String name, String phone, int age) {
        this.name = name;
        this.phone = phone;
        this.age = age;
    }
}
