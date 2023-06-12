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

/*年龄范围管理业务逻辑层*/
public class AgeRangeService {
	/* 添加年龄范围 */
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

	/* 查询年龄范围 */
	public List<AgeRange> QueryAgeRange(AgeRange queryConditionAgeRange) throws Exception {
		String urlString = HttpUtil.BASE_URL + "AgeRangeServlet?action=query";
		if(queryConditionAgeRange != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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

	/* 更新年龄范围 */
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

	/* 删除年龄范围 */
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
			return "年龄范围信息删除失败!";
		}
	}

	/* 根据年龄范围id获取年龄范围对象 */
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
