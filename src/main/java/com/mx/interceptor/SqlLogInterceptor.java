package com.mx.interceptor;

import com.mx.dao.SqlLogMapper;
import com.mx.entity.SqlLogWithBLOBs;
import com.mx.spring.SpringContext;
import com.mx.util.ApiJsonUtil;
import com.mx.util.ReflectHelper;
import net.sf.json.JSONObject;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author 小米线儿
 * @time 2019/2/24 0024
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
public class SqlLogInterceptor implements Interceptor {

    private Integer userId = null;

    private SqlLogMapper sqlLogMapper;

    private final Logger LOG = LoggerFactory.getLogger(SqlLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            if(invocation.getTarget() instanceof RoutingStatementHandler){
                RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
                BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
                MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                if(parameterObject==null){
                    throw new NullPointerException("parameterObject尚未实例化！");
                }else {
                    sqlLogMapper = (SqlLogMapper)SpringContext.instance().get("sqlLogMapper");
                    JSONObject json = ApiJsonUtil.toJson(parameterObject,"userId");
                    if(json.containsKey("userId")){
                        userId = json.getInt("userId");
                    }

                    String params ="";
                    try {
                        params = JSONObject.fromObject(parameterObject).toString();
                    }catch (Exception e){
                        params = String.valueOf(parameterObject);
                    }

                    String sql = boundSql.getSql();
                    String sqlId = mappedStatement.getId();
                    if(!"com.mx.dao.SqlLogMapper.insertSelective".equals(sqlId)){
                        sqlLogMapper.insertSelective(new SqlLogWithBLOBs(mappedStatement.getId(), (endTime - startTime) + "ms", userId, sql, params));
                        LOG.info("sqlId:"+sqlId+"执行耗时 : [" + (endTime - startTime) + "ms ] ");
                    }
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
