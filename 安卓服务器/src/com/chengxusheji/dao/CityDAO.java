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

@Service @Transactional
public class CityDAO {

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
    public void AddCity(City city) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(city);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<City> QueryCityInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From City city where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List cityList = q.list();
    	return (ArrayList<City>) cityList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<City> QueryCityInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From City city where 1=1";
    	Query q = s.createQuery(hql);
    	List cityList = q.list();
    	return (ArrayList<City>) cityList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<City> QueryAllCityInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From City";
        Query q = s.createQuery(hql);
        List cityList = q.list();
        return (ArrayList<City>) cityList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From City city where 1=1";
        Query q = s.createQuery(hql);
        List cityList = q.list();
        recordNumber = cityList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public City GetCityByCityNo(String cityNo) {
        Session s = factory.getCurrentSession();
        City city = (City)s.get(City.class, cityNo);
        return city;
    }

    /*更新City信息*/
    public void UpdateCity(City city) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(city);
    }

    /*删除City信息*/
    public void DeleteCity (String cityNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object city = s.load(City.class, cityNo);
        s.delete(city);
    }

}
