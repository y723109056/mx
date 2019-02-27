
package com.mx.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class ChecksqlFilter implements Filter {

    private Logger          log          = LoggerFactory.getLogger(this.getClass());

    protected FilterConfig  filterConfig = null;
    protected static String tmp          = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        tmp = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
                   +

                   "char|declare|sitename|net user|xp_cmdshell|;|or|+|like'|and|exec|execute|insert|create|drop|"
                   +

                   "table|from|grant|use|group_concat|column_name|"
                   +

                   "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
                   +

                   "chr|mid|master|truncate|char|declare|or|;|+|like|//|%|#";//过滤掉的sql关键字，可以手动添加  

    }

    @SuppressWarnings("rawtypes")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {

        HttpServletRequest myrequest = (HttpServletRequest) request;
        HttpServletResponse myresponse = (HttpServletResponse) response;
        java.util.Enumeration enu = myrequest.getParameterNames();
        try {
            String sql = "";
            //对所有参数进行循环
            while (enu.hasMoreElements()) {
                //得到参数名
                String name = (String) enu.nextElement();
                //得到这个参数的所有值
                String[] value = myrequest.getParameterValues(name);
                for (int i = 0; i < value.length; i++) {
                    sql = sql + value[i];
                }
            }
            if (sqlValidate(sql)) {
                myresponse.sendRedirect("/login/checkSql.html");
 
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
    protected static boolean sqlValidate(String str) {

        str = str.toLowerCase();//统一转为小写  
        String[] badStrs = tmp.split("\\|");

        for (int i = 0; i < badStrs.length; i++) {

            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
