<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Coach" %>
<%@ page import="com.chengxusheji.domain.AgeRange" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="com.chengxusheji.domain.NowState" %>
<%@ page import="com.chengxusheji.domain.PriceRange" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的AgeRange信息
    List<AgeRange> ageRangeList = (List<AgeRange>)request.getAttribute("ageRangeList");
    //获取所有的City信息
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    //获取所有的NowState信息
    List<NowState> nowStateList = (List<NowState>)request.getAttribute("nowStateList");
    //获取所有的PriceRange信息
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    Coach coach = (Coach)request.getAttribute("coach");

%>
<HTML><HEAD><TITLE>查看私教</TITLE>
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
    <td width=70%><%=coach.getCoachUserName() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=coach.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=coach.getName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=coach.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>年龄范围:</td>
    <td width=70%>
      <%=coach.getAgeRangeObj().getShowText() %>
    </td>
  </tr>

  <tr>
    <td width=30%>年龄:</td>
    <td width=70%><%=coach.getAge() %></td>
  </tr>

  <tr>
    <td width=30%>手机号:</td>
    <td width=70%><%=coach.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>城市:</td>
    <td width=70%>
      <%=coach.getCityObj().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>现状态:</td>
    <td width=70%>
      <%=coach.getNowStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>收费价格区间:</td>
    <td width=70%>
      <%=coach.getPriceRangeObj().getShowText() %>
    </td>
  </tr>

  <tr>
    <td width=30%>价格(元/小时):</td>
    <td width=70%><%=coach.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>教练照片:</td>
    <td width=70%><img src="<%=basePath %><%=coach.getCoachPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>教练简介:</td>
    <td width=70%><%=coach.getCoachDesc() %></td>
  </tr>

  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%><%=coach.getShzt() %></td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><%=coach.getRegTime() %></td>
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
