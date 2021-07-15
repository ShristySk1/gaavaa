package com.ayata.purvamart.ui.notification;


import com.ayata.purvamart.ui.Fragment.shop.notification.ModelNotification;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDao {
    @Insert
    public void insertNotification(ModelNotification notification);

    @Query("SELECT * FROM notification")
    public LiveData<List<ModelNotification>> getAllNotification();

}
