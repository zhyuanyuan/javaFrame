package com.credithc.cas.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.credithc.cas.user.dao.UserDao;
import com.credithc.cas.user.dao.entity.UserDO;
import com.credithc.common.dao.AbsBaseDao;

@Repository
public class UserDaoImpl extends AbsBaseDao<UserDO> implements UserDao {

	@Override
	public int updateByUsername(UserDO user) {
		return this.sqlSession.update("updateByUsername", user);
	}
	
}
