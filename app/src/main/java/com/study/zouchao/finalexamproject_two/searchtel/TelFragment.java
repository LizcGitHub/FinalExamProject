package com.study.zouchao.finalexamproject_two.searchtel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.study.zouchao.finalexamproject_three.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public class TelFragment extends Fragment {

    private View currentView = null;
    private RecyclerView mRvTel;

    private TelAdapter adapter;
    private List<CallEntity> mTels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tel, container, false);
        initView(currentView);
        return currentView;
    }

    private void initView(View currentView) {
        mRvTel = (RecyclerView) currentView.findViewById(R.id.rv_tel);

        mTels = new ArrayList<>();
        adapter = new TelAdapter(getContext(),mTels);

        mRvTel.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvTel.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            getPhoneNumberFromMobile();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneNumberFromMobile();
                }
            }
            break;
            default:
                Log.e("", "onRequestPermissionsResult: " + "error");
        }
    }

    public void getPhoneNumberFromMobile() {
        // TODO Auto-generated constructor stub
        Cursor cursor = getContext().getContentResolver().query(Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据 
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(Phone.NUMBER));
            CallEntity phoneInfo = new CallEntity(name, number);
            mTels.add(phoneInfo);
        }
        adapter.notifyDataSetChanged();
    }
}
