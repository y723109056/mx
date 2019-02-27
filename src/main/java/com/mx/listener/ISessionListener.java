package com.mx.listener;

import javax.servlet.http.HttpSession;

public interface ISessionListener {
	/**
	 * session销毁事件
	 */
	public void sessionDestroyed(HttpSession session);
}
