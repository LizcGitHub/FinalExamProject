package com.study.zouchao.finalexamproject_two.searchbusactivity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.study.zouchao.finalexamproject_three.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class BusShowAdapter extends RecyclerView.Adapter<BusShowViewHolder> {
    private final int TYPE_HEAD = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_BOTTOM = 2;

    private List<BusLineResult.BusStation> mBusStations;

    public BusShowAdapter(List<BusLineResult.BusStation> mBusDetails) {
        this.mBusStations = mBusDetails;
    }

    @Override
    public BusShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEAD: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_show_head, parent, false);
            }
            break;
            default:
            case TYPE_ITEM: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_show, parent, false);
            }
            break;
            case TYPE_BOTTOM: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_show_bottom, parent, false);
            }
            break;
        }
        BusShowViewHolder viewHolder = new BusShowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BusShowViewHolder holder, int position) {
        BusLineResult.BusStation entity = mBusStations.get(position);
        holder.mTvContent.setText(entity.getTitle());
    }

    @Override
    public int getItemCount() {
        return mBusStations.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        if (mBusStations.size() != 0 && mBusStations.size() - 1 == position) {
            return TYPE_BOTTOM;
        }
        return TYPE_ITEM;
    }
}

class BusShowViewHolder extends RecyclerView.ViewHolder {
    public View rootView;
    public TextView mBaseLine;
    public TextView mTvContent;

    public BusShowViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.mBaseLine = (TextView) rootView.findViewById(R.id.base_line);
        this.mTvContent = (TextView) rootView.findViewById(R.id.tv_content);
    }
}
