package com.base.lib.utils.down;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DownInfo {

    @PrimaryKey/*(autoGenerate = true)*/
    public int down_id;

    @ColumnInfo(name = "_tag")
    public int tag;

    @ColumnInfo(name = "_name")
    public String name = "";

    @ColumnInfo(name = "_path")
    public String path = "";

    @ColumnInfo(name = "_start")
    public long start;

    @ColumnInfo(name = "_end")
    public long end;

    @ColumnInfo(name = "_progress")
    public String progress = "0";

    @Override
    public String toString() {
        return "DownInfo{" +
                "down_id=" + down_id +
                ", tag=" + tag +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", progress='" + progress + '\'' +
                '}';
    }
}
