package com.credithc.sample.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.credithc.common.service.AbsResTO;

public class QuerySampleResTO extends AbsResTO {

	private static final long serialVersionUID = 1L;
	
	private List<QuerySampleBeanTO> resultList = new ArrayList<QuerySampleBeanTO>();

	public List<QuerySampleBeanTO> getResultList() {
		return resultList;
	}

	public void setResultList(List<QuerySampleBeanTO> resultList) {
		this.resultList = resultList;
	}

}
