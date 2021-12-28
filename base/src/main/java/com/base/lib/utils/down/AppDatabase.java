package com.base.lib.utils.down;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Schema export directory is not provided to the annotation processor so we cannot export the schema.
 * You can either provide `room.schemaLocation` annotation processor argument OR set exportSchema to false.
 * exportSchema = false
 */
@Database(entities = {DownInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DownDao downDao();
}