package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Manager;
import com.mobileclient.util.HttpUtil;

/*��ͨ����Ա����ҵ���߼���*/
public class ManagerService {
	/* �����ͨ����Ա */
	public String AddManager(Manager manager) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("managerUserName", manager.getManagerUserName());
		params.put("password", manager.getPassword());
		params.put("name", manager.getName());
		params.put("sex", manager.getSex());
		params.put("birthDate", manager.getBirthDate().toString());
		params.put("telephone", manager.getTelephone());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ManagerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��ͨ����Ա */
	public List<Manager> QueryManager(Manager queryConditionManager) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ManagerServlet?action=query";
		if(queryConditionManager != null) {
			urlString += "&managerUserName=" + URLEncoder.encode(queryConditionManager.getManagerUserName(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionManager.getName(), "UTF-8") + "";
			if(queryConditionManager.getBirthDate() != null) {
				urlString += "&birthDate=" + URLEncoder.encode(queryConditionManager.getBirthDate().toString(), "UTF-8");
			}
			urlString += "&telephone=" + URLEncoder.encode(queryConditionManager.getTelephone(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ManagerListHandler managerListHander = new ManagerListHandler();
		xr.setContentHandler(managerListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Manager> managerList = managerListHander.getManagerList();
		return managerList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Manager> managerList = new ArrayList<Manager>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Manager manager = new Manager();
				manager.setManagerUserName(object.getString("managerUserName"));
				manager.setPassword(object.getString("password"));
				manager.setName(object.getString("name"));
				manager.setSex(object.getString("sex"));
				manager.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				manager.setTelephone(object.getString("telephone"));
				managerList.add(manager);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return managerList;
	}

	/* ������ͨ����Ա */
	public String UpdateManager(Manager manager) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("managerUserName", manager.getManagerUserName());
		params.put("password", manager.getPassword());
		params.put("name", manager.getName());
		params.put("sex", manager.getSex());
		params.put("birthDate", manager.getBirthDate().toString());
		params.put("telephone", manager.getTelephone());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ManagerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����ͨ����Ա */
	public String DeleteManager(String managerUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("managerUserName", managerUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ManagerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��ͨ����Ա��Ϣɾ��ʧ��!";
		}
	}

	/* ���ݹ���Ա�û�����ȡ��ͨ����Ա���� */
	public Manager GetManager(String managerUserName)  {
		List<Manager> managerList = new ArrayList<Manager>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("managerUserName", managerUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ManagerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Manager manager = new Manager();
				manager.setManagerUserName(object.getString("managerUserName"));
				manager.setPassword(object.getString("password"));
				manager.setName(object.getString("name"));
				manager.setSex(object.getString("sex"));
				manager.setBirthDate(Timestamp.valueOf(object.getString("birthDate")));
				manager.setTelephone(object.getString("telephone"));
				managerList.add(manager);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = managerList.size();
		if(size>0) return managerList.get(0); 
		else return null; 
	}
}
