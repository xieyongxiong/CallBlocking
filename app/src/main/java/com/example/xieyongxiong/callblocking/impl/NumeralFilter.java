package com.example.xieyongxiong.callblocking.impl;


import android.util.Log;

import com.example.xieyongxiong.callblocking.core.IFilter;
import com.example.xieyongxiong.callblocking.core.MessageData;

import java.util.List;

/**
 * 
 * 基于号码严格匹配的过滤器
 * @author boyliang
 *
 */
public final class NumeralFilter implements IFilter {
	private List<String> mNumbers;
	private int mOpcode;
	
	public NumeralFilter(int opcode, List<String> numbers){
		mOpcode = opcode;
		mNumbers = numbers;
	}
	
	@Override
	public int onFiltering(MessageData data) {
		String phone = data.getString(MessageData.KEY_DATA);

		Log.e("THG","onfilter"+phone);

		if(mNumbers.contains(phone)){
			return IFilter.OP_BLOCKED;
		}

		return IFilter.OP_SKIP;
	}
}
