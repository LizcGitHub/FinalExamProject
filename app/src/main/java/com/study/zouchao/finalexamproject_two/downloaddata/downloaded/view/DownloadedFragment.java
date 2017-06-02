package com.study.zouchao.finalexamproject_two.downloaddata.downloaded.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadedDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloaded.view.adapter.DownloadedListAdapter;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.App;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.SetUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/2/14.
 */

public class DownloadedFragment extends Fragment {
    private final static String TAG = "..........已下载Fragment";
    private View mRootView;
    @BindView(R.id.id_rl_empty_view)
    RelativeLayout mRlEmptyView;
    @BindView(R.id.id_lv_downloaded)
    ListView mLvDownloaded;
    private BaseAdapter mAdapter;
    private List<FileInfo> mList = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_downloaded, container, false);
        }
        ButterKnife.bind(this, mRootView);
        EventBusUtils.register(this);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    private void init() {
        //从数据库中取出已下载列表
        mList = DownloadedDAOImpl.getDownloadedListByBean(getContext());
        mAdapter = new DownloadedListAdapter(getContext(), mList);
        mLvDownloaded.setAdapter(mAdapter);
        mLvDownloaded.setEmptyView(mRlEmptyView);
        //注册ContextMenu
        registerForContextMenu(mLvDownloaded);
    }


    /**
     * 下载完成后更新 ListView 的UI
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_DOWNLOADED_SUCCESS_UPDATE_UI :
                updateDownloadedListView(event);
                break;
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.download, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo
                = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = adapterContextMenuInfo.position;
        switch (item.getItemId()) {
            case R.id.id_menu_item_delete_file :
                deleteFileFromSdCard(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    //TODO:
    private void deleteFileFromSdCard(int position) {
        ToastUtils.showShort(getActivity(), "TODO:..删除.."+position);
    }

    private void updateDownloadedListView(EventBusEvent event) {
        List<FileInfo> fileInfos = DownloadedDAOImpl.getDownloadedListByBean(App.getAppContext());
        SetUtils.traverseList("应该是有收到下载完成啊..", fileInfos);
        //TODO：不懂为毛getContext会出现Context空指针
        if (fileInfos == null)  return;
        //更新ListView
        mList.clear();
        mList.addAll(fileInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }
}
