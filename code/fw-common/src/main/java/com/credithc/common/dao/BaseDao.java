package com.credithc.common.dao;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

public interface BaseDao<T> {
	
    public long insert(T entity);
    
    public long batchInsert(List<T> entities);
    
    public long update(T condition);
    
    public long batchUpdate(List<T> condition);
    
    public long delete(T condition);
    
    public long count(T condition);
    
    public List<T> query(T condition);
    
    public T queryOne(T condition);
    
    public List<T> queryForLock(T condition);
    
    public void query(T condition, ResultHandler rh);
    
    public List<T> query(T condition, int offSet, int maxRow);
    
    public void query(T condition, int offSet, int maxRow, ResultHandler rh);

}
