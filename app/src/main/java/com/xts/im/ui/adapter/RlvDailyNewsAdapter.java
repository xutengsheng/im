package com.xts.im.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xts.im.R;
import com.xts.im.adapter.BaseRlvAdapter;
import com.xts.im.bean.DailyNewsBean;
import com.xts.im.util.TimeUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvDailyNewsAdapter extends BaseRlvAdapter {
    private final Context mContext;
    public final ArrayList<DailyNewsBean.StoriesBean> mNewsList;
    private final ArrayList<DailyNewsBean.TopStoriesBean> mBannerList;
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_NEWS = 2;
    private String mDate = "今日新闻";


    public RlvDailyNewsAdapter(Context context, ArrayList<DailyNewsBean.StoriesBean> newsList,
                               ArrayList<DailyNewsBean.TopStoriesBean> bannerList) {

        mContext = context;
        mNewsList = newsList;
        mBannerList = bannerList;
    }

    @Override
    public int getItemViewType(int position) {
        //有banner数据,0的位置是banner
        if (mBannerList.size() > 0) {
            if (position == 0) {
                return TYPE_BANNER;
            } else if (position == 1) {
                return TYPE_DATE;
            } else {
                return TYPE_NEWS;
            }
        } else {
            if (position == 0) {
                return TYPE_DATE;
            } else {
                return TYPE_NEWS;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_BANNER) {
            //轮播图
            //root:子条目添加到哪个容器中
            //attachToRoot:是否关联到容器中
            return new BannerVH(inflater.inflate(R.layout.item_banner, parent, false));
        } else if (viewType == TYPE_DATE) {
            //日期
            return new DateVH(inflater.inflate(R.layout.item_date, parent, false));
        } else {
            //新闻
            return new NewsVH(inflater.inflate(R.layout.item_zhihu_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER) {
            //轮播图
            BannerVH bannerVH = (BannerVH) holder;
            bannerVH.mBanner.setImages(mBannerList)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            //Object path 不是图片链接
                            //Glide.with(mContext).load(path).into(imageView);
                            DailyNewsBean.TopStoriesBean bean = (DailyNewsBean.TopStoriesBean) path;
                            Glide.with(mContext).load(bean.getImage()).into(imageView);
                        }
                    })
                    .start();
        } else if (viewType == TYPE_DATE) {
            //日期
            DateVH dateVH = (DateVH) holder;
            Calendar current = Calendar.getInstance();
            String strCurrent = TimeUtil.parseTime(current);
            if (strCurrent.equals(mDate)) {
                mDate = "今日新闻";
            }
            dateVH.mTv.setText(mDate);
        } else {
            //新闻
            //position 因为前面有banner和日期,所以position从1/2开始的
            int newPosition = position - 1;//减点了日期
            if (mBannerList.size() > 0) {
                newPosition -= 1; //
            }
            DailyNewsBean.StoriesBean bean = mNewsList.get(newPosition);
            NewsVH newsVH = (NewsVH) holder;
            newsVH.mTv.setText(bean.getTitle());
            if (bean.getImages() != null && bean.getImages().size() > 0) {
                Glide.with(mContext).load(bean.getImages().get(0)).into(newsVH.mIv);
            }

            final int finalNewPosition = newPosition;
            newsVH.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, finalNewPosition);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mBannerList.size() > 0) {
            return 1 + 1 + mNewsList.size();
        } else {
            return 1 + mNewsList.size();
        }
    }

    //todo 一会处理
    public void setData(DailyNewsBean bean) {
        mDate = bean.getDate();
        List<DailyNewsBean.StoriesBean> stories = bean.getStories();
        List<DailyNewsBean.TopStoriesBean> top_stories = bean.getTop_stories();

        mNewsList.clear();
        mBannerList.clear();
        if (stories != null && stories.size() > 0) {
            mNewsList.addAll(stories);
        }

        if (top_stories != null && top_stories.size() > 0) {
            mBannerList.addAll(top_stories);
        }
        notifyDataSetChanged();
    }

    class BannerVH extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner mBanner;

        public BannerVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DateVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;

        public DateVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NewsVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.iv)
        ImageView mIv;

        public NewsVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
