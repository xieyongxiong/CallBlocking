package com.example.xieyongxiong.callblocking.impl;


import android.util.Log;

import com.example.xieyongxiong.callblocking.core.IFilter;
import com.example.xieyongxiong.callblocking.core.MessageData;

import java.util.List;

/**
 * 基于号码归属地的过滤器，走网络请求，所以需求时间，作为进阶课程内容
 * HTTP API: https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=1111111111
 * @author boyliang
 *
 */
public final class LocationFilter implements IFilter {

	List<String> mlist;
	String mloc;
	public LocationFilter(List<String> list,String loc) {
		mlist = list;
		mloc = loc;

	}

	@Override
	public int onFiltering(MessageData data) {
		// TODO Auto-generated method stub

		if(mlist.contains(mloc)){
			return IFilter.OP_BLOCKED;
		}

		return IFilter.OP_SKIP;
	}

}
