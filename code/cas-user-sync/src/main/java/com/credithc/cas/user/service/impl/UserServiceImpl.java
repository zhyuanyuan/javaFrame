package com.credithc.cas.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credithc.cas.user.dao.UserDao;
import com.credithc.cas.user.dao.entity.UserDO;
import com.credithc.cas.user.service.UserService;
import com.credithc.cas.user.service.dto.UserResTO;
import com.credithc.common.exception.BizException;
import com.credithc.common.service.AbsService;

@Service
public class UserServiceImpl extends AbsService implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserResTO createIfNotExist(UserDO user) throws BizException{
		UserResTO res = new UserResTO();
		
		//查询用户名是否存在
		UserDO condition = new UserDO();
		condition.setUserName(user.getUserName());	
		UserDO user2 = userDao.queryOne(condition);
		
		if (user2 != null) {
			user.setId(user2.getId());
			userDao.update(user);
			res.setMsg("用户修改成功");
		} else {
			userDao.insert(user);
			res.setMsg("用户新增成功");
		}
		
		res.setSuccess(true);
		return res;
	}
	
	@Override
	public UserResTO createUser(UserDO user) throws BizException{
		UserResTO res = new UserResTO();
		//查询用户名是否存在
		UserDO condition = new UserDO();
		condition.setUserName(user.getUserName());	
		UserDO user2 = userDao.queryOne(condition);
		if (user2 != null) {
			res.setSuccess(false);
			res.setMsg("用户名已存在！");
			return res;
		}
		
		if (StringUtils.isBlank(user.getCompanyId())) {
			user.setCompanyId("HC");
		}
		
		userDao.insert(user);
		res.setSuccess(true);
		res.setMsg("添加用户成功");
		return res;
	}

	@Override
	public UserResTO modifyUser(UserDO user) throws BizException{
		UserResTO res = new UserResTO();
		
		userDao.update(user);
		
		res.setSuccess(true);
		res.setMsg("修改用户成功");
		return res;
	}

	@Override
	public List<UserDO> queryAll() throws BizException {
		List<UserDO> res = userDao.query(null);	
		return res;
	}

	@Override
	public UserResTO updateUserByUsername(UserDO user) throws BizException {
		UserResTO res = new UserResTO();
		userDao.updateByUsername(user);
		res.setSuccess(true);
		res.setMsg("修改用户成功");
		return res;
	}

}

