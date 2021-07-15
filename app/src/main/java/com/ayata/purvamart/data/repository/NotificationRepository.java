package com.ayata.purvamart.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.ayata.purvamart.ui.Fragment.shop.notification.ModelNotification;
import com.ayata.purvamart.ui.notification.NotificationDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class NotificationRepository {
    private static NotificationDatabase notificationDatabase = null;
    private static Context context;
    private static final Object LOCK = new Object();
    private static final String DB_Name = "notification_db";

    public NotificationRepository(Context context) {
        this.context = context;
        getDB();
    }

    public synchronized static NotificationDatabase getDB() {
        if (notificationDatabase == null) {
            synchronized (LOCK) {
                if (notificationDatabase == null) {
                    notificationDatabase = Room.databaseBuilder(context,
                            NotificationDatabase.class, DB_Name)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return notificationDatabase;
    }

    public LiveData<List<ModelNotification>> requestNotification() {
        return notificationDatabase.notificationDao().getAllNotification();
    }


    /**
     * Async tasks
     */
    public void insertNotification( ModelNotification modelNotification) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                notificationDatabase.notificationDao().insertNotification(modelNotification);
                return null;
            }
        }.execute();
    }
}
