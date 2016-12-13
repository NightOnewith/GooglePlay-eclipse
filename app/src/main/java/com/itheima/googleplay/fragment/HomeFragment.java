package com.itheima.googleplay.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.domian.AppInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.protocol.HomeProtocol;
import com.itheima.googleplay.tools.BitmapHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

public class HomeFragment extends BaseFragment {

    private List<AppInfo> datas;
    private BitmapUtils bitmapUtils;

    //当Fragment挂载的Activity创建的时候调用
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    public View createSuccessView() {
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new HomeAdapter());
        bitmapUtils = BitmapHelper.getBitmapUtils();

        // 第二个参数 慢慢滑动的时候是否加载图片 false 加载  true 不加载
        // 第三个参数 飞速滑动的时候是否加载图片 true 不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);  // 设置如果图片加载中显示的图片
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// 加载失败显示的图片
        return listView;
    }

    private class HomeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if(convertView == null){
                view = View.inflate(UiUtils.getContext(), R.layout.item_app, null);
                holder = new ViewHolder();
                holder.item_icon = (ImageView) view.findViewById(R.id.item_icon);
                holder.item_title = (TextView) view.findViewById(R.id.item_title);
                holder.item_size = (TextView) view.findViewById(R.id.item_size);
                holder.item_bottom = (TextView) view.findViewById(R.id.item_bottom);
                holder.item_rating = (RatingBar) view.findViewById(R.id.item_rating);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            AppInfo appInfo = datas.get(position);
            holder.item_title.setText(appInfo.getName()); //设置应用程序名称
            String size = android.text.format.Formatter.formatFileSize(UiUtils.getContext(), appInfo.getSize());
            holder.item_size.setText(size); //设置应用大小
            holder.item_bottom.setText(appInfo.getDes()); //设置描述信息
            float stars = appInfo.getStars();
            holder.item_rating.setRating(stars); //设置ratingbar的值
            String iconUrl = appInfo.getIconUrl(); //(http://127.0.0.1:8090/)image?name=app/com.youyuan.yyhl/icon.jpg

            //显示图片的控件
            bitmapUtils.display(holder.item_icon, HttpHelper.URL + "image?name=" + iconUrl);
            return view;
        }
    }

    static class ViewHolder{
        ImageView item_icon;
        TextView item_title,item_size,item_bottom;
        RatingBar item_rating;
    }
    public LoadResult load() {
        HomeProtocol protocol = new HomeProtocol();
        datas = protocol.load(0);
        return checkData(datas);
    }

    /* 校验数据 */
    private LoadResult checkData(List<AppInfo> load) {
        if (load == null) {
            return LoadResult.error; //请求服务器失败
        } else {
            if (load.size() == 0) {
                return LoadResult.empty; //请求服务器成功，但服务器没数据
            } else {
                return LoadResult.success; //请求服务器成功，且返回数据
            }
        }
    }

}
