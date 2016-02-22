package com.credithc.cas.core.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.cas.facade.CasUserFacade;
import com.credithc.cas.facade.dto.CasUserDTO;
import com.credithc.cas.service.CasUserService;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.common.exception.BizException;
import com.credithc.common.web.AbsController;

/**
 * 同步用户
 * @author yangyang151020
 *
 */
@Controller
public class SyncUserJob extends AbsController {
	
	@Autowired
	private CasUserFacade casUserFacade;
	@Autowired
	private CasUserService casUserService;
	
	@ResponseBody
	@RequestMapping(value="/syncUsersInfo.do")
	public String process() {
		try {
			syncUsersInfo();
			return "用户同步成功";
		} catch (BizException e) {
			logger.error(null, e.getErrorMsg());
			return e.getErrorMsg();
		}
	}
	
	@Transactional
	public void syncUsersInfo() throws BizException {
		logger.info(null, "--------------------同步用户开始----------------------");
		int add = 0;//添加用户数
		int update = 0;//修改用户数
		List<CasUserDTO> casUsers = casUserFacade.queryAll();//cas用户
		List<CasUserDO> users = casUserService.queryAll();//本系统用户
		if (casUsers != null && users != null) {
			logger.info(null, "--------------------cas系统用户数量" + casUsers.size() + "----------------------");
			logger.info(null, "--------------------本系统用户数量" + users.size() + "----------------------");
			
			Map<String, CasUserDO> userMap = new HashMap<String, CasUserDO>();
			for (CasUserDO user : users) {
				userMap.put(user.getUserName(), user);
			}
	
			for (CasUserDTO casUser : casUsers) {
				CasUserReqTO entity = new CasUserReqTO();
				BeanUtils.copyProperties(casUsers, entity);
				
				CasUserDO myUser = userMap.get(casUser.getUserName());
				if (myUser == null){
					casUserService.createUser(entity);;
					add ++;
					logger.info(null, "*******添加用户:" + entity.getUserName());
				} else {
					if(casUser.getEnabled()!= myUser.getEnabled() 
							|| casUser.getMale() != myUser.getMale()
							|| (casUser.getAddress() != null && !casUser.getAddress().equals(myUser.getAddress()))
							|| (casUser.getBirthday() != null && !casUser.getBirthday().equals(myUser.getBirthday()))
							|| (casUser.getCname() != null && !casUser.getCname().equals(myUser.getCname()))
							|| (casUser.getEname() != null && !casUser.getEname().equals(myUser.getEname()))
							|| (casUser.getEmail() != null && !casUser.getEmail().equals(myUser.getEmail()))
							|| (casUser.getMobile() != null && !casUser.getMobile().equals(myUser.getMobile()))
							|| (casUser.getPassword() != null && !casUser.getPassword().equals(myUser.getPassword()))
							|| (casUser.getSalt() != null && !casUser.getSalt().equals(myUser.getSalt()))
							|| (casUser.getCompanyId() != null && !casUser.getCompanyId().equals(myUser.getCompanyId()))){
						casUserService.modifyUser(entity);
						update ++;
						logger.info(null, "*******修改用户：" + entity.getUserName());
					}
				}
			}
		}
		
		logger.info(null, "--------------------添加用户："+ add +"----------------------");
		logger.info(null, "--------------------修改用户："+ update +"----------------------");
		logger.info(null, "--------------------同步用户结束----------------------");
	}

	public CasUserFacade getCasUserFacade() {
		return casUserFacade;
	}

	public void setCasUserFacade(CasUserFacade casUserFacade) {
		this.casUserFacade = casUserFacade;
	}

	public CasUserService getCasUserService() {
		return casUserService;
	}

	public void setCasUserService(CasUserService casUserService) {
		this.casUserService = casUserService;
	}
	
}
