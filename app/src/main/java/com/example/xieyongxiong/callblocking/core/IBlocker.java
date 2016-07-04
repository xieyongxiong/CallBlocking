package com.example.xieyongxiong.callblocking.core;

/**
 * 拦截器，负责把Trigger, Filter和Handler三者关联起来 
 * @author boyliang
 *
 */
public interface IBlocker {
	
	/**
	 * 启动
	 */
	void enable();
	
	/**
	 * 关闭
	 */
	void disable();
	
}
