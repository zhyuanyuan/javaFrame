package com.credithc.sample.core.facade.convert;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.credithc.sample.facade.dto.CreateSampleReq;
import com.credithc.sample.facade.dto.CreateSampleRes;
import com.credithc.sample.facade.dto.DeleteSampleReq;
import com.credithc.sample.facade.dto.DeleteSampleRes;
import com.credithc.sample.facade.dto.ModifySampleReq;
import com.credithc.sample.facade.dto.ModifySampleRes;
import com.credithc.sample.facade.dto.QuerySampleBean;
import com.credithc.sample.facade.dto.QuerySampleReq;
import com.credithc.sample.facade.dto.QuerySampleRes;
import com.credithc.sample.service.dto.CreateSampleReqTO;
import com.credithc.sample.service.dto.CreateSampleResTO;
import com.credithc.sample.service.dto.DeleteSampleReqTO;
import com.credithc.sample.service.dto.DeleteSampleResTO;
import com.credithc.sample.service.dto.ModifySampleReqTO;
import com.credithc.sample.service.dto.ModifySampleResTO;
import com.credithc.sample.service.dto.QuerySampleBeanTO;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;

public class SampleTOConvertor {
	
	public static CreateSampleReqTO convertCreateSampleReqTO(CreateSampleReq req){
		CreateSampleReqTO to = new CreateSampleReqTO();
		BeanUtils.copyProperties(req, to);
		return to;
	}
	
	public static CreateSampleRes convertCreateSampleRes(CreateSampleResTO res){
		CreateSampleRes to = new CreateSampleRes();
		BeanUtils.copyProperties(res, to);
		return to;
	}
	
	public static ModifySampleReqTO convertModifySampleReqTO(ModifySampleReq req){
		ModifySampleReqTO to = new ModifySampleReqTO();
		BeanUtils.copyProperties(req, to);
		return to;
	}
	
	public static ModifySampleRes convertModifySampleRes(ModifySampleResTO res){
		ModifySampleRes to = new ModifySampleRes();
		BeanUtils.copyProperties(res, to);
		return to;
	}
	
	public static DeleteSampleReqTO convertDeleteSampleReqTO(DeleteSampleReq req){
		DeleteSampleReqTO to = new DeleteSampleReqTO();
		BeanUtils.copyProperties(req, to);
		return to;
	}
	
	public static DeleteSampleReqTO convertDeleteSampleReqTO(String id) {
		DeleteSampleReqTO to = new DeleteSampleReqTO();
		to.setId(id);
		return to;
	}
	
	public static DeleteSampleRes convertDeleteSampleRes(DeleteSampleResTO res){
		DeleteSampleRes to = new DeleteSampleRes();
		BeanUtils.copyProperties(res, to);
		return to;
	}
	
	public static QuerySampleReqTO convertQuerySampleReqTO(QuerySampleReq req){
		QuerySampleReqTO to = new QuerySampleReqTO();
		BeanUtils.copyProperties(req, to);
		return to;
	}
	
	public static QuerySampleReqTO convertQuerySampleReqTO(String id) {
		QuerySampleReqTO to = new QuerySampleReqTO();
		to.setId(id);
		return to;
	}
	
	public static QuerySampleRes convertQuerySampleRes(QuerySampleResTO res){
		QuerySampleRes to = new QuerySampleRes();
		if(CollectionUtils.isNotEmpty(res.getResultList())){
			for(QuerySampleBeanTO e : res.getResultList()){
				QuerySampleBean e2 = new QuerySampleBean();
				BeanUtils.copyProperties(e, e2);
				to.getResultList().add(e2);
			}
		}
		return to;
	}

}
