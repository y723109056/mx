package com.mx.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mx.util.FileUtil;
import com.mx.util.ServerRealPathUtil;
import com.mx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlScriptFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected FilterConfig  filterConfig = null;
	protected static String[] filterStrs = null;
    protected List<String> urlList = null;
    private  Map<String,String> configMap;

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    private final String defaultSqlScriptFilter = "false";
    private String  propertyfile;

    public SqlScriptFilter(){
        String  path = ServerRealPathUtil.getServerRealPath();
        this.propertyfile = FileUtil.contact(path, "WEB-INF/classes/globalConfig.properties");
        configMap = new HashMap<String,String>();
        configMap.put("sqlScriptFilter", defaultSqlScriptFilter);
        try {
            loadPropertyMap(configMap);
        }catch (Exception e){
            log.error("SqlScriptFilter error !",e);
        }

    }
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
        urlList = new ArrayList<String>();
        urlList.add("/role/addOrUpdateRole.html");
		filterStrs = ("grant|exec|execute|create|drop|alert"
					+"|insert|update|delete").split("\\|");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest myrequest = (HttpServletRequest) request;
        HttpServletResponse myresponse = (HttpServletResponse) response;
        String requestUrl = myrequest.getRequestURI();
        Map<?,?> map = myrequest.getParameterMap();
        try {
            String str = "";
            //对所有参数进行循环
            for (Object key : map.keySet()) {
                //得到参数名
                String name = (String) key;
                //得到这个参数的所有值
                String[] value = myrequest.getParameterValues(name);
                for (int i = 0; i < value.length; i++) {
                	str = str + value[i];
                }
            }
            if (validate(str) && !urlList.contains(requestUrl)) {
            	if(isAjax(myrequest)){
            		render("unsafe",myresponse);
            	}else{
            		myresponse.sendRedirect("../login/hasUnsafeChar.html");
            	}
            } else {
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            log.error("ChecksqlFilter error:" + e.getMessage());
        }
	}
	
	/**
     * 效验  
     * @param str
     * @return
     */
    protected boolean validate(String str) {
    	if(StringUtil.isNotEmpty(str)){
	        str = str.toLowerCase();//统一转为小写  
	        String[] badStrs = filterStrs;
            if(this.configMap.get("sqlScriptFilter").equals("false")){
                return false;
            }else{
                for (int i = 0; i < badStrs.length; i++) {
                    if (str.indexOf(badStrs[i]) >= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected void render(String text, HttpServletResponse response) {
        try {
            response.setContentType("text/plain;charset=UTF-8");
            ((ServletRequest) response).setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
        } catch (IOException e) {
        	
        }
    }
    
    /**
     * 判断提交方式是否是ajax
     */
    public boolean isAjax(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
	
	public void destroy() {
		this.filterConfig = null;
	}


    private void loadPropertyMap(Map<String,String> map) throws FileNotFoundException, IOException {
        Properties config = new Properties();
        config.load(new FileInputStream(propertyfile));
        String sqlScriptFilter = config.getProperty("sqlScriptFilter",defaultSqlScriptFilter);
        map.put("sqlScriptFilter", sqlScriptFilter);
    }
}
