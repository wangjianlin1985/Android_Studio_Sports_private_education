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

    /*界面层需要查询的属性: 管理员用户名*/
    private String managerUserName;
    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }
    public String getManagerUserName() {
        return this.managerUserName;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthDate;
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthDate() {
        return this.birthDate;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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
    @Resource ManagerDAO managerDAO;

    /*待操作的Manager对象*/
    private Manager manager;
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    public Manager getManager() {
        return this.manager;
    }

    /*跳转到添加Manager视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Manager信息*/
    @SuppressWarnings("deprecation")
    public String AddManager() {
        ActionContext ctx = ActionContext.getContext();
        /*验证管理员用户名是否已经存在*/
        String managerUserName = manager.getManagerUserName();
        Manager db_manager = managerDAO.GetManagerByManagerUserName(managerUserName);
        if(null != db_manager) {
            ctx.put("error",  java.net.URLEncoder.encode("该管理员用户名已经存在!"));
            return "error";
        }
        try {
            managerDAO.AddManager(manager);
            ctx.put("message",  java.net.URLEncoder.encode("Manager添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Manager添加失败!"));
            return "error";
        }
    }

    /*查询Manager信息*/
    public String QueryManager() {
        if(currentPage == 0) currentPage = 1;
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName, name, birthDate, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        managerDAO.CalculateTotalPageAndRecordNumber(managerUserName, name, birthDate, telephone);
        /*获取到总的页码数目*/
        totalPage = managerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryManagerOutputToExcel() { 
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName,name,birthDate,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Manager信息记录"; 
        String[] headers = { "管理员用户名","姓名","性别","出生日期","联系电话"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Manager.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Manager信息*/
    public String FrontQueryManager() {
        if(currentPage == 0) currentPage = 1;
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerDAO.QueryManagerInfo(managerUserName, name, birthDate, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        managerDAO.CalculateTotalPageAndRecordNumber(managerUserName, name, birthDate, telephone);
        /*获取到总的页码数目*/
        totalPage = managerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Manager信息*/
    public String ModifyManagerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键managerUserName获取Manager对象*/
        Manager manager = managerDAO.GetManagerByManagerUserName(managerUserName);

        ctx.put("manager",  manager);
        return "modify_view";
    }

    /*查询要修改的Manager信息*/
    public String FrontShowManagerQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键managerUserName获取Manager对象*/
        Manager manager = managerDAO.GetManagerByManagerUserName(managerUserName);

        ctx.put("manager",  manager);
        return "front_show_view";
    }

    /*更新修改Manager信息*/
    public String ModifyManager() {
        ActionContext ctx = ActionContext.getContext();
        try {
            managerDAO.UpdateManager(manager);
            ctx.put("message",  java.net.URLEncoder.encode("Manager信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Manager信息更新失败!"));
            return "error";
       }
   }

    /*删除Manager信息*/
    public String DeleteManager() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            managerDAO.DeleteManager(managerUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Manager删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Manager删除失败!"));
            return "error";
        }
    }

}
