package com.credithc.cas.common.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author yangyang151020
 *
 */
public class UnifyClientRest implements ApplicationContextAware {
	private String username;
	private String password;
	private String baseUrl;
	private HttpHeaders headers;
	private RestTemplate template;

	public UnifyClientRest() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	/**
	 * rest通用调用
	 * 
	 * @param url
	 * @param headerUserNameKey
	 * @param headerPasswordKey
	 * @param userName
	 * @param password
	 * @param obj
	 * @param responseClazz
	 * @return
	 */
	public <T> T post(String url, String headerUserNameKey,
			String headerPasswordKey, String userName, String password,
			Object obj, Class<T> responseClazz) {
		if (!StringUtils.isEmpty(headerUserNameKey)) {
			headers.add(headerUserNameKey, userName);
			headers.add(headerPasswordKey, password);
		}
		if (obj == null) {
			obj = "blank";
		}
		HttpEntity<Object> entity = null;
		if (headers != null) {
			entity = new HttpEntity<Object>(obj, headers);
		} else {
			entity = new HttpEntity<Object>(obj);
		}
		ResponseEntity<T> response = template.postForEntity(url, entity,
				responseClazz);
		return response.getBody();
	}

	/**
	 * 集成系统调用统一授权使用
	 * 
	 * @param uri
	 * @param obj
	 * @param responseClazz
	 * @return
	 */
	public <T> T post(String uri, Object obj, Class<T> responseClazz) {
		return post(baseUrl + uri,
				UnifyConstants.UNIFY_FRAME_SERVICE_HEARDER_USERNAME,
				UnifyConstants.UNIFY_FRAME_SERVICE_HEARDER_PASSWORD, username,
				password, obj, responseClazz);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RestTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RestTemplate template) {
		this.template = template;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}
}
