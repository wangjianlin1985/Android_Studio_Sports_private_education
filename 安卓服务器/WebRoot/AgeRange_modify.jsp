<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.AgeRange" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    AgeRange ageRange = (AgeRange)request.getAttribute("ageRange");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改年龄范围</TITLE>
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
/*验证表单*/
function checkForm() {
    var showText = document.getElementById("ageRange.showText").value;
    if(showText=="") {
        alert('请输入显示信息!');
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
    <TD align="left" vAlign=top ><s:form action="AgeRange/AgeRange_ModifyAgeRange.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>年龄范围id:</td>
    <td width=70%><input id="ageRange.arId" name="ageRange.arId" type="text" value="<%=ageRange.getArId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>开始年龄:</td>
    <td width=70%><input id="ageRange.startAge" name="ageRange.startAge" type="text" size="8" value='<%=ageRange.getStartAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>结束年龄:</td>
    <td width=70%><input id="ageRange.endAge" name="ageRange.endAge" type="text" size="8" value='<%=ageRange.getEndAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>显示信息:</td>
    <td width=70%><input id="ageRange.showText" name="ageRange.showText" type="text" size="20" value='<%=ageRange.getShowText() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
