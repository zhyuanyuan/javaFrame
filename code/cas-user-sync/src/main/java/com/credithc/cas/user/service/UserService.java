package com.credithc.cas.user.service;

import java.util.List;

import com.credithc.cas.user.dao.entity.UserDO;
import com.credithc.cas.user.service.dto.UserResTO;
import com.credithc.common.exception.BizException;

public interface UserService {
	
	UserResTO createIfNotExist(UserDO user) throws BizException;
	
	UserResTO createUser(UserDO user) throws BizException;
	
	UserResTO modifyUser(UserDO user) throws BizException;
	
	List<UserDO> queryAll() throws BizException;
	
	UserResTO updateUserByUsername(UserDO user) throws BizException;
}
