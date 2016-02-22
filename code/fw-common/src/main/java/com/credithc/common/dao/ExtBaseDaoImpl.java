package com.credithc.common.dao;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.common.annotation.FW_TABLE;
import com.credithc.common.exception.SysException;
import com.credithc.common.util.ReflectUtil;

/**
 * 单表增删改查
 * 注：暂未提供update字段为null的实现
 * @author java.peng
 * @param <T>
 */
public class ExtBaseDaoImpl<T extends AbsEntity> extends AbsBaseDao<T> {

	protected String queryExt;
	protected String insertExt;
	protected String updateExt;
	protected String deleteExt;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void initSqlName() {
		super.initSqlName();
        this.queryExt = MessageFormat.format("{0}.query", new Object[] { "com.credithc.common.dao.ExtBaseDaoImpl" });
        this.insertExt = MessageFormat.format("{0}.insert", new Object[] { "com.credithc.common.dao.ExtBaseDaoImpl" });
        this.updateExt = MessageFormat.format("{0}.update", new Object[] { "com.credithc.common.dao.ExtBaseDaoImpl" });
        this.deleteExt = MessageFormat.format("{0}.delete", new Object[] { "com.credithc.common.dao.ExtBaseDaoImpl" });
	}
	

	@Override
	public long delete(T condition) {
		if(condition == null) return 0;
		Map<String, Object> map = resolveToForDelete(condition);
		String sql = "delete from " + getTableName(condition) + map.get("where");
		map.put("sql", sql);
		logger.info("delete sql: " + sql);
		return sqlSession.delete(this.deleteExt, map);
	}




	@Override
	public long insert(T entity) {
		if(entity == null) return 0;
		Map<String, Object> map = resolveToForInsert(entity);
		String sql = "insert into " + getTableName(entity) + "("
					 + map.get("columns") + ") values("+map.get("conditions")+")";
		map.put("sql", sql);
		logger.info("insert sql: " + sql);
		return sqlSession.insert(this.insertExt, map);
	}




	@Override
	public long update(T condition) {
		if(condition == null) return 0;
		Map<String, Object> map = resolveToForUpdate(condition);
		String sql = "update " + getTableName(condition) + " set " + map.get("conditions") + map.get("where");
		map.put("sql", sql);
		logger.info("update sql: " + sql);
		return sqlSession.update(this.updateExt, map);
	}



	@Override
	public List<T> query(T condition) {
    	
    	Map<String, Object> map = resolveToForSelect(condition);
    	
		String sql = "select " + map.get("columns") + " from " + getTableName(condition);
    	if(!"".equals(map.get("conditions"))) sql += " where " + map.get("conditions") ;
    	map.put("sql", sql);
		logger.info("query sql: " + sql);
    	List<Map<String, Object>> sourceList = sqlSession.selectList(this.queryExt, map);
    	List<T> targetList = new ArrayList<T>();
    	for(Map<String, Object> source : sourceList){
			try {
				@SuppressWarnings("unchecked")
				T target = (T) condition.getClass().newInstance();
				ReflectUtil.map2Bean(source, target);
	    		targetList.add(target);
			}catch (NoSuchFieldException | SecurityException | IllegalArgumentException | InstantiationException  | IllegalAccessException e) {
				throw new SysException("exec queryExt error!" , e);				
			} 
    	}
    	return targetList;
    	
	}

	
	/**
	 * 查询返回List<Map>
	 * @param condition
	 * @return
	 */
    public List<Map<String, Object>> queryRM(T condition){
    	Map<String, Object> map = resolveToForSelect(condition);
		String sql = "select " + map.get("columns") + " from " + getTableName(condition);
    	if(!"".equals(map.get("conditions"))) sql += " where " + map.get("conditions") ;
    	map.put("sql", sql);
		logger.info("select rm sql: " + sql);
    	return sqlSession.selectList(this.queryExt, map);
    }
	
    
    /**
     * 返回to对应数据库表名
     * @param to
     * @return
     */
	private  String getTableName(AbsEntity to){
		if(to.getClass().isAnnotationPresent(FW_TABLE.class)){
			FW_TABLE ft = to.getClass().getAnnotation(FW_TABLE.class);
			return ft.value();
		}else{
			throw new RuntimeException("数据库表映射类需加上FW_TABLE注解，value值为表名!");
		}
	}
	
    /**
     * 
     * @param condition
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	private  Map<String, Object> resolveToForSelect(AbsEntity condition) {
		Field[] fields = condition.getClass().getDeclaredFields();
		StringBuffer strColumn = new StringBuffer("");
		StringBuffer strCondition = new StringBuffer("");
		Map<String, Object> map = new HashMap<String, Object>();
		for(Field field : fields){
			if("serialVersionUID".equals(field.getName())) continue;
			field.setAccessible(true);
			strColumn.append(field.getName()).append(",");
			try {
				if(field.get(condition) != null)
				strCondition.append(field.getName()).append("=").append(genCondition(field)).append(" and ");
				map.put(field.getName(), field.get(condition));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SysException("DO解析异常!select " , e);	
			}
		}
		map.put("columns", strColumn.toString().substring(0, strColumn.toString().lastIndexOf(",")));
		map.put("conditions", "".equals(strCondition.toString()) ? "" : strCondition.toString().substring(0, strCondition.toString().lastIndexOf("and")));
		return map;
	}
	
	/**
	 * 
	 * @param condition
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private  Map<String, Object> resolveToForInsert(AbsEntity condition) {
		Field[] fields = condition.getClass().getDeclaredFields();
		StringBuffer strColumn = new StringBuffer("");
		StringBuffer strCondition = new StringBuffer("");
		Map<String, Object> map = new HashMap<String, Object>();
		for(Field field : fields){
			if("serialVersionUID".equals(field.getName())) continue;
			field.setAccessible(true);
			try {
				if(field.get(condition) != null){
					strColumn.append(field.getName()).append(",");
					strCondition.append(genCondition(field)).append(",");
				}
				map.put(field.getName(), field.get(condition));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SysException("DO解析异常! insert" , e);	
			}
		}
		map.put("columns", strColumn.toString().substring(0, strColumn.toString().lastIndexOf(",")));
		map.put("conditions", "".equals(strCondition.toString()) ? "" : strCondition.toString().substring(0, strCondition.toString().lastIndexOf(",")));
		return map;
	}
	
	/**
	 * 
	 * @param condition
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private  Map<String, Object> resolveToForUpdate(AbsEntity condition) {
		Field[] fields = condition.getClass().getDeclaredFields();
		StringBuffer strColumn = new StringBuffer("");
		StringBuffer strCondition = new StringBuffer("");
		Map<String, Object> map = new HashMap<String, Object>();
		String where = " where id = ";
		for(Field field : fields){
			if("serialVersionUID".equals(field.getName())) continue;
			field.setAccessible(true);
			strColumn.append(field.getName()).append(",");
			try {
				if("id".equals(field.getName())){
					where += "'"+field.get(condition).toString()+"'";					
				}else{
					if(field.get(condition) != null)
						strCondition.append(field.getName()).append("=").append(genCondition(field)).append(",");
				}
				map.put(field.getName(), field.get(condition));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SysException("DO解析异常! update" , e);	
			}
		}
		map.put("columns", strColumn.toString().substring(0, strColumn.toString().lastIndexOf(",")));
		map.put("conditions", "".equals(strCondition.toString()) ? "" : strCondition.toString().substring(0, strCondition.toString().lastIndexOf(",")));
		map.put("where", where);
		return map;
	}
	
	/**
	 * 
	 * @param condition
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private  Map<String, Object> resolveToForDelete(AbsEntity condition) {
		Field[] fields = condition.getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();
		String where = " where id = ";
		for(Field field : fields){
			if("serialVersionUID".equals(field.getName())) continue;
			field.setAccessible(true);
			try {
				if("id".equals(field.getName())){
					where += "'"+field.get(condition).toString()+"'";
					break;
				}
				map.put(field.getName(), field.get(condition));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SysException("DO解析异常! delete" , e);	
			}
		}
		map.put("where", where);
		return map;
	}

	/**
	 * 生成sql条件参数
	 * @param field
	 * @return
	 */
	private  String genCondition(Field field) {
		StringBuilder value = new StringBuilder("#{");
		value.append(field.getName());
//		value.append(field.getName()).append(",jdbcType=").append(JdbcType.forCode(jdbcTypeMap.get(key)).toString());
		value.append("}");
		return value.toString();
	}
	
//	private static final Map<String, Integer> jdbcTypeMap = new HashMap<String, Integer>();
//	static{
//		jdbcTypeMap.put("java.lang.String", java.sql.Types.VARCHAR);
//		jdbcTypeMap.put("java.math.BigDecimal", java.sql.Types.DECIMAL);
//		jdbcTypeMap.put("java.lang.Boolean", java.sql.Types.BIT);
//		jdbcTypeMap.put("java.lang.Short", java.sql.Types.NUMERIC);
//		jdbcTypeMap.put("java.lang.Integer", java.sql.Types.NUMERIC);
//		jdbcTypeMap.put("java.lang.Long", java.sql.Types.NUMERIC);
//		jdbcTypeMap.put("java.lang.Double", java.sql.Types.NUMERIC);
//		jdbcTypeMap.put("java.sql.Date", java.sql.Types.DATE);
//		jdbcTypeMap.put("java.sql.Time", java.sql.Types.TIME);
//		jdbcTypeMap.put("java.sql.Timestamp", java.sql.Types.TIMESTAMP);
//	}
	
}
