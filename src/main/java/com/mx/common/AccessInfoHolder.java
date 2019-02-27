package com.mx.common;


import com.mx.page.IPageSize;
import com.mx.page.PageSize;
import com.mx.permission.WebLoginInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 类解释
 *
 * @Version 1.0 2017/3/28
 * @Author Merlin
 */
public class AccessInfoHolder {

    private final static ThreadLocal<AccessInfo> LOCAL = new ThreadLocal<AccessInfo>();

    private AccessInfoHolder() {
    }

    public static void init(HttpServletRequest request) {
        AccessInfo accessInfo = new AccessInfo();
        // TODO 应该将loginInfo字符串常量化，所有用到该字符串的地方都用常量表示
        Object object = request.getSession().getAttribute("loginInfo");

        // 分页参数
        int pageSize = request.getParameter("pageSize") != null ? Integer.valueOf(request.getParameter("pageSize")) : 25;
        int pageNum = request.getParameter("pageNum") != null ? Integer.valueOf(request.getParameter("pageNum")) : 1;
        IPageSize page = new PageSize(pageSize, pageNum);

        // Session中的用户信息
        WebLoginInfo webLoginInfo = null;
        if (null != object && object instanceof WebLoginInfo) {
            webLoginInfo = (WebLoginInfo) object;
        }
        
        String uri = request.getRequestURI();
        
        //app数据
        if (uri.matches(".*/api/.*")) {
        	
        }else{
	        accessInfo.setRandNum("RandNum=" + Double.toString(Math.random()));
	        accessInfo.setCtx(request.getContextPath());
	        accessInfo.setRootPath(request.getSession().getServletContext().getRealPath("/"));
	        accessInfo.setWebLoginInfo(webLoginInfo);
	        accessInfo.setPage(page);
        }
        
        LOCAL.set(accessInfo);
    }

    public static AccessInfo getAccessInfo() {
        return LOCAL.get();
    }

    public static WebLoginInfo getWebLoginInfo() {
        return LOCAL.get().getWebLoginInfo();
    }

    public static IPageSize getPage() {
        return LOCAL.get().getPage();
    }

    public static class AccessInfo {
        private WebLoginInfo webLoginInfo;

        private IPageSize page;

        private String rules;

        private String account;

        private String autoLogin;

        private String needLogin;

        /**
         * 项目站点路径
         */
        private String rootPath;
        /**
         * 前台页面相对路径
         */
        private String ctx;
        /**
         * 请求随机数
         */
        private String randNum;

        public IPageSize getPage() {
            return page;
        }

        public void setPage(IPageSize page) {
            this.page = page;
        }

        public WebLoginInfo getWebLoginInfo() {
            return webLoginInfo;
        }

        public void setWebLoginInfo(WebLoginInfo webLoginInfo) {
            this.webLoginInfo = webLoginInfo;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAutoLogin() {
            return autoLogin;
        }

        public void setAutoLogin(String autoLogin) {
            this.autoLogin = autoLogin;
        }


        public String getNeedLogin() {
            return needLogin;
        }

        public void setNeedLogin(String needLogin) {
            this.needLogin = needLogin;
        }

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }

        public String getCtx() {
            return ctx;
        }

        public void setCtx(String ctx) {
            this.ctx = ctx;
        }

        public String getRandNum() {
            return randNum;
        }

        public void setRandNum(String randNum) {
            this.randNum = randNum;
        }

        public String getRootPath() {
            return rootPath;
        }

        public void setRootPath(String rootPath) {
            this.rootPath = rootPath;
        }

    }
}
