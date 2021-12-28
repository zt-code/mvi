package com.base.lib.utils.down;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DownDao {

    //增
    @Insert
    void insert(DownInfo info);

    //增
    @Insert
    void insert(DownInfo... infos);

    //删
    @Delete
    void delete(DownInfo info);

    //改
    @Update
    void update(DownInfo info);

    //查
    @Query("SELECT * FROM downinfo WHERE _name=:name")
    DownInfo getByName(String name);

    //查
    @Query("SELECT * FROM downinfo")
    List<DownInfo> getAll();

}