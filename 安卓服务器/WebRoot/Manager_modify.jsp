<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Manager" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Manager manager = (Manager)request.getAttribute("manager");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���ͨ����Ա</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var managerUserName = document.getElementById("manager.managerUserName").value;
    if(managerUserName=="") {
        alert('���������Ա�û���!');
        return false;
    }
    var password = document.getElementById("manager.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("manager.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("manager.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var telephone = document.getElementById("manager.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Manager/Manager_ModifyManager.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����Ա�û���:</td>
    <td width=70%><input id="manager.managerUserName" name="manager.managerUserName" type="text" value="<%=manager.getManagerUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="manager.password" name="manager.password" type="password" size="20" value='<%=manager.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="manager.name" name="manager.name" type="text" size="20" value='<%=manager.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="manager.sex" name="manager.sex" type="text" size="4" value='<%=manager.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat birthDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="manager.birthDate"  name="manager.birthDate" onclick="setDay(this);" value='<%=birthDateSDF.format(manager.getBirthDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="manager.telephone" name="manager.telephone" type="text" size="20" value='<%=manager.getTelephone() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
