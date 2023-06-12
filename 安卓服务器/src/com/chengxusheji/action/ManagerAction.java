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
import com.chengxusheji.dao.ManagerDAO;
import com.chengxusheji.domain.Manager;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ManagerAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����Ա�û���*/
    private String managerUserName;
    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }
    public String getManagerUserName() {
        return this.managerUserName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String birthDate;
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthDate() {
        return this.birthDate;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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
    @Resource ManagerDAO managerDAO;

    /*��������Manager����*/
    private Manager manager;
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    public Manager getManager() {
        return this.manager;
    }

    /*��ת�����Manager��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Manager��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddManager() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤����Ա�û����Ƿ��Ѿ�����*/
        String managerUserName = manager.getManagerUserName();
        Manager db_manager = managerDAO.GetManagerByManagerUserName(managerUserName);
        if(null != db_manager) {
            ctx.put("error",  java.net.URLEncoder.encode("�ù���Ա�û����Ѿ�����!"));
            return "error";
        }
        try {
            managerDAO.AddManager(manager);
            ctx.put("message",  java.net.URLEncoder.encode("Manager��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Manager���ʧ��!"));
            return "error";
        }
    }

    /*��ѯManager��Ϣ*/
    public String QueryManager() {
        if(currentPage == 0) currentPage = 1;
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName, name, birthDate, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        managerDAO.CalculateTotalPageAndRecordNumber(managerUserName, name, birthDate, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = managerDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = managerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("managerList",  managerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("managerUserName", managerUserName);
        ctx.put("name", name);
        ctx.put("birthDate", birthDate);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryManagerOutputToExcel() { 
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName,name,birthDate,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Manager��Ϣ��¼"; 
        String[] headers = { "����Ա�û���","����","�Ա�","��������","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<managerList.size();i++) {
        	Manager manager = managerList.get(i); 
        	dataset.add(new String[]{manager.getManagerUserName(),manager.getName(),manager.getSex(),new SimpleDateFormat("yyyy-MM-dd").format(manager.getBirthDate()),manager.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Manager.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯManager��Ϣ*/
    public String FrontQueryManager() {
        if(currentPage == 0) currentPage = 1;
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName, name, birthDate, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        managerDAO.CalculateTotalPageAndRecordNumber(managerUserName, name, birthDate, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = managerDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = managerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("managerList",  managerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("managerUserName", managerUserName);
        ctx.put("name", name);
        ctx.put("birthDate", birthDate);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Manager��Ϣ*/
    public String ModifyManagerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������managerUserName��ȡManager����*/
        Manager manager = managerDAO.GetManagerByManagerUserName(managerUserName);

        ctx.put("manager",  manager);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Manager��Ϣ*/
    public String FrontShowManagerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������managerUserName��ȡManager����*/
        Manager manager = managerDAO.GetManagerByManagerUserName(managerUserName);

        ctx.put("manager",  manager);
        return "front_show_view";
    }

    /*�����޸�Manager��Ϣ*/
    public String ModifyManager() {
        ActionContext ctx = ActionContext.getContext();
        try {
            managerDAO.UpdateManager(manager);
            ctx.put("message",  java.net.URLEncoder.encode("Manager��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Manager��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Manager��Ϣ*/
    public String DeleteManager() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            managerDAO.DeleteManager(managerUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Managerɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Managerɾ��ʧ��!"));
            return "error";
        }
    }

}
