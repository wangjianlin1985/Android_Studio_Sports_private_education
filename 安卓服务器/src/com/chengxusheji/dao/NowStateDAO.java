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
import com.chengxusheji.domain.NowState;

@Service @Transactional
public class NowStateDAO {

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
    public void AddNowState(NowState nowState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(nowState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NowState> QueryNowStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NowState nowState where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List nowStateList = q.list();
    	return (ArrayList<NowState>) nowStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NowState> QueryNowStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NowState nowState where 1=1";
    	Query q = s.createQuery(hql);
    	List nowStateList = q.list();
    	return (ArrayList<NowState>) nowStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NowState> QueryAllNowStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From NowState";
        Query q = s.createQuery(hql);
        List nowStateList = q.list();
        return (ArrayList<NowState>) nowStateList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From NowState nowState where 1=1";
        Query q = s.createQuery(hql);
        List nowStateList = q.list();
        recordNumber = nowStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public NowState GetNowStateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        NowState nowState = (NowState)s.get(NowState.class, stateId);
        return nowState;
    }

    /*����NowState��Ϣ*/
    public void UpdateNowState(NowState nowState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(nowState);
    }

    /*ɾ��NowState��Ϣ*/
    public void DeleteNowState (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object nowState = s.load(NowState.class, stateId);
        s.delete(nowState);
    }

}
