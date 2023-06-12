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
import com.chengxusheji.domain.AgeRange;
import com.chengxusheji.domain.City;
import com.chengxusheji.domain.NowState;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.domain.Coach;

@Service @Transactional
public class CoachDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddCoach(Coach coach) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(coach);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coach> QueryCoachInfo(String coachUserName,String name,String sex,AgeRange ageRangeObj,City cityObj,NowState nowStateObj,PriceRange priceRangeObj,String shzt,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Coach coach where 1=1";
    	if(!coachUserName.equals("")) hql = hql + " and coach.coachUserName like '%" + coachUserName + "%'";
    	if(!name.equals("")) hql = hql + " and coach.name like '%" + name + "%'";
    	if(!sex.equals("")) hql = hql + " and coach.sex like '%" + sex + "%'";
    	if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and coach.ageRangeObj.arId=" + ageRangeObj.getArId();
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and coach.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and coach.nowStateObj.stateId=" + nowStateObj.getStateId();
    	if(null != priceRangeObj && priceRangeObj.getPrId()!=0) hql += " and coach.priceRangeObj.prId=" + priceRangeObj.getPrId();
    	if(!shzt.equals("")) hql = hql + " and coach.shzt like '%" + shzt + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List coachList = q.list();
    	return (ArrayList<Coach>) coachList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coach> QueryCoachInfo(String coachUserName,String name,String sex,AgeRange ageRangeObj,City cityObj,NowState nowStateObj,PriceRange priceRangeObj,String shzt) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Coach coach where 1=1";
    	if(!coachUserName.equals("")) hql = hql + " and coach.coachUserName like '%" + coachUserName + "%'";
    	if(!name.equals("")) hql = hql + " and coach.name like '%" + name + "%'";
    	if(!sex.equals("")) hql = hql + " and coach.sex like '%" + sex + "%'";
    	if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and coach.ageRangeObj.arId=" + ageRangeObj.getArId();
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and coach.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and coach.nowStateObj.stateId=" + nowStateObj.getStateId();
    	if(null != priceRangeObj && priceRangeObj.getPrId()!=0) hql += " and coach.priceRangeObj.prId=" + priceRangeObj.getPrId();
    	if(!shzt.equals("")) hql = hql + " and coach.shzt like '%" + shzt + "%'";
    	Query q = s.createQuery(hql);
    	List coachList = q.list();
    	return (ArrayList<Coach>) coachList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Coach> QueryAllCoachInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Coach";
        Query q = s.createQuery(hql);
        List coachList = q.list();
        return (ArrayList<Coach>) coachList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String coachUserName,String name,String sex,AgeRange ageRangeObj,City cityObj,NowState nowStateObj,PriceRange priceRangeObj,String shzt) {
        Session s = factory.getCurrentSession();
        String hql = "From Coach coach where 1=1";
        if(!coachUserName.equals("")) hql = hql + " and coach.coachUserName like '%" + coachUserName + "%'";
        if(!name.equals("")) hql = hql + " and coach.name like '%" + name + "%'";
        if(!sex.equals("")) hql = hql + " and coach.sex like '%" + sex + "%'";
        if(null != ageRangeObj && ageRangeObj.getArId()!=0) hql += " and coach.ageRangeObj.arId=" + ageRangeObj.getArId();
        if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and coach.cityObj.cityNo='" + cityObj.getCityNo() + "'";
        if(null != nowStateObj && nowStateObj.getStateId()!=0) hql += " and coach.nowStateObj.stateId=" + nowStateObj.getStateId();
        if(null != priceRangeObj && priceRangeObj.getPrId()!=0) hql += " and coach.priceRangeObj.prId=" + priceRangeObj.getPrId();
        if(!shzt.equals("")) hql = hql + " and coach.shzt like '%" + shzt + "%'";
        Query q = s.createQuery(hql);
        List coachList = q.list();
        recordNumber = coachList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Coach GetCoachByCoachUserName(String coachUserName) {
        Session s = factory.getCurrentSession();
        Coach coach = (Coach)s.get(Coach.class, coachUserName);
        return coach;
    }

    /*更新Coach信息*/
    public void UpdateCoach(Coach coach) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(coach);
    }

    /*删除Coach信息*/
    public void DeleteCoach (String coachUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object coach = s.load(Coach.class, coachUserName);
        s.delete(coach);
    }

}
