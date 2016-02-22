package com.credithc.cas.core.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.cas.facade.CasUserFacade;
import com.credithc.cas.facade.dto.CasUserDTO;
import com.credithc.cas.facade.dto.CasUserReq;
import com.credithc.cas.facade.dto.CasUserRes;
import com.credithc.cas.service.CasUserService;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.cas.service.dto.CasUserResTO;
import com.credithc.cas.service.dto.QueryCasUserReqTO;
import com.credithc.common.core.AbsFacade;
import com.credithc.common.exception.BizException;
import com.credithc.common.log.LogableDTO;

public class CasUserFacadeImpl extends AbsFacade implements CasUserFacade {
	
	@Autowired
	private CasUserService casUserService;
	
	@Override
	public CasUserRes createIfNotExist(CasUserReq req) {
		CasUserRes res = new CasUserRes();
		
		CasUserReqTO condition = new CasUserReqTO();
		BeanUtils.copyProperties(req, condition);
		QueryCasUserReqTO condition2 = new QueryCasUserReqTO();
		BeanUtils.copyProperties(req, condition2);
		try {
			CasUserResTO result = new CasUserResTO();
			//查询用户是否存在：存在修改，不存在新增
			CasUserDO user = casUserService.query(condition2);
			if (user == null) {
				result = casUserService.createUser(condition);
			} else {
				result = casUserService.modifyUser(condition);
			}
			
			res.setSuccess(result.isSuccess());
			res.setRespCode(result.getCode());
			res.setRespMsg(result.getMsg());
		} catch (BizException e) {
			res.setSuccess(false);
			res.setRespCode(e.getErrorCode());
			res.setRespMsg(e.getErrorMsg());
		}
		
		return res;
	}

	@Override
	public List<CasUserDTO> queryAll() {
		List<CasUserDTO> r = new ArrayList<CasUserDTO>();
		try {
			List<CasUserDO> result = casUserService.queryAll();
			for (CasUserDO bean : result) {
				CasUserDTO user = new CasUserDTO();
				BeanUtils.copyProperties(bean, user);
				r.add(user);
			}
		} catch (BizException e) {
			logger.error(new LogableDTO() {}, "查询用户列表异常！");
			r = new ArrayList<CasUserDTO>();
		}
		return r;
	}

}
