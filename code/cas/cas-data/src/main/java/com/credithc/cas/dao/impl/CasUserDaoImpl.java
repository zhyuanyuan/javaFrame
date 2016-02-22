package com.credithc.cas.dao.impl;

import org.apache.commons.lang.math.RandomUtils;

import com.credithc.cas.common.server.UserShaPasswordEncoder;
import com.credithc.cas.dao.CasUserDao;
import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.common.dao.AbsBaseDao;

public class CasUserDaoImpl extends AbsBaseDao<CasUserDO> implements CasUserDao {
	
	private UserShaPasswordEncoder passwordEncoder = new UserShaPasswordEncoder();
	
	public void changePassword(Long id, String newPassword) {
		CasUserDO condition = new CasUserDO();
		condition.setId(id);
		String salt = String.valueOf(RandomUtils.nextInt(1000));
		condition.setSalt(salt);
		condition.setPassword(passwordEncoder.encodePassword(newPassword, salt));
		update(condition);
	}

	public boolean checkPassword(String username, String password) {
		CasUserDO condition = new CasUserDO();
		condition.setUserName(username);
		CasUserDO user = queryOne(condition);
		if (user == null || !passwordEncoder.isPasswordValid(user.getPassword(), 
				password, user.getSalt())) {
			return false;
		} else {
			return true;
		}
	}
	
}
