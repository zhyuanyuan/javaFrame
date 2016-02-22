package com.credithc.cas.service.convert;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.credithc.cas.dao.entity.CasUserDO;
import com.credithc.cas.service.dto.CasUserReqTO;
import com.credithc.cas.service.dto.QueryCasUserBeanTO;
import com.credithc.cas.service.dto.QueryCasUserReqTO;
import com.credithc.cas.service.dto.QueryCasUserResTO;

public class CasUserDOConvertor {
	
	public static CasUserDO convertCasUserDO(CasUserReqTO to){
		CasUserDO entity = new CasUserDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static CasUserDO convertQueryCasUserDO(QueryCasUserReqTO to){
		CasUserDO entity = new CasUserDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static QueryCasUserResTO convertQueryCasUserTO(List<CasUserDO> dos){
		QueryCasUserResTO rst = new QueryCasUserResTO();
		
		if(CollectionUtils.isNotEmpty(dos)){
			for(CasUserDO entity : dos){
				QueryCasUserBeanTO to = new QueryCasUserBeanTO();
				BeanUtils.copyProperties(entity, to);
				rst.getResultList().add(to);
			}
		}
		
		return rst;
	}

}
