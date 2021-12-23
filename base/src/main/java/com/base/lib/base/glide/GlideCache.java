package com.base.lib.base.glide;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.base.lib.net.L;
import com.base.lib.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

@GlideModule
public class GlideCache extends AppGlideModule {

    private String getStorageDirectory(Context context) {
        File file = new File(Utils.getContext().getExternalFilesDir("glide").getAbsolutePath());
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            L.i("SD卡正常挂载");
            return file.getAbsolutePath();
        }else {
            L.i("SD卡无挂载");
            return file.getAbsolutePath();
        }
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //super.applyOptions(context, builder);
        int diskCacheSizeBytes = 1024 * 1024 * 200; // 100 MB
        builder.setDiskCache(new DiskLruCacheFactory(getStorageDirectory(context), diskCacheSizeBytes));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

}
