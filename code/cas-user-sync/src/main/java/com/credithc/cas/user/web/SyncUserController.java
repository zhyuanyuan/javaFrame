package com.credithc.cas.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.credithc.cas.facade.CasUserFacade;
import com.credithc.cas.facade.dto.CasUserDTO;
import com.credithc.cas.user.dao.entity.UserDO;
import com.credithc.cas.user.service.UserService;
import com.credithc.cas.user.service.dto.UserResTO;
import com.credithc.common.exception.BizException;
import com.credithc.common.web.AbsController;

/**
 * 同步用户
 * @author yangyang151020
 *
 */
@Controller
public class SyncUserController extends AbsController {
	
	@Autowired
	private CasUserFacade casUserFacade;
	@Autowired
	private UserService userService;
	
	/**
	 * 同步单个用户
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userSync/syncUserInfo.do")
	public UserResTO syncUser(@ModelAttribute UserDO user) {
		UserResTO res = new UserResTO();
		
		try {
			res = userService.createIfNotExist(user);
		} catch (BizException e) {
			logger.error(null, e.getErrorMsg());
			res.setMsg(e.getErrorMsg());
			res.setSuccess(false);
		}
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/userSync/syncAllUsersInfo.do")
	public String syncAllUsersInfo() {
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
		List<UserDO> users = userService.queryAll();//本系统用户
		if (casUsers != null && users != null) {
			logger.info(null, "--------------------cas系统用户数量" + casUsers.size() + "----------------------");
			logger.info(null, "--------------------本系统用户数量" + users.size() + "----------------------");
			
			Map<String, UserDO> userMap = new HashMap<String, UserDO>();
			for (UserDO user : users) {
				userMap.put(user.getUserName(), user);
			}
	
			for (CasUserDTO casUser : casUsers) {
				UserDO entity = convertTOUserDO(casUser);
				UserDO myUser = userMap.get(casUser.getUserName());
				if (myUser == null){
					userService.createUser(entity);
					add ++;
					logger.info(null, "*******添加用户:" + entity.toString());
				} else {
					if((casUser.getEnabled() != null && !casUser.getEnabled().equals(myUser.getEnabled())) 
							|| (casUser.getCname() != null && !casUser.getCname().equals(myUser.getCname()))
							|| (casUser.getEname() != null && !casUser.getEname().equals(myUser.getEname()))
							|| (casUser.getCompanyId() != null && !casUser.getCompanyId().equals(myUser.getCompanyId()))){
						userService.updateUserByUsername(entity);
						update ++;
						logger.info(null, "*******修改用户：" + entity.toString());
					}
				}
			}
		}
		
		logger.info(null, "--------------------添加用户："+ add +"----------------------");
		logger.info(null, "--------------------修改用户："+ update +"----------------------");
		logger.info(null, "--------------------同步用户结束----------------------");
	}

	private UserDO convertTOUserDO(CasUserDTO casUser){
		UserDO entity = new UserDO();
		if (casUser.getCname() != null) {
			entity.setCname(casUser.getCname());
		}
		if (casUser.getCompanyId() != null) {
			entity.setCompanyId(casUser.getCompanyId());
		}
		if (casUser.getEnabled() != null) {
			entity.setEnabled(casUser.getEnabled());
		}
		if (casUser.getEname() != null) {
			entity.setEname(casUser.getEname());
		}
		if (casUser.getUserName() != null) {
			entity.setUserName(casUser.getUserName());
		}
		return entity;
	}
	
}
