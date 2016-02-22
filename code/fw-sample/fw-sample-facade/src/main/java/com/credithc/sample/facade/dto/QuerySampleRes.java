package com.credithc.sample.facade.dto;

import java.util.ArrayList;
import java.util.List;

import com.credithc.common.facade.AbsRes;

public class QuerySampleRes extends AbsRes {
	
	private List<QuerySampleBean> resultList = new ArrayList<QuerySampleBean>();

	private static final long serialVersionUID = 1L;
	
	public List<QuerySampleBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<QuerySampleBean> resultList) {
		this.resultList = resultList;
	}

}
