package com.mx.listener;

import com.mx.util.RegisterInterfaceImpl;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		@SuppressWarnings("rawtypes")
		Map map = RegisterInterfaceImpl.getInterfaceImpl(ISessionListener.class);
		for(Object obj : map.values()){
			ISessionListener ss = (ISessionListener)obj;
			ss.sessionDestroyed(session);
			break;
		}
	}

}
