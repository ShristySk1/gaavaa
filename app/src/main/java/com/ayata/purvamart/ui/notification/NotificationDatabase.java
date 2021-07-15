package com.ayata.purvamart.ui.notification;

import com.ayata.purvamart.ui.Fragment.shop.notification.ModelNotification;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ModelNotification.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase {
    public abstract NotificationDao notificationDao();
}
