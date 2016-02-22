package com.credithc.cas.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.credithc.common.service.AbsResTO;

public class QueryCasUserResTO extends AbsResTO {

	private static final long serialVersionUID = 1L;
	
	private List<QueryCasUserBeanTO> resultList = new ArrayList<QueryCasUserBeanTO>();
	
	public List<QueryCasUserBeanTO> getResultList() {
		return resultList;
	}

	public void setResultList(List<QueryCasUserBeanTO> resultList) {
		this.resultList = resultList;
	}

}