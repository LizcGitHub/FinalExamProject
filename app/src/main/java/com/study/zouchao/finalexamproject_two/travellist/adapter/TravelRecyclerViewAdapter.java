package com.study.zouchao.finalexamproject_two.travellist.adapter;

import android.content.Context;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/5/17.
 */

public class TravelRecyclerViewAdapter extends RecyclerView.Adapter<TravelRecyclerViewAdapter.MyViewHolder>  {
    private List<TravelListResult.ResultBean> mDatas;
    private Context mContext;
    private IRvItemClickListener mListener;
    private View mFooterView;
    private static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    private static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    public TravelRecyclerViewAdapter(Context context, List<TravelListResult.ResultBean> datas) {
        mContext = context;
        mDatas = datas;
    }
    public void setOnItemClickListener(IRvItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel, parent, false);
        return new MyViewHolder(view);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder != null) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                TravelListResult.ResultBean item = mDatas.get(position);
                ZouImgLoader.loadImage(mContext, holder.mIv, item.getDynamicPhotoUrl(), R.drawable.error_pic);
                ZouImgLoader.loadImage(mContext, holder.ivAuthorImg, item.getUserPhoto(), R.drawable.error_pic);
                holder.tvTitle.setText(item.getTitle());
                holder.tvAuthorName.setText(item.getNickname());
                holder.tvDate.setText(item.getDepartureDate());
                holder.tvView.setText(item.getPictureCount()+"");
                holder.tvComment.setText(item.getCommentCount()+"");
                holder.tvVisit.setText(item.getVisitCount()+"");
            }
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mFooterView == null) return mDatas.size();
        else return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * Holder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView mIv, ivAuthorImg;
        TextView tvAuthorName, tvTitle, tvDate, tvView, tvComment, tvVisit;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mIv = (ImageView) itemView.findViewById(R.id.id_iv);
            ivAuthorImg = (ImageView) itemView.findViewById(R.id.id_iv_authorimg);
            tvAuthorName = (TextView) itemView.findViewById(R.id.id_tv_authorname);
            tvTitle = (TextView) itemView.findViewById(R.id.id_tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.id_tv_date);
            tvView = (TextView) itemView.findViewById(R.id.id_tv_view);
            tvComment = (TextView) itemView.findViewById(R.id.id_tv_comment);
            tvVisit = (TextView) itemView.findViewById(R.id.id_tv_visit);

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        TravelListResult.ResultBean item = mDatas.get(getAdapterPosition());
                        mListener.onItemClick(mIv, item.getTitle(), item.getCoverImageUrl(), item.getH5Url());
                    }
                }
            });
        }

    }
    public interface IRvItemClickListener {
        void onItemClick(View v, String title, String bgImg, String url);
    }
}



