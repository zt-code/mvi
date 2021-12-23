package com.base.lib.base.glide;

import android.graphics.Bitmap;

import com.base.lib.R;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.util.HashMap;

public class GlideUtil {

    private static HashMap<String, RequestOptions> roundMap = new HashMap<>();

    public static void clear() {
        roundMap.clear();
    }

    /**
     * 获取圆形图片
     * @return
     */
    public static RequestOptions getCircle() {
        return RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.bg_default_yuan);
    }

    /**
     * 获取圆角图片
     * @param radius
     * @return
     */
    public static RequestOptions getRound(int radius) {
        String key = radius + "";
        if(roundMap.containsKey(key)) {
            return roundMap.get(key);
        }else {
            RequestOptions options = new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(radius));
            roundMap.put(key, options);
            return options;
        }
    }

    /**
     * 获取指定圆角图片
     * @param radius
     * @return
     */
    public static RequestOptions getRound(int radius, RoundedCornersTransformation.CornerType cornerType) {
        String key = radius + cornerType.toString();
        if(roundMap.containsKey(key)) {
            return roundMap.get(key);
        }else {
            MultiTransformation<Bitmap> mation = null;
            switch (cornerType) {
                case ALL:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case TOP:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case BOTTOM:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case LEFT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case RIGHT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case TOP_LEFT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case TOP_RIGHT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case BOTTOM_LEFT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
                case BOTTOM_RIGHT:
                    mation = new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
                            new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                    break;
            }
            roundMap.put(key, RequestOptions.bitmapTransform(mation));
            return RequestOptions.bitmapTransform(mation);
        }
    }


}