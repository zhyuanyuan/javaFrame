package com.credithc.common.tx;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class ProgrammingTxHelper{

	private DataSourceTransactionManager txManager;
	
	public ProgrammingTxHelper(DataSourceTransactionManager txManager){
		this.txManager = txManager;
	}
	
	public TransactionInfo beginTransaction(){
		return beginTransaction(null);
	}
	
	private TransactionInfo beginTransaction(TransactionInfo tts) {
		TransactionInfo newTts = (tts == null ? new TransactionInfo() : tts);
		
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        newTts.setTs(txManager.getTransaction(defaultTransactionDefinition));
        
        return newTts;
	}

	public void commitTransaction(TransactionInfo tts) {
        if (tts != null) {
            txManager.commit(tts.getTs());
        }
	}

	public void commitAndBeginTransaction(TransactionInfo tts) {
        commitTransaction(tts);
        
        TransactionInfo newTts = beginTransaction(tts);
        
        tts.setTs(newTts.getTs());
	}

	public void rollbackTransaction(TransactionInfo tts) {
        if (tts != null) {
            txManager.rollback(tts.getTs());
        }
	}

	public  void rollbackAndBeginTransaction(TransactionInfo tts) {
        rollbackTransaction(tts);
        
        TransactionInfo newTts = beginTransaction(tts);
        
        tts.setTs(newTts.getTs());
	}

	public void setRollBackupOnlyTransaction(TransactionInfo tts) {
		if (tts != null) {
            tts.getTs().setRollbackOnly();
        }
	}
	
}
