package com.credithc.common.tx;

import org.springframework.transaction.TransactionStatus;

public class TransactionInfo {
	
	private TransactionStatus ts;

	public TransactionStatus getTs() {
		return ts;
	}

	public void setTs(TransactionStatus ts) {
		this.ts = ts;
	}
}
