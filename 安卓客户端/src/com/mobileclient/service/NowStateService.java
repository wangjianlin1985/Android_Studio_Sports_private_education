package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.NowState;
import com.mobileclient.util.HttpUtil;

/*现状态管理业务逻辑层*/
public class NowStateService {
	/* 添加现状态 */
	public String AddNowState(NowState nowState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", nowState.getStateId() + "");
		params.put("stateName", nowState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NowStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询现状态 */
	public List<NowState> QueryNowState(NowState queryConditionNowState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NowStateServlet?action=query";
		if(queryConditionNowState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NowStateListHandler nowStateListHander = new NowStateListHandler();
		xr.setContentHandler(nowStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<NowState> nowStateList = nowStateListHander.getNowStateList();
		return nowStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<NowState> nowStateList = new ArrayList<NowState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NowState nowState = new NowState();
				nowState.setStateId(object.getInt("stateId"));
				nowState.setStateName(object.getString("stateName"));
				nowStateList.add(nowState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nowStateList;
	}

	/* 更新现状态 */
	public String UpdateNowState(NowState nowState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", nowState.getStateId() + "");
		params.put("stateName", nowState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NowStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除现状态 */
	public String DeleteNowState(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NowStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "现状态信息删除失败!";
		}
	}

	/* 根据状态id获取现状态对象 */
	public NowState GetNowState(int stateId)  {
		List<NowState> nowStateList = new ArrayList<NowState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NowStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NowState nowState = new NowState();
				nowState.setStateId(object.getInt("stateId"));
				nowState.setStateName(object.getString("stateName"));
				nowStateList.add(nowState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = nowStateList.size();
		if(size>0) return nowStateList.get(0); 
		else return null; 
	}
}
