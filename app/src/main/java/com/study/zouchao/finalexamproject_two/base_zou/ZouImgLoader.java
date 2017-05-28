package com.study.zouchao.finalexamproject_two.base_zou;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;

import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ZouImgLoader {
    public static void loadImage(Context context, ImageView iv, String urlImg, int errorImg) {
        Glide.with(context)
                .load(urlImg)
                .error(errorImg)
                .into(iv);
    }

    public static void loadImage(Context context, ImageView iv, Bitmap bm, int errorImg) {
        Glide.with(context)
                .load(bm)
                .error(errorImg)
                .into(iv);
    }


    public static void loadImage(Context context, ImageView iv, int resId, int errorImg) {
        Glide.with(context)
                .load(resId)
                .error(errorImg)
                .into(iv);
    }

    public static void loadImage(Context context, ImageView iv, byte[] bytes, int errorImg,
                                 boolean isAllowMemoryCache, DiskCacheStrategy diskCacheStrategy) {
        Glide.with(context)
                .load(bytes)
                .skipMemoryCache(isAllowMemoryCache)
                .diskCacheStrategy(diskCacheStrategy)
                .error(errorImg)
                .into(iv);
    }


    public static void loadImageWithBlur(Context context, ImageView iv, String url, int errorImg) {
        Glide.with(context)
                .load(url)
                .error(errorImg)
                .bitmapTransform(new BlurTransformation(context))
                .into(iv);
    }

    /*
     * 根据Uri 获得Bitmap
     */
    public static void getBitmapByUrl(final Context context, final String urlImg,
                                      final int width, final int height, final IBitmapReadyListener listener) {
        //注：Glide获取图片的方法必须放在子线程
        Observable.just(urlImg)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        try {
                            return Glide.with(context)
                                        .load(urlImg)
                                        .asBitmap()
                                        .centerCrop()
                                        .into(width, height)
                                        .get();
                        } catch (InterruptedException e) {e.printStackTrace(); return null;
                        } catch (ExecutionException e) {e.printStackTrace(); return null;}
                    }
                })
                .compose(RxSchedulers.<Bitmap>io_main())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        listener.onSuccess(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });

    }


    /*
  * 根据Uri 获得Bitmap
  */
    public static void getBitmapByUrl(final Context context, final int resId,
                                      final int width, final int height, final IBitmapReadyListener listener) {
        //注：Glide获取图片的方法必须放在子线程
        Observable.just(resId)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        try {
                            return Glide.with(context)
                                    .load(resId)
                                    .asBitmap()
                                    .centerCrop()
                                    .into(width, height)
                                    .get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return null;
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .compose(RxSchedulers.<Bitmap>io_main())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        listener.onSuccess(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });

    }

    public interface IBitmapReadyListener {
        void onSuccess(Bitmap bitmap);
        void onFailure(Throwable throwable);
    }
}
