package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Coach;
import com.mobileclient.util.HttpUtil;

/*私教管理业务逻辑层*/
public class CoachService {
	/* 添加私教 */
	public String AddCoach(Coach coach) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("coachUserName", coach.getCoachUserName());
		params.put("password", coach.getPassword());
		params.put("name", coach.getName());
		params.put("sex", coach.getSex());
		params.put("ageRangeObj", coach.getAgeRangeObj() + "");
		params.put("age", coach.getAge() + "");
		params.put("telephone", coach.getTelephone());
		params.put("cityObj", coach.getCityObj());
		params.put("nowStateObj", coach.getNowStateObj() + "");
		params.put("priceRangeObj", coach.getPriceRangeObj() + "");
		params.put("price", coach.getPrice() + "");
		params.put("coachPhoto", coach.getCoachPhoto());
		params.put("coachDesc", coach.getCoachDesc());
		params.put("shzt", coach.getShzt());
		params.put("regTime", coach.getRegTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CoachServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询私教 */
	public List<Coach> QueryCoach(Coach queryConditionCoach) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CoachServlet?action=query";
		if(queryConditionCoach != null) {
			urlString += "&coachUserName=" + URLEncoder.encode(queryConditionCoach.getCoachUserName(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionCoach.getName(), "UTF-8") + "";
			urlString += "&sex=" + URLEncoder.encode(queryConditionCoach.getSex(), "UTF-8") + "";
			urlString += "&ageRangeObj=" + queryConditionCoach.getAgeRangeObj();
			urlString += "&cityObj=" + URLEncoder.encode(queryConditionCoach.getCityObj(), "UTF-8") + "";
			urlString += "&nowStateObj=" + queryConditionCoach.getNowStateObj();
			urlString += "&priceRangeObj=" + queryConditionCoach.getPriceRangeObj();
			urlString += "&shzt=" + URLEncoder.encode(queryConditionCoach.getShzt(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CoachListHandler coachListHander = new CoachListHandler();
		xr.setContentHandler(coachListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Coach> coachList = coachListHander.getCoachList();
		return coachList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Coach> coachList = new ArrayList<Coach>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Coach coach = new Coach();
				coach.setCoachUserName(object.getString("coachUserName"));
				coach.setPassword(object.getString("password"));
				coach.setName(object.getString("name"));
				coach.setSex(object.getString("sex"));
				coach.setAgeRangeObj(object.getInt("ageRangeObj"));
				coach.setAge(object.getInt("age"));
				coach.setTelephone(object.getString("telephone"));
				coach.setCityObj(object.getString("cityObj"));
				coach.setNowStateObj(object.getInt("nowStateObj"));
				coach.setPriceRangeObj(object.getInt("priceRangeObj"));
				coach.setPrice(object.getInt("price"));
				coach.setCoachPhoto(object.getString("coachPhoto"));
				coach.setCoachDesc(object.getString("coachDesc"));
				coach.setShzt(object.getString("shzt"));
				coach.setRegTime(object.getString("regTime"));
				coachList.add(coach);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coachList;
	}

	/* 更新私教 */
	public String UpdateCoach(Coach coach) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("coachUserName", coach.getCoachUserName());
		params.put("password", coach.getPassword());
		params.put("name", coach.getName());
		params.put("sex", coach.getSex());
		params.put("ageRangeObj", coach.getAgeRangeObj() + "");
		params.put("age", coach.getAge() + "");
		params.put("telephone", coach.getTelephone());
		params.put("cityObj", coach.getCityObj());
		params.put("nowStateObj", coach.getNowStateObj() + "");
		params.put("priceRangeObj", coach.getPriceRangeObj() + "");
		params.put("price", coach.getPrice() + "");
		params.put("coachPhoto", coach.getCoachPhoto());
		params.put("coachDesc", coach.getCoachDesc());
		params.put("shzt", coach.getShzt());
		params.put("regTime", coach.getRegTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CoachServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除私教 */
	public String DeleteCoach(String coachUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("coachUserName", coachUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CoachServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "私教信息删除失败!";
		}
	}

	/* 根据用户名获取私教对象 */
	public Coach GetCoach(String coachUserName)  {
		List<Coach> coachList = new ArrayList<Coach>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("coachUserName", coachUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CoachServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Coach coach = new Coach();
				coach.setCoachUserName(object.getString("coachUserName"));
				coach.setPassword(object.getString("password"));
				coach.setName(object.getString("name"));
				coach.setSex(object.getString("sex"));
				coach.setAgeRangeObj(object.getInt("ageRangeObj"));
				coach.setAge(object.getInt("age"));
				coach.setTelephone(object.getString("telephone"));
				coach.setCityObj(object.getString("cityObj"));
				coach.setNowStateObj(object.getInt("nowStateObj"));
				coach.setPriceRangeObj(object.getInt("priceRangeObj"));
				coach.setPrice(object.getInt("price"));
				coach.setCoachPhoto(object.getString("coachPhoto"));
				coach.setCoachDesc(object.getString("coachDesc"));
				coach.setShzt(object.getString("shzt"));
				coach.setRegTime(object.getString("regTime"));
				coachList.add(coach);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = coachList.size();
		if(size>0) return coachList.get(0); 
		else return null; 
	}
}
