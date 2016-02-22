package com.credithc.cas.service;

import java.util.List;

import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.cas.service.dto.CasUserResTO;
import com.credithc.cas.service.dto.QueryCasUserReqTO;
import com.credithc.cas.service.dto.QueryCasUserResTO;
import com.credithc.cas.service.dto.UpdatePasswordReqTO;
import com.credithc.common.exception.BizException;

public interface CasUserService {
	
	CasUserResTO createUser(CasUserReqTO req) throws BizException;
	
	CasUserResTO modifyUser(CasUserReqTO req) throws BizException;
	
	CasUserResTO deleteUser(CasUserReqTO req) throws BizException;
	
	QueryCasUserResTO queryUser(QueryCasUserReqTO req) throws BizException;
	
	CasUserDO query(QueryCasUserReqTO req) throws BizException;
	
	CasUserResTO checkPasswordByUserName(CasUserReqTO req) throws BizException;
	
	CasUserResTO updatePassword(UpdatePasswordReqTO req) throws BizException;
	
	List<CasUserDO> queryAll() throws BizException;
	
}
