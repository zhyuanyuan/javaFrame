package com.credithc.sample.service;

import com.credithc.common.exception.BizException;
import com.credithc.sample.service.dto.CreateSampleReqTO;
import com.credithc.sample.service.dto.CreateSampleResTO;
import com.credithc.sample.service.dto.DeleteSampleReqTO;
import com.credithc.sample.service.dto.DeleteSampleResTO;
import com.credithc.sample.service.dto.ModifySampleReqTO;
import com.credithc.sample.service.dto.ModifySampleResTO;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;

public interface SampleService {
	
	CreateSampleResTO createSample(CreateSampleReqTO req) throws BizException;
	
	ModifySampleResTO modifySample(ModifySampleReqTO req) throws BizException;
	
	DeleteSampleResTO deleteSample(DeleteSampleReqTO req) throws BizException;
	
	QuerySampleResTO querySample(QuerySampleReqTO req) throws BizException;
	
}
