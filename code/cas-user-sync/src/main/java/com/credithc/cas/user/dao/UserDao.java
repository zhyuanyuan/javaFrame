package com.credithc.cas.user.dao;

import com.credithc.cas.user.dao.entity.UserDO;
import com.credithc.common.dao.BaseDao;

public interface UserDao extends BaseDao<UserDO>{
	
	int updateByUsername(UserDO user);
	
}
