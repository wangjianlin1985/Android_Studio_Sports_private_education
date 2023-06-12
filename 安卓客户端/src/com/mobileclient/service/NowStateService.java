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

/*��״̬����ҵ���߼���*/
public class NowStateService {
	/* �����״̬ */
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

	/* ��ѯ��״̬ */
	public List<NowState> QueryNowState(NowState queryConditionNowState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NowStateServlet?action=query";
		if(queryConditionNowState != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
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
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
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

	/* ������״̬ */
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

	/* ɾ����״̬ */
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
			return "��״̬��Ϣɾ��ʧ��!";
		}
	}

	/* ����״̬id��ȡ��״̬���� */
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
