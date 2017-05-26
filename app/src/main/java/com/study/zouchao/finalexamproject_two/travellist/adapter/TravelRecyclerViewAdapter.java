package com.study.zouchao.finalexamproject_two.travellist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/5/17.
 */

public class TravelRecyclerViewAdapter extends RecyclerView.Adapter<TravelRecyclerViewAdapter.MyViewHolder>  {
    private List<TravelItem> mDatas;
    private Context mContext;
    private IRvItemClickListener mListener;
    public TravelRecyclerViewAdapter(Context context, List<TravelItem> datas) {
        mContext = context;
        mDatas = datas;
    }
    public void setOnItemClickListener(IRvItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TravelItem item = mDatas.get(position);
        ZouImgLoader.loadImage(mContext, holder.mIv, item.img, R.drawable.error_pic);
        LogLongUtil.logD("头像》》", item.authorImg);
        ZouImgLoader.loadImage(mContext, holder.ivAuthorImg, item.authorImg, R.drawable.error_pic);
        holder.tvTitle.setText(item.title);
        holder.tvAuthorName.setText(item.authorName);
        holder.tvDate.setText(item.date);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView mIv, ivAuthorImg;
        TextView tvAuthorName, tvTitle, tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mIv = (ImageView) itemView.findViewById(R.id.id_iv);
            ivAuthorImg = (ImageView) itemView.findViewById(R.id.id_iv_authorimg);
            tvAuthorName = (TextView) itemView.findViewById(R.id.id_tv_authorname);
            tvTitle = (TextView) itemView.findViewById(R.id.id_tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.id_tv_date);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(mIv, mDatas.get(getAdapterPosition()).content);
                    }
                }
            });
        }

    }
    public interface IRvItemClickListener {
        void onItemClick(View v, String url);
    }
}



