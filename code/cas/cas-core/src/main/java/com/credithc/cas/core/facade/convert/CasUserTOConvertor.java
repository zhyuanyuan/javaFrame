package com.credithc.cas.core.facade.convert;

import org.springframework.beans.BeanUtils;

import com.credithc.cas.facade.dto.CasUserReq;
import com.credithc.cas.service.dto.CasUserReqTO;

public class CasUserTOConvertor {
	
	public static CasUserReqTO convertCreateCasUserTO(CasUserReq req){
		CasUserReqTO entity = new CasUserReqTO();
		BeanUtils.copyProperties(req, entity);
		return entity;
	}

}
