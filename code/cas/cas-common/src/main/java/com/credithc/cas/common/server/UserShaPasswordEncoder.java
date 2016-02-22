package com.credithc.cas.common.server;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 
 * @author yangyang151020
 *
 */
public class UserShaPasswordEncoder extends ShaPasswordEncoder {

	public UserShaPasswordEncoder() {
		super();
	}

	public UserShaPasswordEncoder(int strength) {
		super(strength);
	}

}
