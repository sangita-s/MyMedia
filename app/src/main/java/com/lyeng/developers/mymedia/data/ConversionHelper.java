package com.lyeng.developers.mymedia.data;

public class ConversionHelper {
    public static int boolArrayToIntConv(boolean[] bool) {
        int j = 0;
        for (int i = 0; i < bool.length; i++) {
            if (bool[i]) j += 1;
        }
        return j;
    }

    public static boolean[] intToBoolArrayConv5Star(int j) {
        boolean[] bool = new boolean[5];
        for (int i = 0; i < j; i++)
            bool[i] = true;
        return bool;
    }
}
