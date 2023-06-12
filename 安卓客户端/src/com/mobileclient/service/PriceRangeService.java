package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.PriceRange;
import com.mobileclient.util.HttpUtil;

/*�۸�Χ����ҵ���߼���*/
public class PriceRangeService {
	/* ��Ӽ۸�Χ */
	public String AddPriceRange(PriceRange priceRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("prId", priceRange.getPrId() + "");
		params.put("startPrice", priceRange.getStartPrice() + "");
		params.put("endPrice", priceRange.getEndPrice() + "");
		params.put("showText", priceRange.getShowText());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�۸�Χ */
	public List<PriceRange> QueryPriceRange(PriceRange queryConditionPriceRange) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PriceRangeServlet?action=query";
		if(queryConditionPriceRange != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PriceRangeListHandler priceRangeListHander = new PriceRangeListHandler();
		xr.setContentHandler(priceRangeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<PriceRange> priceRangeList = priceRangeListHander.getPriceRangeList();
		return priceRangeList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PriceRange priceRange = new PriceRange();
				priceRange.setPrId(object.getInt("prId"));
				priceRange.setStartPrice(object.getInt("startPrice"));
				priceRange.setEndPrice(object.getInt("endPrice"));
				priceRange.setShowText(object.getString("showText"));
				priceRangeList.add(priceRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceRangeList;
	}

	/* ���¼۸�Χ */
	public String UpdatePriceRange(PriceRange priceRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("prId", priceRange.getPrId() + "");
		params.put("startPrice", priceRange.getStartPrice() + "");
		params.put("endPrice", priceRange.getEndPrice() + "");
		params.put("showText", priceRange.getShowText());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���۸�Χ */
	public String DeletePriceRange(int prId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("prId", prId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�۸�Χ��Ϣɾ��ʧ��!";
		}
	}

	/* ���ݼ۸�Χid��ȡ�۸�Χ���� */
	public PriceRange GetPriceRange(int prId)  {
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("prId", prId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PriceRange priceRange = new PriceRange();
				priceRange.setPrId(object.getInt("prId"));
				priceRange.setStartPrice(object.getInt("startPrice"));
				priceRange.setEndPrice(object.getInt("endPrice"));
				priceRange.setShowText(object.getString("showText"));
				priceRangeList.add(priceRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = priceRangeList.size();
		if(size>0) return priceRangeList.get(0); 
		else return null; 
	}
}
