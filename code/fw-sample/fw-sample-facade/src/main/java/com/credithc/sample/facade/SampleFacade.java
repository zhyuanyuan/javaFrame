package com.credithc.sample.facade;

import com.credithc.sample.facade.dto.CreateSampleReq;
import com.credithc.sample.facade.dto.CreateSampleRes;
import com.credithc.sample.facade.dto.DeleteSampleReq;
import com.credithc.sample.facade.dto.DeleteSampleRes;
import com.credithc.sample.facade.dto.ModifySampleReq;
import com.credithc.sample.facade.dto.ModifySampleRes;
import com.credithc.sample.facade.dto.QuerySampleReq;
import com.credithc.sample.facade.dto.QuerySampleRes;

public interface SampleFacade {
	
	CreateSampleRes createSample(CreateSampleReq req);
	
	ModifySampleRes modifySample(ModifySampleReq req);
	
	DeleteSampleRes deleteSample(DeleteSampleReq req);
	
	QuerySampleRes querySample(QuerySampleReq req);
	
}
