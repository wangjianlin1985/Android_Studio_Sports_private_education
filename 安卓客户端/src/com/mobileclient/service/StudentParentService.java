package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.StudentParent;
import com.mobileclient.util.HttpUtil;

/*ѧ���ҳ�����ҵ���߼���*/
public class StudentParentService {
	/* ���ѧ���ҳ� */
	public String AddStudentParent(StudentParent studentParent) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("spUserName", studentParent.getSpUserName());
		params.put("password", studentParent.getPassword());
		params.put("parentName", studentParent.getParentName());
		params.put("telephone", studentParent.getTelephone());
		params.put("cityObj", studentParent.getCityObj());
		params.put("studentSex", studentParent.getStudentSex());
		params.put("ageRangeObj", studentParent.getAgeRangeObj() + "");
		params.put("age", studentParent.getAge() + "");
		params.put("school", studentParent.getSchool());
		params.put("nowStateObj", studentParent.getNowStateObj() + "");
		params.put("studentPhoto", studentParent.getStudentPhoto());
		params.put("studentDesc", studentParent.getStudentDesc());
		params.put("shzt", studentParent.getShzt());
		params.put("regTime", studentParent.getRegTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentParentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯѧ���ҳ� */
	public List<StudentParent> QueryStudentParent(StudentParent queryConditionStudentParent) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StudentParentServlet?action=query";
		if(queryConditionStudentParent != null) {
			urlString += "&spUserName=" + URLEncoder.encode(queryConditionStudentParent.getSpUserName(), "UTF-8") + "";
			urlString += "&parentName=" + URLEncoder.encode(queryConditionStudentParent.getParentName(), "UTF-8") + "";
			urlString += "&cityObj=" + URLEncoder.encode(queryConditionStudentParent.getCityObj(), "UTF-8") + "";
			urlString += "&studentSex=" + URLEncoder.encode(queryConditionStudentParent.getStudentSex(), "UTF-8") + "";
			urlString += "&ageRangeObj=" + queryConditionStudentParent.getAgeRangeObj();
			urlString += "&school=" + URLEncoder.encode(queryConditionStudentParent.getSchool(), "UTF-8") + "";
			urlString += "&nowStateObj=" + queryConditionStudentParent.getNowStateObj();
			urlString += "&shzt=" + URLEncoder.encode(queryConditionStudentParent.getShzt(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StudentParentListHandler studentParentListHander = new StudentParentListHandler();
		xr.setContentHandler(studentParentListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<StudentParent> studentParentList = studentParentListHander.getStudentParentList();
		return studentParentList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<StudentParent> studentParentList = new ArrayList<StudentParent>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StudentParent studentParent = new StudentParent();
				studentParent.setSpUserName(object.getString("spUserName"));
				studentParent.setPassword(object.getString("password"));
				studentParent.setParentName(object.getString("parentName"));
				studentParent.setTelephone(object.getString("telephone"));
				studentParent.setCityObj(object.getString("cityObj"));
				studentParent.setStudentSex(object.getString("studentSex"));
				studentParent.setAgeRangeObj(object.getInt("ageRangeObj"));
				studentParent.setAge(object.getInt("age"));
				studentParent.setSchool(object.getString("school"));
				studentParent.setNowStateObj(object.getInt("nowStateObj"));
				studentParent.setStudentPhoto(object.getString("studentPhoto"));
				studentParent.setStudentDesc(object.getString("studentDesc"));
				studentParent.setShzt(object.getString("shzt"));
				studentParent.setRegTime(object.getString("regTime"));
				studentParentList.add(studentParent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentParentList;
	}

	/* ����ѧ���ҳ� */
	public String UpdateStudentParent(StudentParent studentParent) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("spUserName", studentParent.getSpUserName());
		params.put("password", studentParent.getPassword());
		params.put("parentName", studentParent.getParentName());
		params.put("telephone", studentParent.getTelephone());
		params.put("cityObj", studentParent.getCityObj());
		params.put("studentSex", studentParent.getStudentSex());
		params.put("ageRangeObj", studentParent.getAgeRangeObj() + "");
		params.put("age", studentParent.getAge() + "");
		params.put("school", studentParent.getSchool());
		params.put("nowStateObj", studentParent.getNowStateObj() + "");
		params.put("studentPhoto", studentParent.getStudentPhoto());
		params.put("studentDesc", studentParent.getStudentDesc());
		params.put("shzt", studentParent.getShzt());
		params.put("regTime", studentParent.getRegTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentParentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ѧ���ҳ� */
	public String DeleteStudentParent(String spUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("spUserName", spUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentParentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ѧ���ҳ���Ϣɾ��ʧ��!";
		}
	}

	/* �����û�����ȡѧ���ҳ����� */
	public StudentParent GetStudentParent(String spUserName)  {
		List<StudentParent> studentParentList = new ArrayList<StudentParent>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("spUserName", spUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StudentParentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StudentParent studentParent = new StudentParent();
				studentParent.setSpUserName(object.getString("spUserName"));
				studentParent.setPassword(object.getString("password"));
				studentParent.setParentName(object.getString("parentName"));
				studentParent.setTelephone(object.getString("telephone"));
				studentParent.setCityObj(object.getString("cityObj"));
				studentParent.setStudentSex(object.getString("studentSex"));
				studentParent.setAgeRangeObj(object.getInt("ageRangeObj"));
				studentParent.setAge(object.getInt("age"));
				studentParent.setSchool(object.getString("school"));
				studentParent.setNowStateObj(object.getInt("nowStateObj"));
				studentParent.setStudentPhoto(object.getString("studentPhoto"));
				studentParent.setStudentDesc(object.getString("studentDesc"));
				studentParent.setShzt(object.getString("shzt"));
				studentParent.setRegTime(object.getString("regTime"));
				studentParentList.add(studentParent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = studentParentList.size();
		if(size>0) return studentParentList.get(0); 
		else return null; 
	}
}
