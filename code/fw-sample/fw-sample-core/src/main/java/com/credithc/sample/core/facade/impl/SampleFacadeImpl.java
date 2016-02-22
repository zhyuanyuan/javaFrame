package com.credithc.sample.core.facade.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.credithc.common.core.AbsFacade;
import com.credithc.common.exception.BizException;
import com.credithc.common.exception.SysException;
import com.credithc.common.facade.AbsReq;
import com.credithc.common.facade.AbsRes;
import com.credithc.common.log.LogableDTO;
import com.credithc.sample.common.exception.SampleExCode;
import com.credithc.sample.core.facade.convert.SampleTOConvertor;
import com.credithc.sample.facade.SampleFacade;
import com.credithc.sample.facade.dto.CreateSampleReq;
import com.credithc.sample.facade.dto.CreateSampleRes;
import com.credithc.sample.facade.dto.DeleteSampleReq;
import com.credithc.sample.facade.dto.DeleteSampleRes;
import com.credithc.sample.facade.dto.ModifySampleReq;
import com.credithc.sample.facade.dto.ModifySampleRes;
import com.credithc.sample.facade.dto.QuerySampleReq;
import com.credithc.sample.facade.dto.QuerySampleRes;
import com.credithc.sample.service.SampleService;
import com.credithc.sample.service.dto.CreateSampleReqTO;
import com.credithc.sample.service.dto.CreateSampleResTO;
import com.credithc.sample.service.dto.DeleteSampleReqTO;
import com.credithc.sample.service.dto.DeleteSampleResTO;
import com.credithc.sample.service.dto.ModifySampleReqTO;
import com.credithc.sample.service.dto.ModifySampleResTO;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;
import com.credithc.sample.test.mock.facade.MockFacade;
import com.credithc.sample.test.mock.facade.dto.MockReq;
import com.credithc.sample.test.mock.facade.dto.MockRes;

@Path("sample")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class SampleFacadeImpl extends AbsFacade implements SampleFacade {
	
	private SampleService sampleService;
	
	@Autowired
	private MockFacade mockFacade;

	@Override
	@POST
	@Path("create")
	public CreateSampleRes createSample(CreateSampleReq req) {
		try {
			logger.info(req, "createSample");
			CreateSampleReqTO reqTO = SampleTOConvertor.convertCreateSampleReqTO(req);
			CreateSampleResTO resTO = sampleService.createSample(reqTO);
			CreateSampleRes res = SampleTOConvertor.convertCreateSampleRes(resTO);
			return res;
		} catch (Exception e) {
			return handleException(e, CreateSampleRes.class, req);
		}
	}

	@Override
	@POST
	@Path("modify")
	public ModifySampleRes modifySample(ModifySampleReq req) {
		try {
			logger.info(req, "modifySample");
			ModifySampleReqTO reqTO = SampleTOConvertor.convertModifySampleReqTO(req);
			ModifySampleResTO resTO = sampleService.modifySample(reqTO);
			ModifySampleRes res = SampleTOConvertor.convertModifySampleRes(resTO);
			return res;
		} catch (Exception e) {
			return handleException(e, ModifySampleRes.class, req);
		}
	}

	@Override
	@POST
	@Path("delete")
	public DeleteSampleRes deleteSample(DeleteSampleReq req) {
		try {
			logger.info(req, "deleteSample");
			DeleteSampleReqTO reqTO = SampleTOConvertor.convertDeleteSampleReqTO(req);
			DeleteSampleResTO resTO = sampleService.deleteSample(reqTO);
			DeleteSampleRes res = SampleTOConvertor.convertDeleteSampleRes(resTO);
			return res;
		} catch (Exception e) {
			return handleException(e, DeleteSampleRes.class, req);
		}
	}
	
	@Override
	@POST
	@Path("query")
	public QuerySampleRes querySample(QuerySampleReq req) {
		try {
			logger.info(req, "querySample");
			QuerySampleReqTO reqTO = SampleTOConvertor.convertQuerySampleReqTO(req);
			QuerySampleResTO resTO = sampleService.querySample(reqTO);
			QuerySampleRes res = SampleTOConvertor.convertQuerySampleRes(resTO);
			res.setRespMsg(StringUtils.trimToEmpty(res.getRespMsg()) + mockADubboCallForTest(req));
			return res;
		} catch (Exception e) {
			return handleException(e, QuerySampleRes.class, req);
		}
	}
	
	private <T extends AbsRes> T handleException(Exception e, Class<T> clazz, AbsReq req){
		try {
			T res = clazz.newInstance();
			if(e instanceof BizException){
				logger.warn(req, "biz exception", e);
				BizException bizE = (BizException)e;
				res.setRespCode(bizE.getErrorCode());
				res.setRespMsg(bizE.getErrorMsg());
			}else if(e instanceof SysException){
				logger.error(req, "sys exception", e);
				SysException bizE = (SysException)e;
				res.setRespCode(bizE.getErrorCode());
				res.setRespMsg(bizE.getErrorMsg());
			}else{
				logger.error(req, "unknow exception", e);
				res.setRespCode(SampleExCode.SYS_UNKNOWN_ERROR.errorCode);
				res.setRespMsg(SampleExCode.SYS_UNKNOWN_ERROR.errorMsg);
			}
			return res;
		} catch (Exception e1) {
			logger.error(req, "handle exception error[WARN! WARN! WARN!]:", e1);
			return null;
		}
	}
	
	private String mockADubboCallForTest(LogableDTO req){
		MockReq mreq = new MockReq();
		mreq.setSysSeq("888");
		mreq.setBusinessId(req.getBusinessId());
		mreq.setRequestChain(null);
		MockRes mres = mockFacade.mockCall(mreq);
		return mres.getRespMsg();
	}

	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}
	
	public void setMockFacade(MockFacade mockFacade) {
		this.mockFacade = mockFacade;
	}

}
