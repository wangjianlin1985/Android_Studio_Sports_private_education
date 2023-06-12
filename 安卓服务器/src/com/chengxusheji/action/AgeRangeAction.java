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
import com.chengxusheji.dao.AgeRangeDAO;
import com.chengxusheji.domain.AgeRange;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class AgeRangeAction extends BaseAction {

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

    private int arId;
    public void setArId(int arId) {
        this.arId = arId;
    }
    public int getArId() {
        return arId;
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

    /*��������AgeRange����*/
    private AgeRange ageRange;
    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }
    public AgeRange getAgeRange() {
        return this.ageRange;
    }

    /*��ת�����AgeRange��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���AgeRange��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ageRangeDAO.AddAgeRange(ageRange);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRange��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRange���ʧ��!"));
            return "error";
        }
    }

    /*��ѯAgeRange��Ϣ*/
    public String QueryAgeRange() {
        if(currentPage == 0) currentPage = 1;
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        ageRangeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = ageRangeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = ageRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("ageRangeList",  ageRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryAgeRangeOutputToExcel() { 
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "AgeRange��Ϣ��¼"; 
        String[] headers = { "���䷶Χid","��ʼ����","��������","��ʾ��Ϣ"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<ageRangeList.size();i++) {
        	AgeRange ageRange = ageRangeList.get(i); 
        	dataset.add(new String[]{ageRange.getArId() + "",ageRange.getStartAge() + "",ageRange.getEndAge() + "",ageRange.getShowText()});
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
			response.setHeader("Content-disposition","attachment; filename="+"AgeRange.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯAgeRange��Ϣ*/
    public String FrontQueryAgeRange() {
        if(currentPage == 0) currentPage = 1;
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        ageRangeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = ageRangeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = ageRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("ageRangeList",  ageRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�AgeRange��Ϣ*/
    public String ModifyAgeRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������arId��ȡAgeRange����*/
        AgeRange ageRange = ageRangeDAO.GetAgeRangeByArId(arId);

        ctx.put("ageRange",  ageRange);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�AgeRange��Ϣ*/
    public String FrontShowAgeRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������arId��ȡAgeRange����*/
        AgeRange ageRange = ageRangeDAO.GetAgeRangeByArId(arId);

        ctx.put("ageRange",  ageRange);
        return "front_show_view";
    }

    /*�����޸�AgeRange��Ϣ*/
    public String ModifyAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ageRangeDAO.UpdateAgeRange(ageRange);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRange��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRange��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��AgeRange��Ϣ*/
    public String DeleteAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            ageRangeDAO.DeleteAgeRange(arId);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRangeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRangeɾ��ʧ��!"));
            return "error";
        }
    }

}
