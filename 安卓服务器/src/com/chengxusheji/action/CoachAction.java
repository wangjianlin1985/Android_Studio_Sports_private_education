package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CoachDAO;
import com.chengxusheji.domain.Coach;
import com.chengxusheji.dao.AgeRangeDAO;
import com.chengxusheji.domain.AgeRange;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.dao.NowStateDAO;
import com.chengxusheji.domain.NowState;
import com.chengxusheji.dao.PriceRangeDAO;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CoachAction extends BaseAction {

	/*图片或文件字段coachPhoto参数接收*/
	private File coachPhotoFile;
	private String coachPhotoFileFileName;
	private String coachPhotoFileContentType;
	public File getCoachPhotoFile() {
		return coachPhotoFile;
	}
	public void setCoachPhotoFile(File coachPhotoFile) {
		this.coachPhotoFile = coachPhotoFile;
	}
	public String getCoachPhotoFileFileName() {
		return coachPhotoFileFileName;
	}
	public void setCoachPhotoFileFileName(String coachPhotoFileFileName) {
		this.coachPhotoFileFileName = coachPhotoFileFileName;
	}
	public String getCoachPhotoFileContentType() {
		return coachPhotoFileContentType;
	}
	public void setCoachPhotoFileContentType(String coachPhotoFileContentType) {
		this.coachPhotoFileContentType = coachPhotoFileContentType;
	}
    /*界面层需要查询的属性: 用户名*/
    private String coachUserName;
    public void setCoachUserName(String coachUserName) {
        this.coachUserName = coachUserName;
    }
    public String getCoachUserName() {
        return this.coachUserName;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 性别*/
    private String sex;
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return this.sex;
    }

    /*界面层需要查询的属性: 年龄范围*/
    private AgeRange ageRangeObj;
    public void setAgeRangeObj(AgeRange ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }
    public AgeRange getAgeRangeObj() {
        return this.ageRangeObj;
    }

    /*界面层需要查询的属性: 城市*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*界面层需要查询的属性: 现状态*/
    private NowState nowStateObj;
    public void setNowStateObj(NowState nowStateObj) {
        this.nowStateObj = nowStateObj;
    }
    public NowState getNowStateObj() {
        return this.nowStateObj;
    }

    /*界面层需要查询的属性: 收费价格区间*/
    private PriceRange priceRangeObj;
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }
    public PriceRange getPriceRangeObj() {
        return this.priceRangeObj;
    }

    /*界面层需要查询的属性: 审核状态*/
    private String shzt;
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }
    public String getShzt() {
        return this.shzt;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource AgeRangeDAO ageRangeDAO;
    @Resource CityDAO cityDAO;
    @Resource NowStateDAO nowStateDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource CoachDAO coachDAO;

    /*待操作的Coach对象*/
    private Coach coach;
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    public Coach getCoach() {
        return this.coach;
    }

    /*跳转到添加Coach视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的AgeRange信息*/
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        /*查询所有的City信息*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        /*查询所有的NowState信息*/
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        /*查询所有的PriceRange信息*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*添加Coach信息*/
    @SuppressWarnings("deprecation")
    public String AddCoach() {
        ActionContext ctx = ActionContext.getContext();
        /*验证用户名是否已经存在*/
        String coachUserName = coach.getCoachUserName();
        Coach db_coach = coachDAO.GetCoachByCoachUserName(coachUserName);
        if(null != db_coach) {
            ctx.put("error",  java.net.URLEncoder.encode("该用户名已经存在!"));
            return "error";
        }
        try {
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(coach.getAgeRangeObj().getArId());
            coach.setAgeRangeObj(ageRangeObj);
            City cityObj = cityDAO.GetCityByCityNo(coach.getCityObj().getCityNo());
            coach.setCityObj(cityObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(coach.getNowStateObj().getStateId());
            coach.setNowStateObj(nowStateObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByPrId(coach.getPriceRangeObj().getPrId());
            coach.setPriceRangeObj(priceRangeObj);
            /*处理教练照片上传*/
            String coachPhotoPath = "upload/noimage.jpg"; 
       	 	if(coachPhotoFile != null)
       	 		coachPhotoPath = photoUpload(coachPhotoFile,coachPhotoFileContentType);
       	 	coach.setCoachPhoto(coachPhotoPath);
            coachDAO.AddCoach(coach);
            ctx.put("message",  java.net.URLEncoder.encode("Coach添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coach添加失败!"));
            return "error";
        }
    }

    /*查询Coach信息*/
    public String QueryCoach() {
        if(currentPage == 0) currentPage = 1;
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt, currentPage);
        /*计算总的页数和总的记录数*/
        coachDAO.CalculateTotalPageAndRecordNumber(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt);
        /*获取到总的页码数目*/
        totalPage = coachDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = coachDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("coachList",  coachList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("coachUserName", coachUserName);
        ctx.put("name", name);
        ctx.put("sex", sex);
        ctx.put("ageRangeObj", ageRangeObj);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("nowStateObj", nowStateObj);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("shzt", shzt);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCoachOutputToExcel() { 
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName,name,sex,ageRangeObj,cityObj,nowStateObj,priceRangeObj,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Coach信息记录"; 
        String[] headers = { "用户名","姓名","性别","年龄范围","年龄","手机号","城市","现状态","收费价格区间","价格(元/小时)","教练照片","审核状态","注册时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<coachList.size();i++) {
        	Coach coach = coachList.get(i); 
        	dataset.add(new String[]{coach.getCoachUserName(),coach.getName(),coach.getSex(),coach.getAgeRangeObj().getShowText(),
coach.getAge() + "",coach.getTelephone(),coach.getCityObj().getCityName(),
coach.getNowStateObj().getStateName(),
coach.getPriceRangeObj().getShowText(),
coach.getPrice() + "",coach.getCoachPhoto(),coach.getShzt(),coach.getRegTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Coach.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Coach信息*/
    public String FrontQueryCoach() {
        if(currentPage == 0) currentPage = 1;
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt, currentPage);
        /*计算总的页数和总的记录数*/
        coachDAO.CalculateTotalPageAndRecordNumber(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt);
        /*获取到总的页码数目*/
        totalPage = coachDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = coachDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("coachList",  coachList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("coachUserName", coachUserName);
        ctx.put("name", name);
        ctx.put("sex", sex);
        ctx.put("ageRangeObj", ageRangeObj);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("nowStateObj", nowStateObj);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("priceRangeObj", priceRangeObj);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("shzt", shzt);
        return "front_query_view";
    }

    /*查询要修改的Coach信息*/
    public String ModifyCoachQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键coachUserName获取Coach对象*/
        Coach coach = coachDAO.GetCoachByCoachUserName(coachUserName);

        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("coach",  coach);
        return "modify_view";
    }

    /*查询要修改的Coach信息*/
    public String FrontShowCoachQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键coachUserName获取Coach对象*/
        Coach coach = coachDAO.GetCoachByCoachUserName(coachUserName);

        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        ctx.put("coach",  coach);
        return "front_show_view";
    }

    /*更新修改Coach信息*/
    public String ModifyCoach() {
        ActionContext ctx = ActionContext.getContext();
        try {
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(coach.getAgeRangeObj().getArId());
            coach.setAgeRangeObj(ageRangeObj);
            City cityObj = cityDAO.GetCityByCityNo(coach.getCityObj().getCityNo());
            coach.setCityObj(cityObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(coach.getNowStateObj().getStateId());
            coach.setNowStateObj(nowStateObj);
            PriceRange priceRangeObj = priceRangeDAO.GetPriceRangeByPrId(coach.getPriceRangeObj().getPrId());
            coach.setPriceRangeObj(priceRangeObj);
            /*处理教练照片上传*/
            if(coachPhotoFile != null) {
            	String coachPhotoPath = photoUpload(coachPhotoFile,coachPhotoFileContentType);
            	coach.setCoachPhoto(coachPhotoPath);
            }
            coachDAO.UpdateCoach(coach);
            ctx.put("message",  java.net.URLEncoder.encode("Coach信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coach信息更新失败!"));
            return "error";
       }
   }

    /*删除Coach信息*/
    public String DeleteCoach() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            coachDAO.DeleteCoach(coachUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Coach删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coach删除失败!"));
            return "error";
        }
    }

}
