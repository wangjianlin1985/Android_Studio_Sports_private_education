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
    public void AddAgeRange(AgeRange ageRange) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(ageRange);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AgeRange> QueryAgeRangeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From AgeRange ageRange where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public AgeRange GetAgeRangeByArId(int arId) {
        Session s = factory.getCurrentSession();
        AgeRange ageRange = (AgeRange)s.get(AgeRange.class, arId);
        return ageRange;
    }

    /*����AgeRange��Ϣ*/
    public void UpdateAgeRange(AgeRange ageRange) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(ageRange);
    }

    /*ɾ��AgeRange��Ϣ*/
    public void DeleteAgeRange (int arId) throws Exception {
        Session s = factory.getCurrentSession();
        Object ageRange = s.load(AgeRange.class, arId);
        s.delete(ageRange);
    }

}
