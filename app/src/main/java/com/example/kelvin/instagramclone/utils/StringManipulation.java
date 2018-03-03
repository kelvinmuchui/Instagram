package com.example.kelvin.instagramclone.utils;

/**
 * Created by kelvin on 3/3/18.
 */

public class StringManipulation {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public  static  String condenseUsername(String username){
        return username.replace(" ", ".");
    }
}
