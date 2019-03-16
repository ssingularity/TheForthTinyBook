package com.example.demo.Common;

public class Util {
    public static int encodeBill(int bill){
        return 4 * ( bill + 1);
    }

    public static int decodeBill(int bill){
        return bill / 4 - 1;
    }
}
