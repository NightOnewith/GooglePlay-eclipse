package com.itheima.googleplay.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay.domian.AppInfo;
import com.itheima.googleplay.protocol.HomeProtocol;

import java.util.List;

public class HomeFragment extends BaseFragment {

    //当Fragment挂载的Activity创建的时候调用
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    public View createSuccessView() {
        TextView tv = new TextView(getActivity());
        tv.setText("加载成功了...");
        tv.setTextSize(30);
        return tv;
    }

    public LoadResult load() {
        HomeProtocol protocol = new HomeProtocol();
        List<AppInfo> load = protocol.load(0);
        return checkData(load);
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
