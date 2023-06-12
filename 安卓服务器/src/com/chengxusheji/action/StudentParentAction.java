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

	/*ͼƬ���ļ��ֶ�studentPhoto��������*/
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
    /*�������Ҫ��ѯ������: �û���*/
    private String spUserName;
    public void setSpUserName(String spUserName) {
        this.spUserName = spUserName;
    }
    public String getSpUserName() {
        return this.spUserName;
    }

    /*�������Ҫ��ѯ������: �ҳ�����*/
    private String parentName;
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getParentName() {
        return this.parentName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*�������Ҫ��ѯ������: ѧ���Ա�*/
    private String studentSex;
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }
    public String getStudentSex() {
        return this.studentSex;
    }

    /*�������Ҫ��ѯ������: ���䷶Χ*/
    private AgeRange ageRangeObj;
    public void setAgeRangeObj(AgeRange ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }
    public AgeRange getAgeRangeObj() {
        return this.ageRangeObj;
    }

    /*�������Ҫ��ѯ������: ѧ��ѧУ*/
    private String school;
    public void setSchool(String school) {
        this.school = school;
    }
    public String getSchool() {
        return this.school;
    }

    /*�������Ҫ��ѯ������: ��״̬*/
    private NowState nowStateObj;
    public void setNowStateObj(NowState nowStateObj) {
        this.nowStateObj = nowStateObj;
    }
    public NowState getNowStateObj() {
        return this.nowStateObj;
    }

    /*�������Ҫ��ѯ������: ���״̬*/
    private String shzt;
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }
    public String getShzt() {
        return this.shzt;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource CityDAO cityDAO;
    @Resource AgeRangeDAO ageRangeDAO;
    @Resource NowStateDAO nowStateDAO;
    @Resource StudentParentDAO studentParentDAO;

    /*��������StudentParent����*/
    private StudentParent studentParent;
    public void setStudentParent(StudentParent studentParent) {
        this.studentParent = studentParent;
    }
    public StudentParent getStudentParent() {
        return this.studentParent;
    }

    /*��ת�����StudentParent��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�City��Ϣ*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        /*��ѯ���е�AgeRange��Ϣ*/
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        /*��ѯ���е�NowState��Ϣ*/
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        return "add_view";
    }

    /*���StudentParent��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�û����Ƿ��Ѿ�����*/
        String spUserName = studentParent.getSpUserName();
        StudentParent db_studentParent = studentParentDAO.GetStudentParentBySpUserName(spUserName);
        if(null != db_studentParent) {
            ctx.put("error",  java.net.URLEncoder.encode("���û����Ѿ�����!"));
            return "error";
        }
        try {
            City cityObj = cityDAO.GetCityByCityNo(studentParent.getCityObj().getCityNo());
            studentParent.setCityObj(cityObj);
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(studentParent.getAgeRangeObj().getArId());
            studentParent.setAgeRangeObj(ageRangeObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(studentParent.getNowStateObj().getStateId());
            studentParent.setNowStateObj(nowStateObj);
            /*����ѧ����Ƭ�ϴ�*/
            String studentPhotoPath = "upload/noimage.jpg"; 
       	 	if(studentPhotoFile != null)
       	 		studentPhotoPath = photoUpload(studentPhotoFile,studentPhotoFileContentType);
       	 	studentParent.setStudentPhoto(studentPhotoPath);
            studentParentDAO.AddStudentParent(studentParent);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParent��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParent���ʧ��!"));
            return "error";
        }
    }

    /*��ѯStudentParent��Ϣ*/
    public String QueryStudentParent() {
        if(currentPage == 0) currentPage = 1;
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentParentDAO.CalculateTotalPageAndRecordNumber(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentParentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryStudentParentOutputToExcel() { 
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName,parentName,cityObj,studentSex,ageRangeObj,school,nowStateObj,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StudentParent��Ϣ��¼"; 
        String[] headers = { "�û���","��¼����","�ҳ�����","�ֻ���","����","ѧ���Ա�","���䷶Χ","ѧ������","ѧ��ѧУ","��״̬","ѧ����Ƭ","���״̬","ע��ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"StudentParent.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯStudentParent��Ϣ*/
    public String FrontQueryStudentParent() {
        if(currentPage == 0) currentPage = 1;
        if(spUserName == null) spUserName = "";
        if(parentName == null) parentName = "";
        if(studentSex == null) studentSex = "";
        if(school == null) school = "";
        if(shzt == null) shzt = "";
        List<StudentParent> studentParentList = studentParentDAO.QueryStudentParentInfo(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentParentDAO.CalculateTotalPageAndRecordNumber(spUserName, parentName, cityObj, studentSex, ageRangeObj, school, nowStateObj, shzt);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentParentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�StudentParent��Ϣ*/
    public String ModifyStudentParentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������spUserName��ȡStudentParent����*/
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

    /*��ѯҪ�޸ĵ�StudentParent��Ϣ*/
    public String FrontShowStudentParentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������spUserName��ȡStudentParent����*/
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

    /*�����޸�StudentParent��Ϣ*/
    public String ModifyStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        try {
            City cityObj = cityDAO.GetCityByCityNo(studentParent.getCityObj().getCityNo());
            studentParent.setCityObj(cityObj);
            AgeRange ageRangeObj = ageRangeDAO.GetAgeRangeByArId(studentParent.getAgeRangeObj().getArId());
            studentParent.setAgeRangeObj(ageRangeObj);
            NowState nowStateObj = nowStateDAO.GetNowStateByStateId(studentParent.getNowStateObj().getStateId());
            studentParent.setNowStateObj(nowStateObj);
            /*����ѧ����Ƭ�ϴ�*/
            if(studentPhotoFile != null) {
            	String studentPhotoPath = photoUpload(studentPhotoFile,studentPhotoFileContentType);
            	studentParent.setStudentPhoto(studentPhotoPath);
            }
            studentParentDAO.UpdateStudentParent(studentParent);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParent��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParent��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��StudentParent��Ϣ*/
    public String DeleteStudentParent() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentParentDAO.DeleteStudentParent(spUserName);
            ctx.put("message",  java.net.URLEncoder.encode("StudentParentɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentParentɾ��ʧ��!"));
            return "error";
        }
    }

}
