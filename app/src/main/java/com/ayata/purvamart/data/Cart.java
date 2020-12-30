package com.ayata.purvamart.data;

import com.ayata.purvamart.Model.ModelItem;

import java.util.List;

public class Cart {
    public static List<ModelItem> modelItems;

    public static List<ModelItem> getModelItems() {
        return modelItems;
    }

    public static void setModelItems(List<ModelItem> modelItems) {
        Cart.modelItems = modelItems;
    }

}
