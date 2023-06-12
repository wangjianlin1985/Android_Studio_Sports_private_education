package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.NowState;
public class NowStateListHandler extends DefaultHandler {
	private List<NowState> nowStateList = null;
	private NowState nowState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (nowState != null) { 
            String valueString = new String(ch, start, length); 
            if ("stateId".equals(tempString)) 
            	nowState.setStateId(new Integer(valueString).intValue());
            else if ("stateName".equals(tempString)) 
            	nowState.setStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("NowState".equals(localName)&&nowState!=null){
			nowStateList.add(nowState);
			nowState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		nowStateList = new ArrayList<NowState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("NowState".equals(localName)) {
            nowState = new NowState(); 
        }
        tempString = localName; 
	}

	public List<NowState> getNowStateList() {
		return this.nowStateList;
	}
}
