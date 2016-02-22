package com.credithc.sample.core.job;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.sample.service.SampleService;
import com.credithc.sample.service.dto.QuerySampleReqTO;
import com.credithc.sample.service.dto.QuerySampleResTO;
import com.credithc.sample.test.support.BackgroundLogsQueue;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.AbstractSimpleElasticJob;

public class SimpleJobSample extends AbstractSimpleElasticJob {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private SampleService sampleService;
	
	@Override
	public void process(JobExecutionMultipleShardingContext ctx) {
		try {
			String str1 = ObjectUtils.toString(ctx.getShardingTotalCount());
			String str2 = ObjectUtils.toString(ctx.getShardingItems());
			String str3 = ObjectUtils.toString(ctx.getShardingItemParameters());
			
			QuerySampleReqTO req = new QuerySampleReqTO();
			req.setId("1");
			QuerySampleResTO res = sampleService.querySample(req);
			String o = ToStringBuilder.reflectionToString(res.getResultList().get(0), ToStringStyle.SHORT_PREFIX_STYLE);
			
			BackgroundLogsQueue.appendLogs("####Simple JOB is running.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3 + ",rst=" + o);
			logger.info("####Simple JOB is running.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3 + ",rst=" + o);
		} catch (Exception e) {
			BackgroundLogsQueue.appendLogs("####Simple job sample error " + e.toString());
			logger.error("####Simple job sample error", e);
		}
	}

	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}
	
}
