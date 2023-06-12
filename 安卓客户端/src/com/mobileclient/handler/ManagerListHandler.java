package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Manager;
public class ManagerListHandler extends DefaultHandler {
	private List<Manager> managerList = null;
	private Manager manager;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (manager != null) { 
            String valueString = new String(ch, start, length); 
            if ("managerUserName".equals(tempString)) 
            	manager.setManagerUserName(valueString); 
            else if ("password".equals(tempString)) 
            	manager.setPassword(valueString); 
            else if ("name".equals(tempString)) 
            	manager.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	manager.setSex(valueString); 
            else if ("birthDate".equals(tempString)) 
            	manager.setBirthDate(Timestamp.valueOf(valueString));
            else if ("telephone".equals(tempString)) 
            	manager.setTelephone(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Manager".equals(localName)&&manager!=null){
			managerList.add(manager);
			manager = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		managerList = new ArrayList<Manager>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Manager".equals(localName)) {
            manager = new Manager(); 
        }
        tempString = localName; 
	}

	public List<Manager> getManagerList() {
		return this.managerList;
	}
}
