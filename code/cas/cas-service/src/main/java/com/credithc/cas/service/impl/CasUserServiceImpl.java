package com.credithc.cas.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.credithc.cas.common.utils.PasswordUtils;
import com.credithc.cas.dao.CasUserDao;
import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.cas.service.CasUserService;
import com.credithc.cas.service.convert.CasUserDOConvertor;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.cas.service.dto.CasUserResTO;
import com.credithc.cas.service.dto.QueryCasUserReqTO;
import com.credithc.cas.service.dto.QueryCasUserResTO;
import com.credithc.cas.service.dto.UpdatePasswordReqTO;
import com.credithc.common.exception.BizException;
import com.credithc.common.service.AbsService;

public class CasUserServiceImpl extends AbsService implements CasUserService {
	
	@Autowired
	private CasUserDao casUserDao;

	@Override
	public CasUserResTO createUser(CasUserReqTO req) throws BizException{
		CasUserResTO res = new CasUserResTO();
		//查询用户名是否存在
		CasUserDO condition = new CasUserDO();
		condition.setUserName(req.getUserName());	
		CasUserDO user = casUserDao.queryOne(condition);
		if (user != null) {
			res.setSuccess(false);
			res.setMsg("用户名已存在！");
			return res;
		}
		
		CasUserDO entity = CasUserDOConvertor.convertCasUserDO(req);
		entity.setCompanyId("HC");
		entity.setCreateDate(new Date());
		//密码加密
		if (StringUtils.isNotBlank(entity.getPassword()) && StringUtils.isBlank(entity.getSalt())) {
			List<String> pList = PasswordUtils.encodePassword(entity.getPassword());
			entity.setSalt(pList.get(0));
			entity.setPassword(pList.get(1));
		}
		
		casUserDao.insert(entity);
		res.setSuccess(true);
		return res;
	}

	@Override
	public CasUserResTO modifyUser(CasUserReqTO req) throws BizException{
		CasUserResTO res = new CasUserResTO();
		
		CasUserDO condition = CasUserDOConvertor.convertCasUserDO(req);
		casUserDao.update(condition);
		
		return res;
	}

	@Override
	public CasUserResTO deleteUser(CasUserReqTO req) throws BizException{
		CasUserResTO res = new CasUserResTO();
		
		CasUserDO condition = CasUserDOConvertor.convertCasUserDO(req);
		casUserDao.delete(condition);
		
		return res;
	}

	@Override
	public QueryCasUserResTO queryUser(QueryCasUserReqTO req) throws BizException{
		CasUserDO condition = CasUserDOConvertor.convertQueryCasUserDO(req);
		List<CasUserDO> dos = casUserDao.query(condition);
		QueryCasUserResTO res = CasUserDOConvertor.convertQueryCasUserTO(dos);
		
		return res;
	}

	@Override
	public CasUserDO query(QueryCasUserReqTO req) throws BizException{
		CasUserDO condition = CasUserDOConvertor.convertQueryCasUserDO(req);
		CasUserDO user = casUserDao.queryOne(condition);
		return user;
	}
	
	@Override
	public CasUserResTO checkPasswordByUserName(CasUserReqTO req) throws BizException {
		CasUserResTO res = new CasUserResTO();
		
		CasUserDO condition = new CasUserDO();
		condition.setUserName(req.getUserName());
		
		CasUserDO user = casUserDao.queryOne(condition);
		if (user == null){
			res.setSuccess(false);
			res.setMsg("用户不存在");
		} else if (!PasswordUtils.validatePassword(user.getPassword(), 
				req.getPassword(), user.getSalt())) {
			res.setSuccess(false);
			res.setMsg("用户密码有误");
		} else {
			res.setSuccess(true);
		}
		
		return res;
	}
	
	@Override
	public CasUserResTO updatePassword(UpdatePasswordReqTO req)
			throws BizException {
		CasUserResTO res = new CasUserResTO();
		//验证原密码
		CasUserReqTO checkReq = new CasUserReqTO();
		checkReq.setUserName(req.getUserName());
		checkReq.setPassword(req.getPassword());
		CasUserResTO result = checkPasswordByUserName(checkReq);
		if(result.isSuccess()){
			//查询用户ID
			CasUserDO user = new CasUserDO();
			user.setUserName(req.getUserName());
			user = casUserDao.queryOne(user);
			
			//修改密码
			CasUserDO condition2 = new CasUserDO();
			condition2.setId(user.getId());
			//密码加密
			List<String> pList = PasswordUtils.encodePassword(req.getNewPassword());
			condition2.setSalt(pList.get(0));
			condition2.setPassword(pList.get(1));
			casUserDao.update(condition2);
			
			res.setSuccess(true);
			res.setMsg("密码修改成功");
		} else {
			res.setSuccess(false);
			res.setMsg("原密码校验失败");
		}
		
		return res;
	}
	
	@Override
	public List<CasUserDO> queryAll() throws BizException {
		List<CasUserDO> res = casUserDao.query(null);	
		return res;
	}

}

