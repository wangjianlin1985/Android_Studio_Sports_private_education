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
import com.chengxusheji.dao.StudentParentDAO;
import com.chengxusheji.domain.StudentParent;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.dao.AgeRangeDAO;
import com.chengxusheji.domain.AgeRange;
import com.chengxusheji.dao.NowStateDAO;
import com.chengxusheji.domain.NowState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class StudentParentAction extends BaseAction {

	/*图片或文件字段studentPhoto参数接收*/
	private File studentPhotoFile;
	private String studentPhotoFileFileName;
	private String studentPhotoFileContentType;
	public File getStudentPhotoFile() {
		return studentPhotoFile;
	}
	public void setStudentPhotoFile(File studentPhotoFile) {
		this.studentPhotoFile = studentPhotoFile;
	}
	public String getStudentPhotoFileFileName() {
		return studentPhotoFileFileName;
	}
	public void setStudentPhotoFileFileName(String studentPhotoFileFileName) {
		this.studentPhotoFileFileName = studentPhotoFileFileName;
	}
	public String getStudentPhotoFileContentType() {
		return studentPhotoFileContentType;
	}
	public void setStudentPhotoFileContentType(String studentPhotoFileContentType) {
		this.studentPhotoFileContentType = studentPhotoFileContentType;
	}
    /*界面层需要查询的属性: 用户名*/
    private String spUserName;
    public void setSpUserName(String spUserName) {
        this.spUserName = spUserName;
    }
    public String getSpUserName() {
        return this.spUserName;
    }

    /*界面层需要查询的属性: 家长姓名*/
    private String parentName;
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getParentName() {
        return this.parentName;
    }

    /*界面层需要查询的属性: 城市*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*界面层需要查询的属性: 学生性别*/
    private String studentSex;
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }
    public String getStudentSex() {
        return this.studentSex;
    }

    /*界面层需要查询的属性: 年龄范围*/
    private AgeRange ageRangeObj;
    public void setAgeRangeObj(AgeRange ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }
    public AgeRange getAgeRangeObj() {
        return this.ageRangeObj;
    }

    /*界面层需要查询的属性: 学生学校*/
    private String school;
    public void setSchool(String school) {
        this.school = school;
    }
    public String getSchool() {
        return this.school;
    }

    /*界面层需要查询的属性: 现状态*/
    private NowState nowStateObj;
    public void setNowStateObj(NowState nowStateObj) {
        this.nowStateObj = nowStateObj;
    }
    public NowState getNowStateObj() {
        return this.nowStateObj;
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
    @Resource CityDAO cityDAO;
    @Resource AgeRangeDAO ageRangeDAO;
    @Resource NowStateDAO nowStateDAO;
    @Resource StudentParentDAO studentParentDAO;

    /*待操作的StudentParent对象*/
    private StudentParent studentParent;
    public void setStudentParent(StudentParent studentParent) {
        this.studentParent = studentParent;
    }
    public StudentParent getStudentParent() {
        return this.studentParent;
    }

    /*跳转到添加StudentParent视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的City信息*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        /*查询所有的AgeRange信息*/
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        /*查询所有的NowState信息*/
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        return "add_view";
    }

    /*添加StudentParent信息*/
    @SuppressWarnings("deprecation")
    public String AddStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        /*验证用户名是否已经存在*/
        String spUserName = studentParent.getSpUserName();
        StudentParent db_studentParent = studentParentDAO.GetStudentParentBySpUserName(spUserName);
        if(null != db_studentParent) {
            ctx.put("error",  java.net.URLEncoder.encode("该用户名已经存在!"));
            return "error";
        }
        try {
            City cityObj = cityDAO.GetCityByCityNo(studentParent.getCityObj().getCityNo());
            studentParent.setCityObj(cityObj);
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(studentParent.getAgeRangeObj().getArId());
            studentParent.setAgeRangeObj(ageRangeObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(studentParent.getNowStateObj().getStateId());
            studentParent.setNowStateObj(nowStateObj);
            /*处理学生照片上传*/
            String studentPhotoPath = "upload/noimage.jpg"; 
       	 	if(studentPhotoFile != null)
       	 		studentPhotoPath = photoUpload(studentPhotoFile,studentPhotoFileContentType);
       	 	studentParent.setStudentPhoto(studentPhotoPath);
            studentParentDAO.AddStudentParent(studentParent);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParent添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParent添加失败!"));
            return "error";
        }
    }

    /*查询StudentParent信息*/
    public String QueryStudentParent() {
        if(currentPage == 0) currentPage = 1;
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt, currentPage);
        /*计算总的页数和总的记录数*/
        studentParentDAO.CalculateTotalPageAndRecordNumber(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt);
        /*获取到总的页码数目*/
        totalPage = studentParentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentParentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentParentList",  studentParentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("spUserName", spUserName);
        ctx.put("parentName", parentName);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("studentSex", studentSex);
        ctx.put("ageRangeObj", ageRangeObj);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        ctx.put("school", school);
        ctx.put("nowStateObj", nowStateObj);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("shzt", shzt);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryStudentParentOutputToExcel() { 
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName,parentName,cityObj,studentSex,ageRangeObj,school,nowStateObj,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StudentParent信息记录"; 
        String[] headers = { "用户名","登录密码","家长姓名","手机号","城市","学生性别","年龄范围","学生年龄","学生学校","现状态","学生照片","审核状态","注册时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<studentParentList.size();i++) {
        	StudentParent studentParent = studentParentList.get(i); 
        	dataset.add(new String[]{studentParent.getSpUserName(),studentParent.getPassword(),studentParent.getParentName(),studentParent.getTelephone(),studentParent.getCityObj().getCityName(),
studentParent.getStudentSex(),studentParent.getAgeRangeObj().getShowText(),
studentParent.getAge() + "",studentParent.getSchool(),studentParent.getNowStateObj().getStateName(),
studentParent.getStudentPhoto(),studentParent.getShzt(),studentParent.getRegTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"StudentParent.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询StudentParent信息*/
    public String FrontQueryStudentParent() {
        if(currentPage == 0) currentPage = 1;
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt, currentPage);
        /*计算总的页数和总的记录数*/
        studentParentDAO.CalculateTotalPageAndRecordNumber(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt);
        /*获取到总的页码数目*/
        totalPage = studentParentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentParentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentParentList",  studentParentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("spUserName", spUserName);
        ctx.put("parentName", parentName);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("studentSex", studentSex);
        ctx.put("ageRangeObj", ageRangeObj);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        ctx.put("school", school);
        ctx.put("nowStateObj", nowStateObj);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("shzt", shzt);
        return "front_query_view";
    }

    /*查询要修改的StudentParent信息*/
    public String ModifyStudentParentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键spUserName获取StudentParent对象*/
        StudentParent studentParent = studentParentDAO.GetStudentParentBySpUserName(spUserName);

        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("studentParent",  studentParent);
        return "modify_view";
    }

    /*查询要修改的StudentParent信息*/
    public String FrontShowStudentParentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键spUserName获取StudentParent对象*/
        StudentParent studentParent = studentParentDAO.GetStudentParentBySpUserName(spUserName);

        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        ctx.put("studentParent",  studentParent);
        return "front_show_view";
    }

    /*更新修改StudentParent信息*/
    public String ModifyStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        try {
            City cityObj = cityDAO.GetCityByCityNo(studentParent.getCityObj().getCityNo());
            studentParent.setCityObj(cityObj);
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(studentParent.getAgeRangeObj().getArId());
            studentParent.setAgeRangeObj(ageRangeObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(studentParent.getNowStateObj().getStateId());
            studentParent.setNowStateObj(nowStateObj);
            /*处理学生照片上传*/
            if(studentPhotoFile != null) {
            	String studentPhotoPath = photoUpload(studentPhotoFile,studentPhotoFileContentType);
            	studentParent.setStudentPhoto(studentPhotoPath);
            }
            studentParentDAO.UpdateStudentParent(studentParent);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParent信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParent信息更新失败!"));
            return "error";
       }
   }

    /*删除StudentParent信息*/
    public String DeleteStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentParentDAO.DeleteStudentParent(spUserName);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParent删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParent删除失败!"));
            return "error";
        }
    }

}
