package com.ayata.purvamart.ui.Fragment.shop;

import com.ayata.purvamart.data.Model.ModelCategory;
import com.ayata.purvamart.data.network.response.ProductDetail;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewModel extends ViewModel {
    MutableLiveData<List<ModelCategory>> mutableLiveDataCategory=new MutableLiveData<>();
    MutableLiveData<List<ProductDetail>> mutableLiveDataProduct=new MutableLiveData<>();

    public LiveData<List<ModelCategory>> getMutableLiveDataCategory() {
        return mutableLiveDataCategory;
    }

    public void setMutableLiveDataCategory(MutableLiveData<List<ModelCategory>> mutableLiveDataCategory) {
        this.mutableLiveDataCategory = mutableLiveDataCategory;
    }

    public LiveData<List<ProductDetail>> getMutableLiveDataProduct() {
        return mutableLiveDataProduct;
    }

    public void setMutableLiveDataProduct(MutableLiveData<List<ProductDetail>> mutableLiveDataProduct) {
        this.mutableLiveDataProduct = mutableLiveDataProduct;
    }
}
