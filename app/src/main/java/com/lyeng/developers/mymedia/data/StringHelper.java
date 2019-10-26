package com.lyeng.developers.mymedia.data;

public class StringHelper {
    public static String stringListToString(String[] stringList){
        String genreString = "";
        for(String string:stringList){
            genreString = genreString + ",";
        }
        return genreString;
    }
}
