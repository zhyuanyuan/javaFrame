package com.credithc.sample.service.impl;

import java.util.List;

import com.credithc.common.dao.AbsBaseDao;
import com.credithc.common.dao.BaseDao;
import com.credithc.common.exception.BizException;
import com.credithc.common.service.AbsService;
import com.credithc.sample.dao.entity.SampleDO;
import com.credithc.sample.service.SampleService;
import com.credithc.sample.service.convert.SampleDOConvertor;
import com.credithc.sample.service.dto.CreateSampleReqTO;
import com.credithc.sample.service.dto.CreateSampleResTO;
import com.credithc.sample.service.dto.DeleteSampleReqTO;
import com.credithc.sample.service.dto.DeleteSampleResTO;
import com.credithc.sample.service.dto.ModifySampleReqTO;
import com.credithc.sample.service.dto.ModifySampleResTO;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;

public class SampleServiceImpl extends AbsService implements SampleService {
	
	private BaseDao extBaseDao;

	@Override
	public CreateSampleResTO createSample(CreateSampleReqTO req) throws BizException{
		CreateSampleResTO res = new CreateSampleResTO();
		
		SampleDO entity = SampleDOConvertor.convertCreateSampleDO(req);
		extBaseDao.insert(entity);
		
		return res;
	}

	@Override
	public ModifySampleResTO modifySample(ModifySampleReqTO req) throws BizException{
		ModifySampleResTO res = new ModifySampleResTO();
		
		SampleDO condition = SampleDOConvertor.convertModifySampleDO(req);
		extBaseDao.update(condition);
		
		return res;
	}

	@Override
	public DeleteSampleResTO deleteSample(DeleteSampleReqTO req) throws BizException{
		DeleteSampleResTO res = new DeleteSampleResTO();
		
		SampleDO condition = SampleDOConvertor.convertDeleteSampleDO(req);
		extBaseDao.delete(condition);
		
		return res;
	}

	@Override
	public QuerySampleResTO querySample(QuerySampleReqTO req) throws BizException{
		SampleDO condition = SampleDOConvertor.convertQuerySampleDO(req);
		List<SampleDO> dos = extBaseDao.query(condition);
		QuerySampleResTO res = SampleDOConvertor.convertQuerySampleTO(dos);
		
		return res;
	}

	public BaseDao getExtBaseDao() {
		return extBaseDao;
	}

	public void setExtBaseDao(BaseDao extBaseDao) {
		this.extBaseDao = extBaseDao;
	}

}
