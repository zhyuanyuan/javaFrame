package com.credithc.cas.facade;

import java.util.List;

import javax.ws.rs.Path;

import com.credithc.cas.facade.dto.CasUserDTO;
import com.credithc.cas.facade.dto.CasUserReq;
import com.credithc.cas.facade.dto.CasUserRes;

/**
 * 用户对外接口
 * @author yangyang151020
 *
 */
@Path(value="/casUser")
public interface CasUserFacade {
	
	/**
	 * 有此用户则修改，无此用户则新增
	 * @param req
	 * @return
	 */
	CasUserRes createIfNotExist(CasUserReq req);
	
	/**
	 * 查询用户列表
	 * @return
	 */
	List<CasUserDTO> queryAll();
	
}
