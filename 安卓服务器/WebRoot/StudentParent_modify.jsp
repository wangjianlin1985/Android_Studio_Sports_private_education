<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.StudentParent" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="com.chengxusheji.domain.AgeRange" %>
<%@ page import="com.chengxusheji.domain.NowState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的City信息
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    //获取所有的AgeRange信息
    List<AgeRange> ageRangeList = (List<AgeRange>)request.getAttribute("ageRangeList");
    //获取所有的NowState信息
    List<NowState> nowStateList = (List<NowState>)request.getAttribute("nowStateList");
    StudentParent studentParent = (StudentParent)request.getAttribute("studentParent");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改学生家长</TITLE>
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
    var spUserName = document.getElementById("studentParent.spUserName").value;
    if(spUserName=="") {
        alert('请输入用户名!');
        return false;
    }
    var password = document.getElementById("studentParent.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var parentName = document.getElementById("studentParent.parentName").value;
    if(parentName=="") {
        alert('请输入家长姓名!');
        return false;
    }
    var telephone = document.getElementById("studentParent.telephone").value;
    if(telephone=="") {
        alert('请输入手机号!');
        return false;
    }
    var studentSex = document.getElementById("studentParent.studentSex").value;
    if(studentSex=="") {
        alert('请输入学生性别!');
        return false;
    }
    var school = document.getElementById("studentParent.school").value;
    if(school=="") {
        alert('请输入学生学校!');
        return false;
    }
    var studentDesc = document.getElementById("studentParent.studentDesc").value;
    if(studentDesc=="") {
        alert('请输入学生介绍!');
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
    <TD align="left" vAlign=top ><s:form action="StudentParent/StudentParent_ModifyStudentParent.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>用户名:</td>
    <td width=70%><input id="studentParent.spUserName" name="studentParent.spUserName" type="text" value="<%=studentParent.getSpUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="studentParent.password" name="studentParent.password" type="text" size="20" value='<%=studentParent.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家长姓名:</td>
    <td width=70%><input id="studentParent.parentName" name="studentParent.parentName" type="text" size="20" value='<%=studentParent.getParentName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>手机号:</td>
    <td width=70%><input id="studentParent.telephone" name="studentParent.telephone" type="text" size="20" value='<%=studentParent.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>城市:</td>
    <td width=70%>
      <select name="studentParent.cityObj.cityNo">
      <%
        for(City city:cityList) {
          String selected = "";
          if(city.getCityNo().equals(studentParent.getCityObj().getCityNo()))
            selected = "selected";
      %>
          <option value='<%=city.getCityNo() %>' <%=selected %>><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生性别:</td>
    <td width=70%><input id="studentParent.studentSex" name="studentParent.studentSex" type="text" size="4" value='<%=studentParent.getStudentSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>年龄范围:</td>
    <td width=70%>
      <select name="studentParent.ageRangeObj.arId">
      <%
        for(AgeRange ageRange:ageRangeList) {
          String selected = "";
          if(ageRange.getArId() == studentParent.getAgeRangeObj().getArId())
            selected = "selected";
      %>
          <option value='<%=ageRange.getArId() %>' <%=selected %>><%=ageRange.getShowText() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生年龄:</td>
    <td width=70%><input id="studentParent.age" name="studentParent.age" type="text" size="8" value='<%=studentParent.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生学校:</td>
    <td width=70%><input id="studentParent.school" name="studentParent.school" type="text" size="50" value='<%=studentParent.getSchool() %>'/></td>
  </tr>

  <tr>
    <td width=30%>现状态:</td>
    <td width=70%>
      <select name="studentParent.nowStateObj.stateId">
      <%
        for(NowState nowState:nowStateList) {
          String selected = "";
          if(nowState.getStateId() == studentParent.getNowStateObj().getStateId())
            selected = "selected";
      %>
          <option value='<%=nowState.getStateId() %>' <%=selected %>><%=nowState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生照片:</td>
    <td width=70%><img src="<%=basePath %><%=studentParent.getStudentPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="studentParent.studentPhoto" value="<%=studentParent.getStudentPhoto() %>" />
    <input id="studentPhotoFile" name="studentPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>学生介绍:</td>
    <td width=70%><textarea id="studentParent.studentDesc" name="studentParent.studentDesc" rows=5 cols=50><%=studentParent.getStudentDesc() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%>
    	<select id="studentParent.shzt" name="studentParent.shzt">
    		<option value="审核通过">审核通过</option>
    		<option value="审核不通过">审核不通过</option>
    	</select>
    </td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><input id="studentParent.regTime" name="studentParent.regTime" type="text" size="20" value='<%=studentParent.getRegTime() %>'/></td>
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
