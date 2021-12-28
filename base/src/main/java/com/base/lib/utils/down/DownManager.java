package com.base.lib.utils.down;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class DownManager {


    private static DownManager ins;
    private static AppDatabase mDb;

    public static DownManager init(Context context) {
        if (ins == null) {
            synchronized (DownManager.class) {
                if (ins == null) {
                    ins = new DownManager(context);
                }
            }
        }
        return ins;
    }

    public static DownManager getIns() {
        return ins;
    }

    public DownManager(Context context) {
        mDb = Room.databaseBuilder(context,
                AppDatabase.class, "database-name")
                .allowMainThreadQueries().build();
    }

    //增
    public synchronized void insert(DownInfo user) {
        mDb.downDao().insert(user);
    }

    //删
    public synchronized void delete(DownInfo info) {
        mDb.downDao().delete(info);
    }

    //改
    public synchronized void update(DownInfo info) {
        mDb.downDao().update(info);
    }

    //查
    public synchronized DownInfo getByName(String name) {
        return mDb.downDao().getByName(name);
    }

    //查询
    public synchronized List<DownInfo> getAll() {
        return mDb.downDao().getAll();
    }

}
