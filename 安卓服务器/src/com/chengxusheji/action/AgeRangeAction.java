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

    private int arId;
    public void setArId(int arId) {
        this.arId = arId;
    }
    public int getArId() {
        return arId;
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

    /*待操作的AgeRange对象*/
    private AgeRange ageRange;
    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }
    public AgeRange getAgeRange() {
        return this.ageRange;
    }

    /*跳转到添加AgeRange视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加AgeRange信息*/
    @SuppressWarnings("deprecation")
    public String AddAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ageRangeDAO.AddAgeRange(ageRange);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRange添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRange添加失败!"));
            return "error";
        }
    }

    /*查询AgeRange信息*/
    public String QueryAgeRange() {
        if(currentPage == 0) currentPage = 1;
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        ageRangeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = ageRangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = ageRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("ageRangeList",  ageRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryAgeRangeOutputToExcel() { 
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "AgeRange信息记录"; 
        String[] headers = { "年龄范围id","开始年龄","结束年龄","显示信息"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"AgeRange.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询AgeRange信息*/
    public String FrontQueryAgeRange() {
        if(currentPage == 0) currentPage = 1;
        List<AgeRange> ageRangeList = ageRangeDAO.QueryAgeRangeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        ageRangeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = ageRangeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = ageRangeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("ageRangeList",  ageRangeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的AgeRange信息*/
    public String ModifyAgeRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键arId获取AgeRange对象*/
        AgeRange ageRange = ageRangeDAO.GetAgeRangeByArId(arId);

        ctx.put("ageRange",  ageRange);
        return "modify_view";
    }

    /*查询要修改的AgeRange信息*/
    public String FrontShowAgeRangeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键arId获取AgeRange对象*/
        AgeRange ageRange = ageRangeDAO.GetAgeRangeByArId(arId);

        ctx.put("ageRange",  ageRange);
        return "front_show_view";
    }

    /*更新修改AgeRange信息*/
    public String ModifyAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ageRangeDAO.UpdateAgeRange(ageRange);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRange信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRange信息更新失败!"));
            return "error";
       }
   }

    /*删除AgeRange信息*/
    public String DeleteAgeRange() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            ageRangeDAO.DeleteAgeRange(arId);
            ctx.put("message",  java.net.URLEncoder.encode("AgeRange删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("AgeRange删除失败!"));
            return "error";
        }
    }

}
