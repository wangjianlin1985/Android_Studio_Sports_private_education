package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Coach;
public class CoachListHandler extends DefaultHandler {
	private List<Coach> coachList = null;
	private Coach coach;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (coach != null) { 
            String valueString = new String(ch, start, length); 
            if ("coachUserName".equals(tempString)) 
            	coach.setCoachUserName(valueString); 
            else if ("password".equals(tempString)) 
            	coach.setPassword(valueString); 
            else if ("name".equals(tempString)) 
            	coach.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	coach.setSex(valueString); 
            else if ("ageRangeObj".equals(tempString)) 
            	coach.setAgeRangeObj(new Integer(valueString).intValue());
            else if ("age".equals(tempString)) 
            	coach.setAge(new Integer(valueString).intValue());
            else if ("telephone".equals(tempString)) 
            	coach.setTelephone(valueString); 
            else if ("cityObj".equals(tempString)) 
            	coach.setCityObj(valueString); 
            else if ("nowStateObj".equals(tempString)) 
            	coach.setNowStateObj(new Integer(valueString).intValue());
            else if ("priceRangeObj".equals(tempString)) 
            	coach.setPriceRangeObj(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	coach.setPrice(new Integer(valueString).intValue());
            else if ("coachPhoto".equals(tempString)) 
            	coach.setCoachPhoto(valueString); 
            else if ("coachDesc".equals(tempString)) 
            	coach.setCoachDesc(valueString); 
            else if ("shzt".equals(tempString)) 
            	coach.setShzt(valueString); 
            else if ("regTime".equals(tempString)) 
            	coach.setRegTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Coach".equals(localName)&&coach!=null){
			coachList.add(coach);
			coach = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		coachList = new ArrayList<Coach>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Coach".equals(localName)) {
            coach = new Coach(); 
        }
        tempString = localName; 
	}

	public List<Coach> getCoachList() {
		return this.coachList;
	}
}
