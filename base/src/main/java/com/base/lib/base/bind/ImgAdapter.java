package com.base.lib.base.bind;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import com.base.lib.base.glide.GlideUtil;
import com.base.lib.base.glide.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ImgAdapter {

    @BindingAdapter("onImageRes")
    public static void onImageRes(ImageView imageView, @DrawableRes int res) {
        if(res != 0) {
            imageView.setImageResource(res);
        }
    }

    @BindingAdapter("onImageGif")
    public static void onImageGif(ImageView imageView, @DrawableRes int res) {
        if(res != 0) {
            Glide.with(imageView.getContext())
                    .asGif()
                    .load(res).into(imageView);
        }
    }
    
    @BindingAdapter(value = {"onImageUrl", "onImageRadius", "onImageBorder", "onImageScaleType"}, requireAll = false)
    public static void onImageUrl(ImageView imageView, String url, int radius, int border, int scaleType) {
        //L.i("======================================onImageUrl");
        if(url == null || url.trim().equals("")) return;

        // 获取上下文对象
        Context context = imageView.getContext();
        RequestOptions options = null;

        // 圆形图片
        if(radius == -1) {
            //L.i("====ImgBindindAdapter  加载圆形图片");
            options = GlideUtil.getCircle();
        }

        //圆角图片
        if(radius > 0) {
            Resources r = context.getResources();
            int num = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, r.getDisplayMetrics());
            if(border > 0) {
                //L.i("====ImgBindindAdapter  加载圆角图片 指定圆角位置  角度="+radius);
                RoundedCornersTransformation.CornerType type = getCornerType(border);
                options = GlideUtil.getRound(num, type);
            }else {
                //L.i("====ImgBindindAdapter  加载圆角图片  角度="+radius);
                options = GlideUtil.getRound(num);
            }
        }

        //普通图片
        if(radius == 0) {
            if(scaleType == 0) {
                //L.i("====ImgBindindAdapter  加载图片 - 裁剪");
                options = new RequestOptions().centerCrop();
            }else {
                //L.i("====ImgBindindAdapter  加载图片 - 保持比例");
                options = new RequestOptions().fitCenter();
            }
        }

        //加载图片
        //L.i("====加载图片  "+url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static RoundedCornersTransformation.CornerType getCornerType(int border) {
        RoundedCornersTransformation.CornerType cornerType = RoundedCornersTransformation.CornerType.ALL;
        if(border == 1) {
            cornerType = RoundedCornersTransformation.CornerType.TOP;
        }else if(border == 2) {
            cornerType = RoundedCornersTransformation.CornerType.TOP_LEFT;
        }else if(border == 3) {
            cornerType = RoundedCornersTransformation.CornerType.BOTTOM;
        }else if(border == 4) {
            cornerType = RoundedCornersTransformation.CornerType.BOTTOM_LEFT;
        }else if(border == 5) {
            cornerType = RoundedCornersTransformation.CornerType.LEFT;
        }else if(border == 6) {
            cornerType = RoundedCornersTransformation.CornerType.TOP_LEFT;
        }else if(border == 7) {
            cornerType = RoundedCornersTransformation.CornerType.TOP_RIGHT;
        }else if(border == 8) {
            cornerType = RoundedCornersTransformation.CornerType.RIGHT;
        }else if(border == 9) {
            cornerType = RoundedCornersTransformation.CornerType.TOP_RIGHT;
        }else if(border == 10) {
            cornerType = RoundedCornersTransformation.CornerType.BOTTOM_RIGHT;
        }else if(border == 11) {
            cornerType = RoundedCornersTransformation.CornerType.OTHER_TOP_LEFT;
        }else if(border == 12) {
            cornerType = RoundedCornersTransformation.CornerType.OTHER_TOP_RIGHT;
        }else if(border == 13) {
            cornerType = RoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT;
        }else if(border == 14) {
            cornerType = RoundedCornersTransformation.CornerType.OTHER_BOTTOM_RIGHT;
        }else if(border == 15) {
            cornerType = RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT;
        }else if(border == 16) {
            cornerType = RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT;
        }
        return cornerType;
    }

}