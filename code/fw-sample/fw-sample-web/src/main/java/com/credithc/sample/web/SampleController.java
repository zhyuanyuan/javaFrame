package com.credithc.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.credithc.common.web.AbsController;
import com.credithc.sample.facade.SampleFacade;
import com.credithc.sample.facade.dto.CreateSampleReq;
import com.credithc.sample.facade.dto.DeleteSampleReq;
import com.credithc.sample.facade.dto.ModifySampleReq;
import com.credithc.sample.facade.dto.QuerySampleBean;
import com.credithc.sample.facade.dto.QuerySampleReq;
import com.credithc.sample.facade.dto.QuerySampleRes;
import com.credithc.sample.test.mock.facade.MockFacade;

@Controller
public class SampleController extends AbsController {
	
	@Autowired
	@Qualifier("sampleFacade")
	private SampleFacade sampleFacade;
	
	@Autowired
	private MockFacade mockFacade;
	
	@RequestMapping("/index")
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("welcome");
		return mav;
    }
	
	@RequestMapping("/add1")
	@ResponseBody
	public String add1(HttpServletRequest req, HttpServletResponse res) {
		
		CreateSampleReq csr = new CreateSampleReq();
		csr.setId(req.getParameter("id"));
		csr.setCode(req.getParameter("c1"));
		csr.setName(req.getParameter("c2"));
		csr.setMemo(req.getParameter("c3"));
		csr.setCrt_time(new java.sql.Date(System.currentTimeMillis()));
		sampleFacade.createSample(csr);
		return "新增成功！";
	}
	
	@RequestMapping("/update1")
	@ResponseBody
	public String update1(HttpServletRequest req, HttpServletResponse res) {
		ModifySampleReq msr = new ModifySampleReq();
		msr.setId(req.getParameter("id"));
		msr.setCode(req.getParameter("c1"));
		msr.setName(req.getParameter("c2"));
		msr.setMemo(req.getParameter("c3"));
		sampleFacade.modifySample(msr);
		return "修改成功！";
	}
	
//	private static List<Data> dataList = new ArrayList<Data>();
//	
//	static{
//		for(int i=0; i<5; ++i){
//			Data d = new Data();
//			d.setId("2015aa"+i);
//			d.setC1("c1data"+i);
//			d.setC2("c2data"+i);
//			d.setC3("c3data"+i);
//			dataList.add(d);
//		}
//	}

	
	@RequestMapping("/menu")
	public ModelAndView menu(String menu, HttpServletRequest req, HttpServletResponse res, ModelMap model) {
		
//		model.put("dataList", dataList);
		ModelAndView mav = new ModelAndView(menu);
		return mav;
		
	}
	@RequestMapping("menu1AddAndupdate")
	public String menu1Add(HttpServletRequest req, HttpServletResponse res) {
		return "menu1AddAndupdate";
	}
	
	@RequestMapping("/queryall")
	@ResponseBody
	public String queryall(String menu, HttpServletRequest req, HttpServletResponse res, ModelMap model) {
		QuerySampleReq rr = new QuerySampleReq();
//		rr.setId("1");
//		rr.setCode("0001");
		QuerySampleRes qsres = sampleFacade.querySample(rr);
		JSONArray json = new JSONArray();
		for(QuerySampleBean qsb : qsres.getResultList()){
			JSONObject jo = new JSONObject();
			jo.put("id", qsb.getId());
			jo.put("c1", qsb.getCode());
			jo.put("c2", qsb.getName());
			jo.put("c3", qsb.getMemo());
			json.add(jo);
		}
		return json.toJSONString();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id, HttpServletRequest req, HttpServletResponse res, ModelMap model) {
		DeleteSampleReq dsb = new DeleteSampleReq();
		dsb.setId(id);
		sampleFacade.deleteSample(dsb);
//		try{
//			List<Data> newList = new ArrayList<Data>();
//			for(Data d : dataList){
//				if(id.equals(d.getId()))continue;
//				newList.add(d);
//			}
//			dataList = newList;
//		}catch(Exception e){
//			return "删除失败！";
//		}
		return "删除成功！";
		
	}
	
//	@RequestMapping("/index")
//	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
//		String id= req.getParameter("id");
//		String code= req.getParameter("code");
//		
//		QuerySampleReq qr = new QuerySampleReq();
//		qr.setSysId("sys");
//		qr.setSysSeq("sys");
//		qr.setBusinessId("sys");
//		
//		qr.setId(StringUtils.isNotBlank(id) ? id : null);
//		qr.setCode(StringUtils.isNotBlank(code) ? code : null);
//		
//		QuerySampleRes r = sampleFacade.querySample(qr);
//		List<QuerySampleBean> list = r.getResultList();
//		
//		Map<String,Object> model = new HashMap<String,Object>();
//		model.put("sampleList", list);
//		ModelAndView mav = new ModelAndView("sample", model);
//		return mav;
//	}
//	
//	@RequestMapping("/sampleCUD")
//	public void cud(HttpServletRequest req, HttpServletResponse res){
//		try {
//			String cudType = req.getParameter("type");
//			StringBuilder rst = new StringBuilder();
//			if(StringUtils.equals("d", cudType)){//delete
//				String ids = req.getParameter("ids");
//				if(StringUtils.isNotBlank(ids)){
//					String [] idsary = ids.split(",");
//					for(String id : idsary){
//						DeleteSampleReq  delreq = new DeleteSampleReq ();
//						delreq.setId(id);
//						sampleFacade.deleteSample(delreq);
//					}
//					rst.append("删除成功");
//				}else{
//					rst.append("请选择要删除的数据项");
//				}
//			}
//			if(StringUtils.equals("a", cudType)){//add
//				rst.append("新增成功");
//			}
//			if(StringUtils.equals("u", cudType)){//update
//				rst.append("更新成功");
//			}
//			
//			res.setCharacterEncoding("utf-8");
//			PrintWriter out = res.getWriter();  
//			out.print(rst.toString());  
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			logger.error(null, "cud error", e);
//		}
//	}
//	
//	@RequestMapping("/sampleInvoke")
//	public void sampleInvoke(HttpServletRequest req, HttpServletResponse res){
//		try {
//			String type = req.getParameter("type");
//			String sysId = req.getParameter("sysid");
//			String busiId = req.getParameter("busiid");
//			String sysSeq = req.getParameter("sysseq");
//			
//			StringBuilder sb = new StringBuilder();
//			if(StringUtils.equals("1", type)){
//				sb.append("<font color='red'>直接调用</font><br>");
//				sb.append("<table>");
//				sb.append("<tr><td>ID</td><td>编码</td><td>名称</td><td>备注</td><td>创建时间</td><td>修改时间</td></tr>");
//				QuerySampleReq qr = new QuerySampleReq();
//				qr.setSysId(sysId);
//				qr.setSysSeq(sysSeq);
//				qr.setBusinessId(busiId);
//				QuerySampleRes r = sampleFacade.querySample(qr);
//				List<QuerySampleBean> list = r.getResultList();
//				if(list != null){
//					for(QuerySampleBean bean : list){
//						sb.append("<tr>");
//						sb.append("<td>" + bean.getId() + "</td>");
//						sb.append("<td>" + bean.getCode() + "</td>");
//						sb.append("<td>" + bean.getName() + "</td>");
//						sb.append("<td>" + bean.getMemo() + "</td>");
//						sb.append("<td>" + bean.getCrtTime() + "</td>");
//						sb.append("<td>" + bean.getMdfTime() + "</td>");
//						sb.append("</tr>");
//					}
//				}
//				sb.append("</table><br>");
//				sb.append(r.getRespMsg() + "<br>");
//			}else if(StringUtils.equals("2", type)){
//				sb.append("<font color='red'>发Queue(direct)</font><br>");
//				sb.append("direct-mq-exchange//null//message1...direct-mq-exchange...<br>");
//				sendMessage("direct-mq-exchange", "queue_one", "message1...direct-mq-exchange...");
//			}else if(StringUtils.equals("3", type)){
//				sb.append("<font color='red'>发Queue(fanout)</font><br>");
//				sb.append("fanout-mq-exchange//null//message1...fanout-mq-exchange...<br>");
//				sendMessage("fanout-mq-exchange", "", "message1...fanout-mq-exchange...");
//			}else if(StringUtils.equals("4", type)){
//				sb.append("<font color='red'>发Queue(topic)</font><br>");
//				sendMessage("topic-mq-exchange", "shanghai.1", "message1...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "beijing.3", "message2...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "5.news", "message3...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "7.football", "message4...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "shanghai.2", "message5...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "beijing.4", "message6...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "6.news", "message7...topic-mq-exchange...");
//				sendMessage("topic-mq-exchange", "8.football", "message8...topic-mq-exchange...");
//				sb.append("topic-mq-exchange//shanghai.1//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//beijing.3//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//5.news//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//7.football//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//shanghai.2//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//beijing.4//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//6.news//message1...topic-mq-exchange...<br>");
//				sb.append("topic-mq-exchange//8.football//message1...topic-mq-exchange...<br>");
//			}else if(StringUtils.equals("5", type)){
//				sb.append("<font color='red'>Rest POST(delete)</font><br>");
//				if(StringUtils.isNotBlank(req.getParameter("id"))){
//					Client client = ClientBuilder.newClient();
//			        WebTarget target = client.target("http://localhost:8888/services/sample/delete");
//			        DeleteSampleReq obj = new DeleteSampleReq();
//			        obj.setSysId(sysId);
//			        obj.setSysSeq(sysSeq);
//			        obj.setBusinessId(busiId);
//			        obj.setId(req.getParameter("id"));
//			        Response response = target.request().post(Entity.json(obj));
//			        try {
//			            if (response.getStatus() != 200) {
//			            	sb.append("HTTP error : " + response.getStatus() + "##" + response.getStatusInfo() + "<br>");
//			            }else{
//			            	sb.append("SUCC : " + response.readEntity(String.class) + "<br>");
//			            }
//			            
//			        } finally {
//			            response.close();
//			            client.close();
//			        }
//				}else{
//					sb.append("id is null,return with nothing doing<br>");
//				}
//			}else if(StringUtils.equals("6", type)){
//				sb.append("<font color='red'>Rest POST(update)</font><br>");
//				if(StringUtils.isNotBlank(req.getParameter("id"))){
//					Client client = ClientBuilder.newClient();
//			        WebTarget target = client.target("http://localhost:8888/services/sample/modify");
//			        QuerySampleReq r = new QuerySampleReq();
//			        r.setSysId(sysId);
//			        r.setSysSeq(sysSeq);
//			        r.setBusinessId(busiId);
//			        r.setId(req.getParameter("id"));
//			        QuerySampleRes rs = sampleFacade.querySample(new QuerySampleReq());
//			        ModifySampleReq obj = new ModifySampleReq();
//			        QuerySampleBean bean = rs.getResultList().get(0);
//			        BeanUtils.copyProperties(bean, obj);
//			        obj.setMemo("催收" + new Date().toString());
//			        Response response = target.request().post(Entity.json(obj));
//			        try {
//			            if (response.getStatus() != 200) {
//			            	sb.append("HTTP error : " + response.getStatus() + "##" + response.getStatusInfo() + "<br>");
//			            }else{
//			            	sb.append("SUCC : " + response.readEntity(String.class) + "<br>");
//			            }
//			            
//			        } finally {
//			            response.close();
//			            client.close();
//			        }
//				}else{
//					sb.append("id is null,return with nothing doing<br>");
//				}
//			}else if(StringUtils.equals("7", type)){
//				sb.append("<font color='red'>Rest Post(query)</font><br>");
//				Client client = ClientBuilder.newClient();
//		        WebTarget target = client.target("http://localhost:8888/services/sample/query");
//		        QuerySampleReq obj = new QuerySampleReq();
//		        obj.setSysId(sysId);
//		        obj.setSysSeq(sysSeq);
//		        obj.setBusinessId(busiId);
//		        obj.setId(req.getParameter("id"));
//		        Response response = target.request().post(Entity.json(obj));
//		        try {
//		            if (response.getStatus() != 200) {
//		            	sb.append("HTTP error : " + response.getStatus() + "##" + response.getStatusInfo() + "<br>");
//		            }else{
//		            	sb.append("SUCC : " + response.readEntity(String.class) + "<br>");
//		            }
//		            
//		        } finally {
//		            response.close();
//		            client.close();
//		        }
//			}else if(StringUtils.equals("8", type)){
//				sb.append("<font color='red'>programming tx(succ)</font><br>");
//				TransactionInfo tsi = beginTransaction();
//				try {
//					CreateSampleReq req1 = new CreateSampleReq();
//					req1.setId(System.currentTimeMillis() + "");
//					req1.setCode("001");
//					req1.setName("succ-小王");
//					req1.setMemo("额hehe...");
//					req1.setCrtTime(new Date());
//					req1.setMdfTime(new Date());
//					req1.setSysId("001");
//					req1.setSysSeq("001");
//					CreateSampleReq req2 = new CreateSampleReq();
//					req2.setId(System.currentTimeMillis() + "-2");
//					req2.setCode("002");
//					req2.setName("succ2-小王");
//					req2.setMemo("额hehe...");
//					req2.setCrtTime(new Date());
//					req2.setMdfTime(new Date());
//					req2.setSysId("001");
//					req2.setSysSeq("001");
//					CreateSampleRes response1 = sampleFacade.createSample(req1);
//					CreateSampleRes response2 = sampleFacade.createSample(req2);
//					commitTransaction(tsi);
//					sb.append("commit + " + response1.getRespMsg() + "#" + response2.getRespMsg() +"<br>");
//				} catch (Exception e) {
//					sb.append("programing tx(succ) exception:" + e.getMessage() + "<br>");
//					rollbackTransaction(tsi);
//					sb.append("rollback with exception<br>");
//				}
//			}else if(StringUtils.equals("9", type)){
//				sb.append("<font color='red'>programming tx(rollback)</font><br>");
//				TransactionInfo tsi = beginTransaction();
//				try {
//					CreateSampleReq req1 = new CreateSampleReq();
//					req1.setId(System.currentTimeMillis() + "");
//					req1.setCode("003");
//					req1.setName("rollback-小王");
//					req1.setMemo("额hehe...");
//					req1.setCrtTime(new Date());
//					req1.setMdfTime(new Date());
//					req1.setSysId("001");
//					req1.setSysSeq("001");
//					CreateSampleReq req2 = new CreateSampleReq();
//					req2.setId(System.currentTimeMillis() + "-2");
//					req2.setCode("004");
//					req2.setName("rollback2-小王");
//					req2.setMemo("额hehe...");
//					req2.setCrtTime(new Date());
//					req2.setMdfTime(new Date());
//					req2.setSysId("001");
//					req2.setSysSeq("001");
//					CreateSampleRes response1 = sampleFacade.createSample(req1);
//					CreateSampleRes response2 = sampleFacade.createSample(req2);
//					rollbackTransaction(tsi);
//					sb.append("rollback + " + response1.getRespMsg() + "#" + response2.getRespMsg() +"<br>");
//				} catch (Exception e) {
//					sb.append("programing tx(rollback) exception" + e.getMessage() + "<br>");
//					rollbackTransaction(tsi);
//					sb.append("rollback with exception<br>");
//				}
//			}else if(StringUtils.equals("10", type)){
//				try {
//					Context.initContext();
//					sb.append("<font color='red'>dubbo call(mock)</font><br>");
//					MockReq mreq = new MockReq();
//					mreq.setSysId(SysInfo.currentSystemId);
//					mreq.setSysSeq(sysSeq);
//					mreq.setBusinessId(busiId);
//					mreq.setRequestChain(">999");
//					Context.put(LoggerHelper.REQUEST_CHAIN_CTX_KEY, mreq.getRequestChain());
//					MockRes mres = mockFacade.mockCall(mreq);
//					sb.append("mock result:" + mres.getRespCode() + ","  + mres.getRespMsg() + "<br>");
//				} finally {
//					Context.destoryContext();
//				}
//			}else{
//				sb.append("unsupported invoking...<br>");
//			}
//			
//			res.setCharacterEncoding("utf-8");
//			PrintWriter out = res.getWriter();  
//			out.print(sb.toString());  
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			logger.error(null, "sample invoke error", e);
//		}
//	}
//	
//	@RequestMapping("/backgroundLogs")
//	public void backgroundLogs(HttpServletRequest req, HttpServletResponse res){
//		try {
//			String log = BackgroundLogsQueue.fetchLogs();
//			if(StringUtils.isNotBlank(log)){
//				res.setCharacterEncoding("utf-8");
//				PrintWriter out = res.getWriter();  
//				out.print(log);  
//				out.flush();
//				out.close();
//			}
//		} catch (Exception e) {
//			logger.error(null, "background logs error", e);
//		}
//	}
//
//	public void setSampleFacade(SampleFacade sampleFacade) {
//		this.sampleFacade = sampleFacade;
//	}
//
//	public void setMockFacade(MockFacade mockFacade) {
//		this.mockFacade = mockFacade;
//	}

}
