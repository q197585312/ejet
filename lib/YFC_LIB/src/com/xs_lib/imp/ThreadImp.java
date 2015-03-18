package com.xs_lib.imp;

/**
 * 
 *                       
 * @Filename ContentViewImp.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author xs
 *
 * @Email 197585312@qq.com
 *       
 * @History
 *<li>Author: xs</li>
 *<li>Date: 2015年1月27日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public interface ThreadImp {
	
	void startThread(Object obj);
	
	void threadStart(Object obj);
	
	void successEnd(Object obj);
	
	void errorEnd(Object obj);
}
