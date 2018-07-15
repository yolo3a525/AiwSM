package com.aiw.base.extend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import com.aiw.base.entity.Page;


@Intercepts({
     @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),  
     @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})  
public class PageInterceptor implements Interceptor {  
  
	
	private static final Logger logger = Logger.getLogger(PageInterceptor.class);
    private static final String SELECT_ID="selectpage";


    //插件运行的代码，它将代替原有的方法
    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        
        if (invocation.getTarget() instanceof StatementHandler) {  
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();  
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);  
            MappedStatement mappedStatement=(MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            
            String selectId=mappedStatement.getId();
            
            if(SELECT_ID.equals(selectId.substring(selectId.lastIndexOf(".")+1).toLowerCase())){
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");  
                
                // 分页参数作为参数对象parameterObject的一个属性  
                String sql = boundSql.getSql();
                Page co=(Page)((Map<?, ?>)(boundSql.getParameterObject())).get("page");
                
                
                // 重写sql  
                String countSql=concatCountSql(sql);
                String pageSql=concatPageSql(sql,co);
                
                logger.info("重写的 count  sql :"+countSql);
                logger.info("重写的 select sql :"+pageSql);
                
                Connection connection = (Connection) invocation.getArgs()[0];  
                
                PreparedStatement countStmt = null;  
                ResultSet rs = null;  
                int totalCount = 0;  
                try { 
                    countStmt = connection.prepareStatement(countSql);  
                    BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,  
                            boundSql.getParameterMappings(), boundSql.getParameterObject());  
                    setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
                    rs = countStmt.executeQuery();  
                    if (rs.next()) {  
                        totalCount = rs.getInt(1);  
                    } 
                    
                } catch (SQLException e) {  
                    logger.info("Ignore this exception"+e);  
                } finally {  
                    try {  
                        rs.close();  
                        countStmt.close();  
                    } catch (SQLException e) {  
                        logger.info("Ignore this exception"+ e);  
                    }  
                }  
                
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);            
              
                //绑定count
                co.setTotalSize(totalCount);
            }
        } 
        
        return invocation.proceed();
    }
    
    /** 
     * 代入参数值 
     * @param ps 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,  
                               Object parameterObject) throws SQLException {  
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);  
        parameterHandler.setParameters(ps);  
    }  
    
    /**
     * 拦截类型StatementHandler 
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
    }
    
    @Override
    public void setProperties(Properties properties) {
        
    }  
    
    
    public String concatCountSql(String sql){
        StringBuffer sb=new StringBuffer("select count(*) from ");
        sql=sql.toLowerCase();
        
//        if(sql.lastIndexOf("order")>sql.lastIndexOf(")")){
//            sb.append(sql.substring(sql.indexOf("from")+4, sql.lastIndexOf("order")));
//        }else{
//            sb.append(sql.substring(sql.indexOf("from")+4));
//        }
        
        if(sql.lastIndexOf("order")>sql.lastIndexOf(")")){
	          
	          sb.append("( ");
	          sb.append(sql.substring(0, sql.lastIndexOf("order")));
	          sb.append(" ) as a999");
	          
	      }else{
	    	  sb.append("( ");
	          sb.append(sql);
	          sb.append(" ) as a999");
	      }
        
       
        
        return sb.toString();
    }
    
    public String concatPageSql(String sql,Page co){
        StringBuffer sb=new StringBuffer();
        sb.append(sql);
        sb.append(" limit ").append((co.getCurrentPage() -1) * co.getPageSize()).append(" , ").append(co.getPageSize());
        return sb.toString();
    }
    
    public void setPageCount(){
        
    }
    
}