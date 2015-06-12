package cn.beijing.zukai.newimooc.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.beijing.zukai.newsimooc.R;
import cn.beijing.zukai.newsimooc.bean.NewsBean;
import cn.beijing.zukai.newsimooc.loader.ImageLoader;

/**
 * Created by zukai on 2015/06/12.
 */
public class NewsAdapter extends BaseAdapter{

    private List<NewsBean> mList;
    private LayoutInflater miInflater;
    private ImageLoader mImageLoader;

    public NewsAdapter(Context context,List<NewsBean> data){
        mList = data;
        miInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
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
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);
        viewHolder.tvTitle.setText(mList.get(position).getNewTitle());
        viewHolder.tvContent.setText(mList.get(position).getNewsContent());
        return convertView;
    }

    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }

}
