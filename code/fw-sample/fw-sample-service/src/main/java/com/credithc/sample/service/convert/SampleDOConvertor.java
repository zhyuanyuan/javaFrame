package com.credithc.sample.service.convert;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.credithc.sample.dao.entity.SampleDO;
import com.credithc.sample.service.dto.CreateSampleReqTO;
import com.credithc.sample.service.dto.DeleteSampleReqTO;
import com.credithc.sample.service.dto.ModifySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleBeanTO;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;

public class SampleDOConvertor {
	
	public static SampleDO convertCreateSampleDO(CreateSampleReqTO to){
		SampleDO entity = new SampleDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static SampleDO convertModifySampleDO(ModifySampleReqTO to){
		SampleDO entity = new SampleDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static SampleDO convertDeleteSampleDO(DeleteSampleReqTO to){
		SampleDO entity = new SampleDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static SampleDO convertQuerySampleDO(QuerySampleReqTO to){
		SampleDO entity = new SampleDO();
		BeanUtils.copyProperties(to, entity);
		return entity;
	}
	
	public static QuerySampleResTO convertQuerySampleTO(List<SampleDO> dos){
		QuerySampleResTO rst = new QuerySampleResTO();
		
		if(CollectionUtils.isNotEmpty(dos)){
			for(SampleDO entity : dos){
				QuerySampleBeanTO to = new QuerySampleBeanTO();
				BeanUtils.copyProperties(entity, to);
				rst.getResultList().add(to);
			}
		}
		
		return rst;
	}

}
