package com.itheima.googleplay.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

		return LoadResult.success;
	}



}
