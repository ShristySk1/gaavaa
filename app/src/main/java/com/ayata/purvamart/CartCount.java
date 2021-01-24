package com.ayata.purvamart;

import java.util.ArrayList;
import java.util.List;

public class CartCount {
    private static Integer myCartCount;
    private static List<MainActivity.cartCountChangeListener> listeners = new ArrayList<MainActivity.cartCountChangeListener>();

    public static Integer getMyBoolean() {
        return myCartCount;
    }

    public static void setMyBoolean(Integer value) {
        myCartCount = value;
        System.out.println("testtttttttttttttttt"+myCartCount);
        for (MainActivity.cartCountChangeListener l : listeners) {
            l.onCartCountChange(myCartCount);
        }
    }

    public static void addMyBooleanListener(MainActivity.cartCountChangeListener l) {
        listeners.add(l);
    }
}
