package com.credithc.common.spec;

import java.util.Set;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.credithc.cache.util.redis.RedisTemplate;
import com.credithc.common.log.LogableDTO;
import com.credithc.common.log.LoggerHelper;
import com.credithc.common.message.MessageHelper;
import com.credithc.common.tx.ProgrammingTxHelper;
import com.credithc.common.tx.TransactionInfo;

public abstract class AbsBusiSpec {
	
	protected ProgrammingTxHelper programmingTxHelper;
	
	protected MessageHelper maessageHelper;
	
	protected RedisTemplate redisTemplate;
	
	protected LoggerHelper logger = new LoggerHelper(getClass());
	
	public TransactionInfo beginTransaction(){
		return programmingTxHelper.beginTransaction();
	}
	
	public void commitTransaction(TransactionInfo tts) {
		programmingTxHelper.commitTransaction(tts);
	}

	public void commitAndBeginTransaction(TransactionInfo tts) {
		programmingTxHelper.commitAndBeginTransaction(tts);
	}

	public void rollbackTransaction(TransactionInfo tts) {
		programmingTxHelper.rollbackTransaction(tts);
	}

	public  void rollbackAndBeginTransaction(TransactionInfo tts) {
		programmingTxHelper.rollbackAndBeginTransaction(tts);
	}

	public void setRollBackupOnlyTransaction(TransactionInfo tts) {
		programmingTxHelper.setRollBackupOnlyTransaction(tts);
	}
	
	public void debug(LogableDTO obj, String str){
		logger.debug(obj, str);
	}
	
	public void debug(LogableDTO obj, String format, Object... arguments){
		logger.debug(obj, format, arguments);
	}
	
	public void debug(LogableDTO obj, String str, Throwable t){
		logger.debug(obj, str, t);
	}
	
	public void info(LogableDTO obj, String str){
		logger.info(obj, str);
	}
	
	public void info(LogableDTO obj, String format, Object... arguments){
		logger.info(obj, format, arguments);
	}
	
	public void info(LogableDTO obj, String str, Throwable t){
		logger.info(obj, str, t);
	}
	
	public void warn(LogableDTO obj, String str){
		logger.warn(obj, str);
	}
	
	public void warn(LogableDTO obj, String format, Object... arguments){
		logger.warn(obj, format, arguments);
	}
	
	public void warn(LogableDTO obj, String str, Throwable t){
		logger.warn(obj, str, t);
	}
	
	public void error(LogableDTO obj, String str){
		logger.error(obj, str);
	}
	
	public void error(LogableDTO obj, String format, Object... arguments){
		logger.error(obj, format, arguments);
	}
	
	public void error(LogableDTO obj, String str, Throwable t){
		logger.error(obj, str, t);
	}
	
	public void sendMessage(String exchangeId, String routeKey, Object message){
		maessageHelper.sendMessage(exchangeId, routeKey, message);
	}
	
	public String redisSetValue(String key, String value) {
		return redisTemplate.setValue(key, value);
	}

	public String redisGetValue(String key) {
		return redisTemplate.getValue(key);
	}

	public Long redisRemoveValue(String key) {
		return redisTemplate.removeValue(key);
	}

	public Long redisExpire(String key, int seconds) {
		return redisTemplate.expire(key, seconds);
	}
	
	public Long redisSetValueWithExpire(String key, String value ,int seconds) {
		redisSetValue(key, value);
		return redisExpire(key, seconds);
	}

	public Long redisTtl(String key) {
		return redisTemplate.ttl(key);
	}

	public boolean redisExists(String key) {
		return redisTemplate.exists(key);
	}

	public Set<String> redisKeys(String kayPattern) {
		return redisTemplate.keys(kayPattern);
	}

	public long redisRemoveValues(String keyPattern) {
		return redisTemplate.removeValues(keyPattern);
	}

	public String redisLpop(String list) {
		return redisTemplate.lpop(list);
	}

	public String redisLpeek(String list) {
		return redisTemplate.lpeek(list);
	}

	public void redisRpush(String list, String value) {
		redisTemplate.rpush(list, value);
	}

	public String redisBlpop(int timeout, String value) {
		return redisTemplate.blpop(timeout, value);
	}

	public String redisGetSet(String key, String value) {
		return redisTemplate.getSet(key, value);
	}

	public void redisCloseConnections(Set<Long> consumerThreadIds) {
		redisTemplate.closeConnections(consumerThreadIds);
	}

	public void redisFlushDb() {
		redisTemplate.flushDb();
	}

	@Autowired
	public void setTxManager(DataSourceTransactionManager txManager) {
		programmingTxHelper = new ProgrammingTxHelper(txManager);
	}
	
	@Autowired
	public void setAmqpTemplate(AmqpTemplate amqpTemplate){
		maessageHelper = new MessageHelper(amqpTemplate);
	}

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
