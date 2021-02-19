package com.ayata.purvamart.data.Model;

public class ModelShop {
    public static final int TTTLE_TYPE = 0;
    public static final int AD_TYPE = 1;
    public static final int CATEGORY_TYPE = 2;
    public static final int PRODUCT_TYPE = 3;
    public  static final int STORY_TYPE=4;

    private int type;
    private Object data;
    private Object listener;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getListener() {
        return listener;
    }

    public void setListener(Object listener) {
        this.listener = listener;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ModelShop(int type, Object data, Object listener) {
        this.type = type;
        this.data = data;
        this.listener = listener;
    }
}
