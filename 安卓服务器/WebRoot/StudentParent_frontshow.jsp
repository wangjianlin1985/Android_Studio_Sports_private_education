<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.StudentParent" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="com.chengxusheji.domain.AgeRange" %>
<%@ page import="com.chengxusheji.domain.NowState" %>
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

%>
<HTML><HEAD><TITLE>查看学生家长</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>用户名:</td>
    <td width=70%><%=studentParent.getSpUserName() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=studentParent.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>家长姓名:</td>
    <td width=70%><%=studentParent.getParentName() %></td>
  </tr>

  <tr>
    <td width=30%>手机号:</td>
    <td width=70%><%=studentParent.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>城市:</td>
    <td width=70%>
      <%=studentParent.getCityObj().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生性别:</td>
    <td width=70%><%=studentParent.getStudentSex() %></td>
  </tr>

  <tr>
    <td width=30%>年龄范围:</td>
    <td width=70%>
      <%=studentParent.getAgeRangeObj().getShowText() %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生年龄:</td>
    <td width=70%><%=studentParent.getAge() %></td>
  </tr>

  <tr>
    <td width=30%>学生学校:</td>
    <td width=70%><%=studentParent.getSchool() %></td>
  </tr>

  <tr>
    <td width=30%>现状态:</td>
    <td width=70%>
      <%=studentParent.getNowStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生照片:</td>
    <td width=70%><img src="<%=basePath %><%=studentParent.getStudentPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>学生介绍:</td>
    <td width=70%><%=studentParent.getStudentDesc() %></td>
  </tr>

  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%><%=studentParent.getShzt() %></td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><%=studentParent.getRegTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
