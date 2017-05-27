package com.study.zouchao.finalexamproject_two.traveldetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.FastBlurUtil;
import com.study.zouchao.finalexamproject_two.util.H5Util;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class TravelDetailFragment extends MyBaseFragment {
    @BindView(R.id.id_collapsing_toolbar)
    CollapsingToolbarLayout mToolbar;
    @BindView(R.id.id_bg_travel_detail)
    ImageView mIvBg;
    @BindView(R.id.id_wv)
    WebView mWv;
    private String mUrl;
    private String mBgImg;
    private String mTitle;

    /**
     * 遍历所有图片加上点击事件
     */
    private static final String JS_CLICK_IMG
            = "javascript:(function() {                                                    "
            + "                        var objs = document.getElementsByTagName(\"img\");  "
            + "                        for(var i = 0; i < objs.length; i++) {              "
            + "                        objs[i].onclick = function() {                      "
            + "                        window.imagelistener.openImage(this.src);           "
            + "                    }                                                       "
            + "                }                                                           "
            + "            })()                                                            ";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            final String url = (String) msg.obj;
            Log.d("webview", "...");
//            ToastUtils.showLong(getActivity(), "图片点击事件！！"+img);
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_big_pic, null, false);
            final PhotoView iv = (PhotoView) view.findViewById(R.id.id_iv);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setView(view);
            builder.show();
            ZouImgLoader.loadImage(getActivity(), iv, url, R.drawable.error_pic);
        }
    };

    //js通信接口
    public class MyJavascriptInterface {
        @JavascriptInterface
        public void openImage(final String img) {
            handler.obtainMessage(0, img).sendToTarget();
        }
    }

    private void addImageClickListener() {
//        //在这添加myClient
//        mWv.setWebChromeClient(new WebChromeClient());
//        mWv.getSettings().setBlockNetworkImage(false);
//        //设置为true
//        mWv.getSettings().setJavaScriptEnabled(true);
//        mWv.addJavascriptInterface(new MyJavascriptInterface(), "imagelistener");
//        mWv.setWebViewClient(new MyWebViewClient());

//        mWv.loadUrl(JS_CLICK_IMG);

        mWv.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("webview", "onLoading");
            addImageClickListener();
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
            Log.d("webview", "onStart");
            addImageClickListener();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            Log.d("webview", "onfinish");
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListener();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.d("webview", "onError");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUrl();
//        init2();
        load();
    }

    private void setUrl() {
        Intent intent = getActivity().getIntent();
        if (intent == null) return;
        mUrl = intent.getStringExtra(TravelDetailActivity.KEY_URL);
        mTitle = intent.getStringExtra(TravelDetailActivity.KEY_TITLE);
        mBgImg = intent.getStringExtra(TravelDetailActivity.KEY_BG_IMG);
        mUrl = mUrl.replace("webapp", "html5");
        showUi();
    }

    private void showUi() {
        mToolbar.setTitle(mTitle);
        //加载背景
        ZouImgLoader.getBitmapByUrl(getActivity(), mBgImg, 1080, 400, new ZouImgLoader.IBitmapReadyListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
//                Log.i("得到图片成功", "en ...");
                ZouImgLoader.loadImage(getActivity(), mIvBg, FastBlurUtil.toBlur(bitmap, 1), R.drawable.error_pic);
//                ZouImgLoader.loadImage(getActivity(), mIvBg, bitmap, R.drawable.error_pic);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("得到图片失败", "en ...");
                throwable.printStackTrace();
            }
        });
        ZouImgLoader.loadImage(getActivity(), mIvBg, mBgImg, R.drawable.error_pic);
    }

    private void load() {
        showLoading("正在加载...");
        Observable.just(mUrl)
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String url) {
                        try {
                            return Jsoup.connect(url)
                                    .timeout(C.CONN_TIME_OUT)
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
//                .map(RxSchedulers.<Document>sleep(3000))
                .compose(RxSchedulers.<Document>io_main())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document doc) {
                        parseToContent(doc);
                        hideBaseLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        hideBaseLoading();
                    }
                });
    }

    private void hideBaseLoading() {
        hideLoading();
    }

    private void parseToContent(Document doc) {
        if (doc == null) return;
        H5Util.removeAllHref(doc);
        H5Util.imgAutoScale(doc);
        Elements elesContent = doc.select(".content");
        if (elesContent.size() <= 0) return;
        Element eleContent = elesContent.get(0);
        load2WebView(eleContent.html());
    }


    private void load2WebView(String content) {
        if (StringUtils.isEmpty(content)) return;
//        LogLongUtil.logD("web", content);

        mWv.getSettings().setJavaScriptEnabled(true);

        mWv.loadDataWithBaseURL(null, changeDataSrc2Src(content), "text/html", "utf-8", null);

        // 添加js交互接口类，并起别名 imagelistner
        mWv.addJavascriptInterface(new MyJavascriptInterface(), "imagelistner");
        mWv.setWebViewClient(new MyWebViewClient());
//        mWv.loadUrl(content);
    }


    private String changeDataSrc2Src(String content) {
        return content.replace("data-src", "src");
    }

    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_fragment_loading_travel_detail);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_travel_detail;
    }
}
