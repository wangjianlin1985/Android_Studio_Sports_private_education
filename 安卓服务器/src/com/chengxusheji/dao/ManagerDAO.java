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

import com.chengxusheji.domain.Admin;
import com.chengxusheji.domain.Manager;

@Service @Transactional
public class ManagerDAO {

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

    
    /*����ҵ���߼�������Ϣ�ֶ�*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*��֤�û���¼*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean CheckLogin(Admin admin) { 
		Session s = factory.getCurrentSession();  
		Manager db_manager = (Manager)s.get(Manager.class, admin.getUsername());
		if(db_manager == null) { 
			this.errMessage = " �˺Ų����� ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_manager.getPassword().equals(admin.getPassword())) {
			this.errMessage = " ���벻��ȷ! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	
	
	
    /*���ͼ����Ϣ*/
    public void AddManager(Manager manager) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(manager);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Manager> QueryManagerInfo(String managerUserName,String name,String birthDate,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Manager manager where 1=1";
    	if(!managerUserName.equals("")) hql = hql + " and manager.managerUserName like '%" + managerUserName + "%'";
    	if(!name.equals("")) hql = hql + " and manager.name like '%" + name + "%'";
    	if(!birthDate.equals("")) hql = hql + " and manager.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) hql = hql + " and manager.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List managerList = q.list();
    	return (ArrayList<Manager>) managerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Manager> QueryManagerInfo(String managerUserName,String name,String birthDate,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Manager manager where 1=1";
    	if(!managerUserName.equals("")) hql = hql + " and manager.managerUserName like '%" + managerUserName + "%'";
    	if(!name.equals("")) hql = hql + " and manager.name like '%" + name + "%'";
    	if(!birthDate.equals("")) hql = hql + " and manager.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) hql = hql + " and manager.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List managerList = q.list();
    	return (ArrayList<Manager>) managerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Manager> QueryAllManagerInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Manager";
        Query q = s.createQuery(hql);
        List managerList = q.list();
        return (ArrayList<Manager>) managerList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String managerUserName,String name,String birthDate,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Manager manager where 1=1";
        if(!managerUserName.equals("")) hql = hql + " and manager.managerUserName like '%" + managerUserName + "%'";
        if(!name.equals("")) hql = hql + " and manager.name like '%" + name + "%'";
        if(!birthDate.equals("")) hql = hql + " and manager.birthDate like '%" + birthDate + "%'";
        if(!telephone.equals("")) hql = hql + " and manager.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List managerList = q.list();
        recordNumber = managerList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Manager GetManagerByManagerUserName(String managerUserName) {
        Session s = factory.getCurrentSession();
        Manager manager = (Manager)s.get(Manager.class, managerUserName);
        return manager;
    }

    /*����Manager��Ϣ*/
    public void UpdateManager(Manager manager) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(manager);
    }

    /*ɾ��Manager��Ϣ*/
    public void DeleteManager (String managerUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object manager = s.load(Manager.class, managerUserName);
        s.delete(manager);
    }

}
