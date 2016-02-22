package com.credithc.sample.core.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.sample.test.support.BackgroundLogsQueue;
import com.dangdang.ddframe.job.api.JobExecutionSingleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.AbstractSequenceDataFlowElasticJob;

public class SequenceDataFlowJobSample extends AbstractSequenceDataFlowElasticJob<Fcc> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Map<Integer, List<Fcc>> datas = new HashMap<Integer, List<Fcc>>();
	static{
		Integer page = new Integer(0);
		datas.put(page, new ArrayList<Fcc>());
		datas.get(page).add(new Fcc("FCC_0_1"));
		datas.get(page).add(new Fcc("FCC_0_2"));
		datas.get(page).add(new Fcc("FCC_0_3"));
		datas.get(page).add(new Fcc("FCC_0_4"));
		datas.get(page).add(new Fcc("FCC_0_5"));
		
		page ++;
		datas.put(page, new ArrayList<Fcc>());
		datas.get(page).add(new Fcc("FCC_1_1"));
		datas.get(page).add(new Fcc("FCC_1_2"));
		datas.get(page).add(new Fcc("FCC_1_3"));
		datas.get(page).add(new Fcc("FCC_1_4"));
		datas.get(page).add(new Fcc("FCC_1_5"));
		
		page ++;
		datas.put(page, new ArrayList<Fcc>());
		datas.get(page).add(new Fcc("FCC_2_1"));
		datas.get(page).add(new Fcc("FCC_2_2"));
		datas.get(page).add(new Fcc("FCC_2_3"));
		datas.get(page).add(new Fcc("FCC_2_4"));
		datas.get(page).add(new Fcc("FCC_2_5"));
	}

	@Override
	public List<Fcc> fetchData(JobExecutionSingleShardingContext ctx) {
		String str1 = ObjectUtils.toString(ctx.getShardingTotalCount());
		String str2 = ObjectUtils.toString(ctx.getShardingItem());
		String str3 = ObjectUtils.toString(ctx.getShardingItemParameter());
		BackgroundLogsQueue.appendLogs("####SequenceDataFlow JOB is FETCHING datas.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3);
		logger.info("####SequenceDataFlow JOB is FETCHING datas.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3);
		
		return datas.get(ctx.getShardingItem());
	}

	@Override
	public boolean isStreamingProcess() {
		return false;
	}

	@Override
	public boolean processData(JobExecutionSingleShardingContext ctx, Fcc data) {
		BackgroundLogsQueue.appendLogs("####SequenceDataFlowJob is PROCESSING datas@@@@" + Thread.currentThread().getId() +"####"+ data.toString());
		logger.info("####SequenceDataFlowJob is PROCESSING datas@@@@" + Thread.currentThread().getId() +"####" + data.toString());
		return true;
	}

}

class Fcc{
	private String str;
	Fcc(String str){
		this.str = str;
	}
	
	public String toString(){
		return str;
	}
}
