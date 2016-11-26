package com.itheima.googleplay.fragment;

import android.view.View;

public class SubjectFragment extends BaseFragment {

	@Override
	public View createSuccessView() {
		return null;
	}

	@Override
	protected LoadResult load() {
		return LoadResult.error;
	}
}
