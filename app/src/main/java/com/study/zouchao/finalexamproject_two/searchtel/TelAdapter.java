package com.study.zouchao.finalexamproject_two.searchtel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public class TelAdapter extends RecyclerView.Adapter<TelViewHolder> implements View.OnClickListener {

    private List<CallEntity> mTels = null;
    private Context context;

    public TelAdapter(Context mContext, List<CallEntity> mTels) {
        this.mTels = mTels;
        this.context = mContext;
    }

    @Override
    public TelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tel, parent, false);
        TelViewHolder viewHolder = new TelViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TelViewHolder holder, int position) {
        CallEntity entity = mTels.get(position);

        holder.mTvName.setText(entity.getName());
        holder.mTvTel.setText(entity.getTel());
        holder.rootView.setTag(entity);
        holder.rootView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mTels.size();
    }

    @Override
    public void onClick(View v) {
        CallEntity entity = (CallEntity) v.getTag();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + entity.getTel()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

class TelViewHolder extends RecyclerView.ViewHolder {

    public View rootView;
    public TextView mTvName;
    public TextView mTvTel;

    public TelViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.mTvName = (TextView) rootView.findViewById(R.id.tv_name);
        this.mTvTel = (TextView) rootView.findViewById(R.id.tv_tel);
    }
}
