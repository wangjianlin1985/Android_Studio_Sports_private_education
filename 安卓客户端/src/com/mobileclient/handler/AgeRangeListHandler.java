package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.AgeRange;
public class AgeRangeListHandler extends DefaultHandler {
	private List<AgeRange> ageRangeList = null;
	private AgeRange ageRange;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (ageRange != null) { 
            String valueString = new String(ch, start, length); 
            if ("arId".equals(tempString)) 
            	ageRange.setArId(new Integer(valueString).intValue());
            else if ("startAge".equals(tempString)) 
            	ageRange.setStartAge(new Integer(valueString).intValue());
            else if ("endAge".equals(tempString)) 
            	ageRange.setEndAge(new Integer(valueString).intValue());
            else if ("showText".equals(tempString)) 
            	ageRange.setShowText(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("AgeRange".equals(localName)&&ageRange!=null){
			ageRangeList.add(ageRange);
			ageRange = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		ageRangeList = new ArrayList<AgeRange>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("AgeRange".equals(localName)) {
            ageRange = new AgeRange(); 
        }
        tempString = localName; 
	}

	public List<AgeRange> getAgeRangeList() {
		return this.ageRangeList;
	}
}
