package com.credithc.cas.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.credithc.cas.service.CasUserService;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.cas.service.dto.CasUserResTO;
import com.credithc.cas.service.dto.UpdatePasswordReqTO;
import com.credithc.common.exception.BizException;

@Controller
public class CasUserController {
	
	@Autowired
	private CasUserService casUserService;
	
	@ResponseBody
	@RequestMapping(value="add_user.do")
    public void addUser(@ModelAttribute CasUserReqTO user, HttpServletResponse rsp,
    		HttpServletRequest req) {
		rsp.setContentType("application/json;charset=UTF-8");

		CasUserResTO res = new CasUserResTO();
		res.setSuccess(false);
		AttributePrincipal userinfo = (AttributePrincipal)req.getUserPrincipal();
		String isAdmin = (String)(userinfo.getAttributes().get("isAdmin"));
		if (StringUtils.isBlank(isAdmin) || "false".equals(isAdmin)) {
			res.setMsg("您没有权限添加用户");
		} else {
			String msg = checkAddUserForm(user);
			if (msg != null) {
				res.setMsg(msg); 
			} else {
				try {
					res = casUserService.createUser(user);
				} catch (BizException e) {
					res.setMsg(e.getErrorMsg());
				}
			}
		}
		
		try {
			rsp.getWriter().write(res.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private String checkAddUserForm(CasUserReqTO user){
		if (StringUtils.isBlank(user.getUserName())) {
			return "请输入用户名";
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return "请输入密码";
		}
		if (!user.getPassword().equals(user.getRepassword())) {
			return "两次密码不一致";
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("update_password.do")
    public void updatePassword(HttpServletResponse rsp,
    		@RequestParam String username,
    		@RequestParam String password,
    		@RequestParam String newpassword,
    		@RequestParam String repassword) {
		UpdatePasswordReqTO req = new UpdatePasswordReqTO();
		req.setUserName(username);
		req.setPassword(password);
		req.setNewPassword(newpassword);
		CasUserResTO res = new CasUserResTO();
		res.setSuccess(false);
		rsp.setContentType("application/json;charset=UTF-8");
		String msg = checkUpdatePasswordForm(password, newpassword, repassword);
		if (msg != null) {
			res.setMsg(msg);
		} else {
			try {
				res = casUserService.updatePassword(req);
			} catch (BizException e) {
				res.setMsg(e.getErrorMsg());
			}
		}
		
		try {
			rsp.getWriter().write(res.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private String checkUpdatePasswordForm(String password, String newpassword,
    		String repassword){
		if (StringUtils.isBlank(password)) {
			return "请输入原密码";
		}
		if (StringUtils.isBlank(newpassword)) {
			return "请输入新密码";
		}
		if (!newpassword.equals(repassword)) {
			return "两次密码输入不一致";
		} 
		return null;
	}
}

