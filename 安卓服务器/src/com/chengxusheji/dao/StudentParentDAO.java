package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.City;
import com.chengxusheji.domain.AgeRange;
import com.chengxusheji.domain.NowState;
import com.chengxusheji.domain.StudentParent;

@Service @Transactional
public class StudentParentDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddStudentParent(StudentParent studentParent) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(studentParent);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentParent> QueryStudentParentInfo(String spUserName,String parentName,City cityObj,String studentSex,AgeRange ageRangeObj,String school,NowState nowStateObj,String shzt,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StudentParent studentParent where 1=1";
    	if(!spUserName.equals("")) hql = hql + " and studentParent.spUserName like '%" + spUserName + "%'";
    	if(!parentName.equals("")) hql = hql + " and studentParent.parentName like '%" + parentName + "%'";
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and studentParent.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(!studentSex.equals("")) hql = hql + " and studentParent.studentSex like '%" + studentSex + "%'";
    	if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and studentParent.ageRangeObj.arId=" + ageRangeObj.getArId();
    	if(!school.equals("")) hql = hql + " and studentParent.school like '%" + school + "%'";
    	if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and studentParent.nowStateObj.stateId=" + nowStateObj.getStateId();
    	if(!shzt.equals("")) hql = hql + " and studentParent.shzt like '%" + shzt + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List studentParentList = q.list();
    	return (ArrayList<StudentParent>) studentParentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentParent> QueryStudentParentInfo(String spUserName,String parentName,City cityObj,String studentSex,AgeRange ageRangeObj,String school,NowState nowStateObj,String shzt) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StudentParent studentParent where 1=1";
    	if(!spUserName.equals("")) hql = hql + " and studentParent.spUserName like '%" + spUserName + "%'";
    	if(!parentName.equals("")) hql = hql + " and studentParent.parentName like '%" + parentName + "%'";
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and studentParent.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(!studentSex.equals("")) hql = hql + " and studentParent.studentSex like '%" + studentSex + "%'";
    	if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and studentParent.ageRangeObj.arId=" + ageRangeObj.getArId();
    	if(!school.equals("")) hql = hql + " and studentParent.school like '%" + school + "%'";
    	if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and studentParent.nowStateObj.stateId=" + nowStateObj.getStateId();
    	if(!shzt.equals("")) hql = hql + " and studentParent.shzt like '%" + shzt + "%'";
    	Query q = s.createQuery(hql);
    	List studentParentList = q.list();
    	return (ArrayList<StudentParent>) studentParentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StudentParent> QueryAllStudentParentInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From StudentParent";
        Query q = s.createQuery(hql);
        List studentParentList = q.list();
        return (ArrayList<StudentParent>) studentParentList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String spUserName,String parentName,City cityObj,String studentSex,AgeRange ageRangeObj,String school,NowState nowStateObj,String shzt) {
        Session s = factory.getCurrentSession();
        String hql = "From StudentParent studentParent where 1=1";
        if(!spUserName.equals("")) hql = hql + " and studentParent.spUserName like '%" + spUserName + "%'";
        if(!parentName.equals("")) hql = hql + " and studentParent.parentName like '%" + parentName + "%'";
        if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and studentParent.cityObj.cityNo='" + cityObj.getCityNo() + "'";
        if(!studentSex.equals("")) hql = hql + " and studentParent.studentSex like '%" + studentSex + "%'";
        if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and studentParent.ageRangeObj.arId=" + ageRangeObj.getArId();
        if(!school.equals("")) hql = hql + " and studentParent.school like '%" + school + "%'";
        if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and studentParent.nowStateObj.stateId=" + nowStateObj.getStateId();
        if(!shzt.equals("")) hql = hql + " and studentParent.shzt like '%" + shzt + "%'";
        Query q = s.createQuery(hql);
        List studentParentList = q.list();
        recordNumber = studentParentList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StudentParent GetStudentParentBySpUserName(String spUserName) {
        Session s = factory.getCurrentSession();
        StudentParent studentParent = (StudentParent)s.get(StudentParent.class, spUserName);
        return studentParent;
    }

    /*����StudentParent��Ϣ*/
    public void UpdateStudentParent(StudentParent studentParent) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(studentParent);
    }

    /*ɾ��StudentParent��Ϣ*/
    public void DeleteStudentParent (String spUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object studentParent = s.load(StudentParent.class, spUserName);
        s.delete(studentParent);
    }

}
