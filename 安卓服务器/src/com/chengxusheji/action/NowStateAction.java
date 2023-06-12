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
import com.chengxusheji.dao.NowStateDAO;
import com.chengxusheji.domain.NowState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NowStateAction extends BaseAction {

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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource NowStateDAO nowStateDAO;

    /*待操作的NowState对象*/
    private NowState nowState;
    public void setNowState(NowState nowState) {
        this.nowState = nowState;
    }
    public NowState getNowState() {
        return this.nowState;
    }

    /*跳转到添加NowState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加NowState信息*/
    @SuppressWarnings("deprecation")
    public String AddNowState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            nowStateDAO.AddNowState(nowState);
            ctx.put("message",  java.net.URLEncoder.encode("NowState添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowState添加失败!"));
            return "error";
        }
    }

    /*查询NowState信息*/
    public String QueryNowState() {
        if(currentPage == 0) currentPage = 1;
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        nowStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = nowStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = nowStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("nowStateList",  nowStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNowStateOutputToExcel() { 
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NowState信息记录"; 
        String[] headers = { "状态id","状态名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<nowStateList.size();i++) {
        	NowState nowState = nowStateList.get(i); 
        	dataset.add(new String[]{nowState.getStateId() + "",nowState.getStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"NowState.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询NowState信息*/
    public String FrontQueryNowState() {
        if(currentPage == 0) currentPage = 1;
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        nowStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = nowStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = nowStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("nowStateList",  nowStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的NowState信息*/
    public String ModifyNowStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取NowState对象*/
        NowState nowState = nowStateDAO.GetNowStateByStateId(stateId);

        ctx.put("nowState",  nowState);
        return "modify_view";
    }

    /*查询要修改的NowState信息*/
    public String FrontShowNowStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取NowState对象*/
        NowState nowState = nowStateDAO.GetNowStateByStateId(stateId);

        ctx.put("nowState",  nowState);
        return "front_show_view";
    }

    /*更新修改NowState信息*/
    public String ModifyNowState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            nowStateDAO.UpdateNowState(nowState);
            ctx.put("message",  java.net.URLEncoder.encode("NowState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowState信息更新失败!"));
            return "error";
       }
   }

    /*删除NowState信息*/
    public String DeleteNowState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            nowStateDAO.DeleteNowState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("NowState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowState删除失败!"));
            return "error";
        }
    }

}
