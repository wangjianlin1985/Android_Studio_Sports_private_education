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

    
    /*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean CheckLogin(Admin admin) { 
		Session s = factory.getCurrentSession();  
		Manager db_manager = (Manager)s.get(Manager.class, admin.getUsername());
		if(db_manager == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_manager.getPassword().equals(admin.getPassword())) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	
	
	
    /*添加图书信息*/
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
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Manager GetManagerByManagerUserName(String managerUserName) {
        Session s = factory.getCurrentSession();
        Manager manager = (Manager)s.get(Manager.class, managerUserName);
        return manager;
    }

    /*更新Manager信息*/
    public void UpdateManager(Manager manager) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(manager);
    }

    /*删除Manager信息*/
    public void DeleteManager (String managerUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object manager = s.load(Manager.class, managerUserName);
        s.delete(manager);
    }

}
