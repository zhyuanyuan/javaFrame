package com.credithc.sample.core.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.sample.test.support.BackgroundLogsQueue;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.AbstractThroughputDataFlowElasticJob;

public class ThroughputDataFlowJobSample extends AbstractThroughputDataFlowElasticJob<Foo> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Map<Integer, List<Foo>> datas = new HashMap<Integer, List<Foo>>();
	static{
		Integer page = new Integer(0);
		datas.put(page, new ArrayList<Foo>());
		datas.get(page).add(new Foo("foo_0_1"));
		datas.get(page).add(new Foo("foo_0_2"));
		datas.get(page).add(new Foo("foo_0_3"));
		datas.get(page).add(new Foo("foo_0_4"));
		datas.get(page).add(new Foo("foo_0_5"));
		
		page ++;
		datas.put(page, new ArrayList<Foo>());
		datas.get(page).add(new Foo("foo_1_1"));
		datas.get(page).add(new Foo("foo_1_2"));
		datas.get(page).add(new Foo("foo_1_3"));
		datas.get(page).add(new Foo("foo_1_4"));
		datas.get(page).add(new Foo("foo_1_5"));
		
		page ++;
		datas.put(page, new ArrayList<Foo>());
		datas.get(page).add(new Foo("foo_2_1"));
		datas.get(page).add(new Foo("foo_2_2"));
		datas.get(page).add(new Foo("foo_2_3"));
		datas.get(page).add(new Foo("foo_2_4"));
		datas.get(page).add(new Foo("foo_2_5"));
	}

	private Iterator<Integer> itemsItr = null;
	@Override
	public List<Foo> fetchData(JobExecutionMultipleShardingContext ctx) {
		String str1 = ObjectUtils.toString(ctx.getShardingTotalCount());
		String str2 = ObjectUtils.toString(ctx.getShardingItems());
		String str3 = ObjectUtils.toString(ctx.getShardingItemParameters());
		BackgroundLogsQueue.appendLogs("####ThroughputDataFlow JOB is FETCHING datas.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3);
		logger.info("####ThroughputDataFlow JOB is FETCHING datas.....str1=" + str1  + ",str2=" + str2  + ",str3=" + str3);
		
		if(itemsItr == null){
			if(CollectionUtils.isNotEmpty(ctx.getShardingItems())){
				itemsItr = ctx.getShardingItems().iterator();
			}
		}
		List<Foo> rst = null;
		if(itemsItr != null && itemsItr.hasNext()){
			rst = datas.get(itemsItr.next());
		}else{
			itemsItr = null;
		}
		return rst;
	}

	@Override
	public boolean isStreamingProcess() {
		return true;
	}

	@Override
	public boolean processData(JobExecutionMultipleShardingContext ctx, Foo data) {
		BackgroundLogsQueue.appendLogs("####ThroughputDataFlow is PROCESSING datas@@@@" + Thread.currentThread().getId() +"####"+ data.toString());
		logger.info("####ThroughputDataFlow is PROCESSING datas@@@@" + Thread.currentThread().getId() +"####" + data.toString());
		return true;
	}

}

class Foo{
	private String str;
	Foo(String str){
		this.str = str;
	}
	
	public String toString(){
		return str;
	}
	
}
