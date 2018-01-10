package com.icaopan.util.page;

import com.icaopan.util.MapUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class SpringMybatisPageInterceptor implements Interceptor {

    /**
     * 拦截器
     */
    public Object intercept(Invocation invocation) throws Throwable {
        //拦截执行的Handler
        final RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //获取分页拦截器必要的参数
        final StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        //获取执行的SQL
        final BoundSql boundSql = delegate.getBoundSql();
        //获取执行的参数
        final Object obj = boundSql.getParameterObject();
        if (obj instanceof Map) {
            Map<String, Object> paraMap = (Map<String, Object>) obj;
            if (paraMap.containsKey("page")) {
                Object pageObj = paraMap.get("page");
                Object paramObj =null;
                if(paraMap.containsKey("params")){
                	paramObj=paraMap.get("params");
                }
                Page page = (Page) pageObj;
                if (page != null) {
                	if(paramObj!=null){
                		page.setParams(MapUtil.beanToMap(paramObj));
                	}
                    //获取StateMent
                    final MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                    //判断参数是否有Page参数，如果有则按照
                    final String sql = boundSql.getSql();
                    //升级sECO
                    page.setsEcho(page.getsEcho() + 1);
                    //计算总的条目数
                    final String countSQL = getMysqlCountSql(new StringBuffer(sql));
                    int totalCount = getCounts(mappedStatement, countSQL, boundSql, page);
                    //获取分页后的结果
                    final String pageSql = this.getMysqlPageSql(page, new StringBuffer(sql));
                    ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
                    page.setiTotalRecords(totalCount);
                    page.setiTotalDisplayRecords(totalCount);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 定义使用的拦截器
     */
    public Object plugin(Object obj) {
        return Plugin.wrap(obj, this);
    }

    /**
     * 获取属性信息
     */
    public void setProperties(Properties properties) {

    }

    /**
     * 组装分页SQL
     *
     * @param page      分页javaBean
     * @param sqlBuffer 组装好的分页SQL
     * @return
     */
    private String getMysqlPageSql(Page page, StringBuffer sqlBuffer) {
        if (page.getiDisplayLength() < 0) {
            return sqlBuffer.toString();
        }
        sqlBuffer.append(" limit ").append(page.getLimitStart()).append(",").append(page.getLimitend());
        return sqlBuffer.toString();
    }

    /**
     * 组装查询数量的SQL
     *
     * @param sqlBuffer 组装好的分页SQL
     * @return
     */
    private String getMysqlCountSql(StringBuffer sqlBuffer) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select count(*) from (");
        buffer.append(sqlBuffer);
        buffer.append(")  as total");
        return buffer.toString();
    }

    /**
     * 获取总页数
     *
     * @param mappedStatement
     * @param countSQL
     * @return
     */
    private int getCounts(MappedStatement mappedStatement, String countSQL, BoundSql boundSql, Page page) {

        final ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, boundSql);
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement countStmt = null;
        int totpage = 0;
        try {
            //获取连接
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            //预处理SQL
            countStmt = connection.prepareStatement(countSQL);
            //给预处理SQL 赋值 
            parameterHandler.setParameters(countStmt);
            //查询预处理的SQL
            rs = countStmt.executeQuery();
            if (rs.next()) {
                totpage = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭释放资源
            try {
                if (rs != null) rs.close();
                if (countStmt != null) countStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totpage;
    }
}
