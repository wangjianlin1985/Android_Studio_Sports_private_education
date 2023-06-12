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
    public void AddNowState(NowState nowState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(nowState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NowState> QueryNowStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NowState nowState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public NowState GetNowStateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        NowState nowState = (NowState)s.get(NowState.class, stateId);
        return nowState;
    }

    /*更新NowState信息*/
    public void UpdateNowState(NowState nowState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(nowState);
    }

    /*删除NowState信息*/
    public void DeleteNowState (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object nowState = s.load(NowState.class, stateId);
        s.delete(nowState);
    }

}
