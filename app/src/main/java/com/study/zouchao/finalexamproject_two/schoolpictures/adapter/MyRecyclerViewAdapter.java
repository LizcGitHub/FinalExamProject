package com.study.zouchao.finalexamproject_two.schoolpictures.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/5/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>  {
    private List<String> mDatas;
    private Context mContext;
    private IRvItemClickListener mListener;
    public MyRecyclerViewAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
    }
    public void setOnItemClickListener(IRvItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_pics, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZouImgLoader.loadImage(mContext, holder.mIv, mDatas.get(position), R.drawable.error_pic);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView mIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mIv = (ImageView) itemView.findViewById(R.id.id_iv);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(mIv, mDatas.get(getAdapterPosition()));
                    }
                }
            });
        }

    }
    public interface IRvItemClickListener {
        void onItemClick(View v, String url);
    }
}



