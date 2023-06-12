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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource NowStateDAO nowStateDAO;

    /*��������NowState����*/
    private NowState nowState;
    public void setNowState(NowState nowState) {
        this.nowState = nowState;
    }
    public NowState getNowState() {
        return this.nowState;
    }

    /*��ת�����NowState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���NowState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNowState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            nowStateDAO.AddNowState(nowState);
            ctx.put("message",  java.net.URLEncoder.encode("NowState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNowState��Ϣ*/
    public String QueryNowState() {
        if(currentPage == 0) currentPage = 1;
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        nowStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = nowStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = nowStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("nowStateList",  nowStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNowStateOutputToExcel() { 
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NowState��Ϣ��¼"; 
        String[] headers = { "״̬id","״̬����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"NowState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNowState��Ϣ*/
    public String FrontQueryNowState() {
        if(currentPage == 0) currentPage = 1;
        List<NowState> nowStateList = nowStateDAO.QueryNowStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        nowStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = nowStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = nowStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("nowStateList",  nowStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�NowState��Ϣ*/
    public String ModifyNowStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡNowState����*/
        NowState nowState = nowStateDAO.GetNowStateByStateId(stateId);

        ctx.put("nowState",  nowState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�NowState��Ϣ*/
    public String FrontShowNowStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡNowState����*/
        NowState nowState = nowStateDAO.GetNowStateByStateId(stateId);

        ctx.put("nowState",  nowState);
        return "front_show_view";
    }

    /*�����޸�NowState��Ϣ*/
    public String ModifyNowState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            nowStateDAO.UpdateNowState(nowState);
            ctx.put("message",  java.net.URLEncoder.encode("NowState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��NowState��Ϣ*/
    public String DeleteNowState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            nowStateDAO.DeleteNowState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("NowStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NowStateɾ��ʧ��!"));
            return "error";
        }
    }

}
