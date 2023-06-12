package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.StudentParent;
public class StudentParentListHandler extends DefaultHandler {
	private List<StudentParent> studentParentList = null;
	private StudentParent studentParent;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (studentParent != null) { 
            String valueString = new String(ch, start, length); 
            if ("spUserName".equals(tempString)) 
            	studentParent.setSpUserName(valueString); 
            else if ("password".equals(tempString)) 
            	studentParent.setPassword(valueString); 
            else if ("parentName".equals(tempString)) 
            	studentParent.setParentName(valueString); 
            else if ("telephone".equals(tempString)) 
            	studentParent.setTelephone(valueString); 
            else if ("cityObj".equals(tempString)) 
            	studentParent.setCityObj(valueString); 
            else if ("studentSex".equals(tempString)) 
            	studentParent.setStudentSex(valueString); 
            else if ("ageRangeObj".equals(tempString)) 
            	studentParent.setAgeRangeObj(new Integer(valueString).intValue());
            else if ("age".equals(tempString)) 
            	studentParent.setAge(new Integer(valueString).intValue());
            else if ("school".equals(tempString)) 
            	studentParent.setSchool(valueString); 
            else if ("nowStateObj".equals(tempString)) 
            	studentParent.setNowStateObj(new Integer(valueString).intValue());
            else if ("studentPhoto".equals(tempString)) 
            	studentParent.setStudentPhoto(valueString); 
            else if ("studentDesc".equals(tempString)) 
            	studentParent.setStudentDesc(valueString); 
            else if ("shzt".equals(tempString)) 
            	studentParent.setShzt(valueString); 
            else if ("regTime".equals(tempString)) 
            	studentParent.setRegTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("StudentParent".equals(localName)&&studentParent!=null){
			studentParentList.add(studentParent);
			studentParent = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		studentParentList = new ArrayList<StudentParent>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("StudentParent".equals(localName)) {
            studentParent = new StudentParent(); 
        }
        tempString = localName; 
	}

	public List<StudentParent> getStudentParentList() {
		return this.studentParentList;
	}
}
