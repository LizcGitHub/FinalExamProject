package com.study.zouchao.finalexamproject_two.downloaddata.downloading.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadedDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.contract.IDownloadingContract;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.UpdateProgressDTO;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.presenter.DownloadingPresenter;
import com.study.zouchao.finalexamproject_two.util.App;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/2/12.
 */

public class DownloadingFragment extends Fragment implements IDownloadingContract.IDownloadingView {
    private View mRootView = null;

    @BindView(R.id.lvFile)
    ListView mLvFile;
    @BindView(R.id.id_rl_empty_view)
    RelativeLayout mRlEmptyView;
    //P
    private DownloadingPresenter mPresenter;

    @Override
    public void setListViewAdapter(BaseAdapter adapter) {
        mLvFile.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_downloading, container, false);
        }
        ButterKnife.bind(this, mRootView);
        mLvFile.setEmptyView(mRlEmptyView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mPresenter = new DownloadingPresenter(getContext(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }

    private void updateProgress(UpdateProgressDTO dto) {
        mPresenter.updateProgress(dto);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_ONLY_UPDATE_UI :
                mPresenter.updateDownloadingListViewItemCount();
                break;
            case EventBusEvent_C.EVENT_UPDATE_DOWNLOADING_PROGRESS :
                updateProgress((UpdateProgressDTO) event.getObj());
                break;
            case EventBusEvent_C.EVENT_DOWNLOADED_SUCCESS_UPDATE_UI :
                updateDownloadingListViewItemCount();
                break;
        }
    }

    private void updateDownloadingListViewItemCount() {
        mPresenter.updateDownloadingListViewItemCount();
    }

}
