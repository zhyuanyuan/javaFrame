package com.credithc.common.dao;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.credithc.common.exception.SysException;
import com.credithc.common.spec.AbsBusiSpec;
import com.credithc.common.tx.TransactionInfo;
import com.credithc.common.util.ReflectUtil;


@Repository
public abstract class AbsBaseDao<T> extends AbsBusiSpec implements BaseDao<T> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    private Class<T> entityClass;
    protected String namespace;
    
    @Autowired
    protected SqlSessionTemplate sqlSession;
    
    @Autowired
	protected SqlSessionTemplate batchSqlSession;
    
    protected String insert;
    protected String update;
    protected String delete;
    protected String count;
    protected String query;
    protected String queryIterator;
    protected String queryForLock;
    
    public AbsBaseDao(){
    	initialize();
    }
    
    private void initialize(){
        entityClass = ReflectUtil.reflectParameterizedType(getClass());
        namespace = entityClass != null ? entityClass.getName() : "";
        
        initSqlName();
    }
    
    protected void initSqlName()
    {
        this.insert = MessageFormat.format("{0}.insert", new Object[] { this.namespace });
        this.update = MessageFormat.format("{0}.update", new Object[] { this.namespace });
        this.delete = MessageFormat.format("{0}.delete", new Object[] { this.namespace });
        this.count = MessageFormat.format("{0}.count", new Object[] { this.namespace });
        this.query = MessageFormat.format("{0}.query", new Object[] { this.namespace });
        this.queryIterator = MessageFormat.format("{0}.queryIterator", new Object[] { this.namespace });
        this.queryForLock = MessageFormat.format("{0}.queryForLock", new Object[] { this.namespace });
    }

    @Override
    public long insert(T entity) {
    	if(entity == null){
    		return 0;
    	}
        return sqlSession.insert(this.insert, entity);
    }
    
    @Override
    public long batchInsert(List<T> entities){
    	if(CollectionUtils.isEmpty(entities)){
            return 0;
        }
    	
        int rows = 0;
        
        TransactionInfo  ts = null;
        
        try {
        	ts = beginTransaction();
			for(T entity : entities){
			    int effected = batchSqlSession.insert(this.insert, entity);
			    rows += effected;
			}
			commitTransaction(ts);
		} catch (Exception e) {
			if(ts != null){
				rollbackTransaction(ts);
				rows = 0;
			}
			throw new SysException("batch insert error!", e);
		}
        
        return rows;
    }
    
    @Override
    public long update(T condition){
    	if(condition == null){
    		return 0;
    	}
        return sqlSession.update(this.update, condition);
    }
    
    @Override
    public long batchUpdate(List<T> conditions){
    	if(CollectionUtils.isEmpty(conditions)){
            return 0;
        }
    	
        int rows = 0;
        
        TransactionInfo  ts = null;
        
        try {
        	ts = beginTransaction();
	        for(T condition : conditions){
	            int effected = batchSqlSession.update(this.update, condition);
	            rows += effected;
	        }
	        commitTransaction(ts);
        } catch (Exception e) {
			if(ts != null){
				rollbackTransaction(ts);
				rows = 0;
			}
			throw new SysException("batch update error!", e);
		}
        
        return rows;
    }

    @Override
    public long delete(T condition) {
    	if(condition == null){
    		return 0;
    	}
        return sqlSession.delete(this.delete, condition);
    }
    
    @Override
    public long count(T condition) {
    	if(condition == null){
    		return 0;
    	}
        return ((Long)sqlSession.selectOne(this.count, condition)).longValue();
    }

    @Override
    public List<T> query(T condition) {
        return sqlSession.selectList(this.query, condition);
    }
    
    @Override
    public T queryOne(T condition){
    	return sqlSession.selectOne(this.query, condition);
    }
    
    @Override
    public void query(T condition, ResultHandler rh){
    	sqlSession.select(this.queryIterator, condition, rh);
    }

    @Override
    public List<T> query(T condition, int offSet, int maxRow) {
        return sqlSession.selectList(this.query, condition, new RowBounds(offSet, maxRow));
    }
    
    @Override
    public void query(T condition, int offSet, int maxRow, ResultHandler rh) {
        sqlSession.select(this.queryIterator, condition, new RowBounds(offSet, maxRow), rh);
    }
    
    @Override
    public List<T> queryForLock(T condition){
    	return sqlSession.selectList(this.queryForLock, condition);
    }

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void setBatchSqlSession(SqlSessionTemplate batchSqlSession) {
		this.batchSqlSession = batchSqlSession;
	}

}
