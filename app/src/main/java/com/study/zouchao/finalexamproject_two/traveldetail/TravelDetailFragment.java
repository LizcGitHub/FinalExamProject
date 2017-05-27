package com.study.zouchao.finalexamproject_two.traveldetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/5/26.
 */

public class TravelDetailFragment extends MyBaseFragment {

    @BindView(R.id.id_wv)
    WebView mWv;
    private String mUrl;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Intent intent = getActivity().getIntent();
        if (intent == null) return;
        mUrl = intent.getStringExtra(TravelDetailActivity.URL);

        mWv.setWebViewClient(new WebViewClient());
        mWv.setWebChromeClient(new WebChromeClient());
        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.getSettings().setBlockNetworkImage(false);
//        mWv.loadUrl(mUrl);
//        mWv.loadUrl("http://www.baidu.com");
        Log.d("URL??", mUrl);
        load();
    }

    private void load() {
        Observable.just(mUrl)
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String url) {
                        try {
                            return Jsoup.connect(url)
                                    .timeout(C.CONN_TIME_OUT)
                                    .get();
                        } catch (IOException e) {e.printStackTrace();return null;}
                    }
                })
//                .map(RxSchedulers.<Document>sleep(3000))
                .compose(RxSchedulers.<Document>io_main())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document doc) {
                        parseToContent(doc);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void parseToContent(Document doc) {
        if (doc == null)    return;
        Elements elesContent =  doc.select(".ctd_content");
        if (elesContent.size() <= 0)    return;
        Element eleContent = elesContent.get(0);
        load2WebView(eleContent.html());
    }

    private void load2WebView(String content) {
        if (StringUtils.isEmpty(content))   return;
        LogLongUtil.logD("web", content);
        mWv.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//        mWv.loadUrl(content);
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
