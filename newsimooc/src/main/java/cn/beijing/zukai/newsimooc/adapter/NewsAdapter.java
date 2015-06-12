package cn.beijing.zukai.newsimooc.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.beijing.zukai.newsimooc.R;
import cn.beijing.zukai.newsimooc.bean.NewsBean;
import cn.beijing.zukai.newsimooc.loader.ImageLoader;

/**
 * Created by zukai on 2015/06/12.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<NewsBean> mList;
    private LayoutInflater miInflater;
    private ImageLoader mImageLoader;
    private int mStart;
    private int mEnd;
    private boolean mFirstIn;

    public static String[] URLS;

    public NewsAdapter(Context context,List<NewsBean> data,ListView listView){
        mList = data;
        miInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);
        URLS = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            URLS[i] = data.get(i).getNewsIconUrl();
        }
        mFirstIn = true;
        //register listener
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = miInflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).getNewsIconUrl();
        viewHolder.ivIcon.setTag(url);
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,url);
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon, url);
        viewHolder.tvTitle.setText(mList.get(position).getNewTitle());
        viewHolder.tvContent.setText(mList.get(position).getNewsContent());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            //加载可见项
            mImageLoader.loadImages(mStart,mEnd);
        }else{
            //停止所有的加载任务
            mImageLoader.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if(mFirstIn && visibleItemCount > 0){
            mImageLoader.loadImages(mStart,mEnd);
            mFirstIn = false;
        }
    }

    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }

}
