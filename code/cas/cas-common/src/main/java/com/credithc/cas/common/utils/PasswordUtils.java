package com.credithc.cas.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.credithc.cas.common.server.UserShaPasswordEncoder;

/**
 * cas用户密码加密工具
 * @author yangyang151020
 *
 */
public class PasswordUtils {
	
	private static UserShaPasswordEncoder encoder = new UserShaPasswordEncoder();
	
	public static List<String> encodePassword(String password){
		List<String> r = new ArrayList<String>();
		String salt = String.valueOf(RandomUtils.nextInt(1000));
		r.add(salt);
		r.add(encoder.encodePassword(password, salt));
		return r;
	}
	
	public static boolean validatePassword(String encPass, String rawPass, String salt){
		return encoder.isPasswordValid(encPass, rawPass, salt);
	}
	
}
