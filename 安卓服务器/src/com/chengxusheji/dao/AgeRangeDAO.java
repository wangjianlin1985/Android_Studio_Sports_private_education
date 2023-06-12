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

@Service @Transactional
public class AgeRangeDAO {

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
    public void AddAgeRange(AgeRange ageRange) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(ageRange);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AgeRange> QueryAgeRangeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From AgeRange ageRange where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List ageRangeList = q.list();
    	return (ArrayList<AgeRange>) ageRangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AgeRange> QueryAgeRangeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From AgeRange ageRange where 1=1";
    	Query q = s.createQuery(hql);
    	List ageRangeList = q.list();
    	return (ArrayList<AgeRange>) ageRangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AgeRange> QueryAllAgeRangeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From AgeRange";
        Query q = s.createQuery(hql);
        List ageRangeList = q.list();
        return (ArrayList<AgeRange>) ageRangeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From AgeRange ageRange where 1=1";
        Query q = s.createQuery(hql);
        List ageRangeList = q.list();
        recordNumber = ageRangeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public AgeRange GetAgeRangeByArId(int arId) {
        Session s = factory.getCurrentSession();
        AgeRange ageRange = (AgeRange)s.get(AgeRange.class, arId);
        return ageRange;
    }

    /*更新AgeRange信息*/
    public void UpdateAgeRange(AgeRange ageRange) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(ageRange);
    }

    /*删除AgeRange信息*/
    public void DeleteAgeRange (int arId) throws Exception {
        Session s = factory.getCurrentSession();
        Object ageRange = s.load(AgeRange.class, arId);
        s.delete(ageRange);
    }

}
