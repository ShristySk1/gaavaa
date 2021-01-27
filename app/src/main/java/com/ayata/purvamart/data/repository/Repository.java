package com.ayata.purvamart.data.repository;

import com.ayata.purvamart.data.Model.ModelAddress;
import com.ayata.purvamart.data.Model.ModelRegister;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.generic.GenericNetworkResponse;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.RegisterDetail;

public class Repository {
    private NetworkResponseListener listener;
    private ApiService apiService;

    public Repository(NetworkResponseListener listener, ApiService apiService) {
        this.listener = listener;
        this.apiService = apiService;
    }

    public void requestRegister(ModelRegister registerDetail) {
        apiService.registerUser(registerDetail).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestLogin(String mobile, String password) {
        apiService.loginUser(mobile, password).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestVerification(String otp) {
        apiService.verifyOtp(otp).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestAllHome() {
        apiService.getAllHome().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestAllCategory() {
        apiService.getCategoryList().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestAllProducts() {
        apiService.getProductsList().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestCart() {
        apiService.getMyOrder().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestMyOrder() {
        apiService.getMyOrder().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestConfirmOrder(String orderId, String paymentName, String addressId) {
        apiService.confirmOrder(orderId, paymentName, addressId).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestMyAddress() {
        apiService.getAddress().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestUpdateMyAddress(ModelAddress modelAddress) {
        apiService.updateAddress(modelAddress).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestMyRecentOrder() {
        apiService.getRecentOrder().enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestCancelOrder(String orderId) {
        apiService.cancelOrder(orderId).enqueue(new GenericNetworkResponse<>(listener));
    }

    public void requestAddAdress(ModelAddress modelAddress) {
        apiService.addAddress(modelAddress).enqueue(new GenericNetworkResponse<>(listener));
    }
}
