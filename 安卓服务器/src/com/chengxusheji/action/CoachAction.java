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

	/*ͼƬ���ļ��ֶ�coachPhoto��������*/
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
    /*�������Ҫ��ѯ������: �û���*/
    private String coachUserName;
    public void setCoachUserName(String coachUserName) {
        this.coachUserName = coachUserName;
    }
    public String getCoachUserName() {
        return this.coachUserName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: �Ա�*/
    private String sex;
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return this.sex;
    }

    /*�������Ҫ��ѯ������: ���䷶Χ*/
    private AgeRange ageRangeObj;
    public void setAgeRangeObj(AgeRange ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }
    public AgeRange getAgeRangeObj() {
        return this.ageRangeObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*�������Ҫ��ѯ������: ��״̬*/
    private NowState nowStateObj;
    public void setNowStateObj(NowState nowStateObj) {
        this.nowStateObj = nowStateObj;
    }
    public NowState getNowStateObj() {
        return this.nowStateObj;
    }

    /*�������Ҫ��ѯ������: �շѼ۸�����*/
    private PriceRange priceRangeObj;
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }
    public PriceRange getPriceRangeObj() {
        return this.priceRangeObj;
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
    @Resource AgeRangeDAO ageRangeDAO;
    @Resource CityDAO cityDAO;
    @Resource NowStateDAO nowStateDAO;
    @Resource PriceRangeDAO priceRangeDAO;
    @Resource CoachDAO coachDAO;

    /*��������Coach����*/
    private Coach coach;
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    public Coach getCoach() {
        return this.coach;
    }

    /*��ת�����Coach��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�AgeRange��Ϣ*/
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAllAgeRangeInfo();
        ctx.put("ageRangeList", ageRangeList);
        /*��ѯ���е�City��Ϣ*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        /*��ѯ���е�NowState��Ϣ*/
        List<NowState> nowStateList = nowStateDAO.QueryAllNowStateInfo();
        ctx.put("nowStateList", nowStateList);
        /*��ѯ���е�PriceRange��Ϣ*/
        List<PriceRange> priceRangeList = priceRangeDAO.QueryAllPriceRangeInfo();
        ctx.put("priceRangeList", priceRangeList);
        return "add_view";
    }

    /*���Coach��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCoach() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�û����Ƿ��Ѿ�����*/
        String coachUserName = coach.getCoachUserName();
        Coach db_coach = coachDAO.GetCoachByCoachUserName(coachUserName);
        if(null != db_coach) {
            ctx.put("error",  java.net.URLEncoder.encode("���û����Ѿ�����!"));
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
            /*���������Ƭ�ϴ�*/
            String coachPhotoPath = "upload/noimage.jpg"; 
       	 	if(coachPhotoFile != null)
       	 		coachPhotoPath = photoUpload(coachPhotoFile,coachPhotoFileContentType);
       	 	coach.setCoachPhoto(coachPhotoPath);
            coachDAO.AddCoach(coach);
            ctx.put("message",  java.net.URLEncoder.encode("Coach��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coach���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCoach��Ϣ*/
    public String QueryCoach() {
        if(currentPage == 0) currentPage = 1;
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        coachDAO.CalculateTotalPageAndRecordNumber(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = coachDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryCoachOutputToExcel() { 
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName,name,sex,ageRangeObj,cityObj,nowStateObj,priceRangeObj,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Coach��Ϣ��¼"; 
        String[] headers = { "�û���","����","�Ա�","���䷶Χ","����","�ֻ���","����","��״̬","�շѼ۸�����","�۸�(Ԫ/Сʱ)","������Ƭ","���״̬","ע��ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Coach.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCoach��Ϣ*/
    public String FrontQueryCoach() {
        if(currentPage == 0) currentPage = 1;
        if(coachUserName == null) coachUserName = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(shzt == null) shzt = "";
        List<Coach> coachList = coachDAO.QueryCoachInfo(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        coachDAO.CalculateTotalPageAndRecordNumber(coachUserName, name, sex, ageRangeObj, cityObj, nowStateObj, priceRangeObj, shzt);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = coachDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Coach��Ϣ*/
    public String ModifyCoachQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������coachUserName��ȡCoach����*/
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

    /*��ѯҪ�޸ĵ�Coach��Ϣ*/
    public String FrontShowCoachQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������coachUserName��ȡCoach����*/
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

    /*�����޸�Coach��Ϣ*/
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
            /*���������Ƭ�ϴ�*/
            if(coachPhotoFile != null) {
            	String coachPhotoPath = photoUpload(coachPhotoFile,coachPhotoFileContentType);
            	coach.setCoachPhoto(coachPhotoPath);
            }
            coachDAO.UpdateCoach(coach);
            ctx.put("message",  java.net.URLEncoder.encode("Coach��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coach��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Coach��Ϣ*/
    public String DeleteCoach() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            coachDAO.DeleteCoach(coachUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Coachɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Coachɾ��ʧ��!"));
            return "error";
        }
    }

}
