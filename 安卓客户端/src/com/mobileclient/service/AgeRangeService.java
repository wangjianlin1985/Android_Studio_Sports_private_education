package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.AgeRange;
import com.mobileclient.util.HttpUtil;

/*���䷶Χ����ҵ���߼���*/
public class AgeRangeService {
	/* ������䷶Χ */
	public String AddAgeRange(AgeRange ageRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("arId", ageRange.getArId() + "");
		params.put("startAge", ageRange.getStartAge() + "");
		params.put("endAge", ageRange.getEndAge() + "");
		params.put("showText", ageRange.getShowText());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AgeRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���䷶Χ */
	public List<AgeRange> QueryAgeRange(AgeRange queryConditionAgeRange) throws Exception {
		String urlString = HttpUtil.BASE_URL + "AgeRangeServlet?action=query";
		if(queryConditionAgeRange != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		AgeRangeListHandler ageRangeListHander = new AgeRangeListHandler();
		xr.setContentHandler(ageRangeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<AgeRange> ageRangeList = ageRangeListHander.getAgeRangeList();
		return ageRangeList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<AgeRange> ageRangeList = new ArrayList<AgeRange>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				AgeRange ageRange = new AgeRange();
				ageRange.setArId(object.getInt("arId"));
				ageRange.setStartAge(object.getInt("startAge"));
				ageRange.setEndAge(object.getInt("endAge"));
				ageRange.setShowText(object.getString("showText"));
				ageRangeList.add(ageRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ageRangeList;
	}

	/* �������䷶Χ */
	public String UpdateAgeRange(AgeRange ageRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("arId", ageRange.getArId() + "");
		params.put("startAge", ageRange.getStartAge() + "");
		params.put("endAge", ageRange.getEndAge() + "");
		params.put("showText", ageRange.getShowText());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AgeRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ�����䷶Χ */
	public String DeleteAgeRange(int arId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("arId", arId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AgeRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "���䷶Χ��Ϣɾ��ʧ��!";
		}
	}

	/* �������䷶Χid��ȡ���䷶Χ���� */
	public AgeRange GetAgeRange(int arId)  {
		List<AgeRange> ageRangeList = new ArrayList<AgeRange>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("arId", arId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AgeRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				AgeRange ageRange = new AgeRange();
				ageRange.setArId(object.getInt("arId"));
				ageRange.setStartAge(object.getInt("startAge"));
				ageRange.setEndAge(object.getInt("endAge"));
				ageRange.setShowText(object.getString("showText"));
				ageRangeList.add(ageRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = ageRangeList.size();
		if(size>0) return ageRangeList.get(0); 
		else return null; 
	}
}
