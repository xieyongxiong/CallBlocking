package com.example.xieyongxiong.callblocking.impl;


import com.example.xieyongxiong.callblocking.core.IFilter;
import com.example.xieyongxiong.callblocking.core.MessageData;
import java.util.List;

/**
 * 系统联系人过滤器， 进阶课程内容
 * 
 * @author boyliang
 *
 */
public final class SystemContactFilter implements IFilter {

	List<String> mContact;

	public SystemContactFilter(List<String> contact) {
		mContact = contact;
	}

	@Override
	public int onFiltering(MessageData data) {
		// TODO Auto-generated method stub
		String number = data.getString(MessageData.KEY_DATA);
		if(mContact.contains(number)){
			return IFilter.OP_SKIP;
		}
		return IFilter.OP_BLOCKED;
	}


}
