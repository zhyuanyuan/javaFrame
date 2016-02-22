package com.credithc.cas.user.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.credithc.common.web.AbsController;

/**
 * 用户检测
 * @author yangyang151020
 *
 */
@Controller
public class CheckUserController extends AbsController {
	
	/**
	 * 用户是否登录，true已登录
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userSync/isLogin.do")
	public void syncUser(HttpSession ses, HttpServletResponse rsp) {
		rsp.setContentType("text/html;charset=UTF-8");
		AssertionImpl ast = (AssertionImpl)ses.getAttribute("_const_cas_assertion_");
		String flag = "false";
		if (ast != null && ast.getPrincipal() != null && StringUtils.isNotBlank(ast.getPrincipal().getName())) {
			flag = "true";
		}
		try {
			rsp.getWriter().write(flag);
		} catch (IOException e) {
			
		}
	}
}
