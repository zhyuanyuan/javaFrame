package com.credithc.cas.dao;

import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.common.dao.BaseDao;

public interface CasUserDao extends BaseDao<CasUserDO>{
	
	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 */
	public void changePassword(Long id, String newPassword);

	/**
	 * 验证原密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String username, String password);
	
}
