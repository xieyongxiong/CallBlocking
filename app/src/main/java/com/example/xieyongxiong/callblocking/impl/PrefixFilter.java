package com.example.xieyongxiong.callblocking.impl;


import com.example.xieyongxiong.callblocking.core.IFilter;
import com.example.xieyongxiong.callblocking.core.MessageData;

/**
 * 前缀匹配 过滤器
 * @author boyliang
 *
 */
public final class PrefixFilter implements IFilter {
	private String[] mPrefixs;
	private int mOpcode;

	public PrefixFilter(int opcode, String... prefixs) {
		mOpcode = opcode;
		mPrefixs = prefixs;
	}

	@Override
	public int onFiltering(MessageData data) {
		String phone = data.getString(MessageData.KEY_DATA);
		
		for(String prefix : mPrefixs){
			if(phone.startsWith(prefix)){
				return mOpcode;
			}
		}
		
		return IFilter.OP_SKIP;
	}

}
