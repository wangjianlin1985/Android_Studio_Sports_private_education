<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>171基于安卓初中生体育私教平台app-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>Coach/Coach_FrontQueryCoach.action" target="OfficeMain">私教</a></li> 
			<li><a href="<%=basePath %>StudentParent/StudentParent_FrontQueryStudentParent.action" target="OfficeMain">学生家长</a></li> 
			<li><a href="<%=basePath %>City/City_FrontQueryCity.action" target="OfficeMain">城市</a></li> 
			<li><a href="<%=basePath %>NowState/NowState_FrontQueryNowState.action" target="OfficeMain">现状态</a></li> 
			<li><a href="<%=basePath %>PriceRange/PriceRange_FrontQueryPriceRange.action" target="OfficeMain">价格范围</a></li> 
			<li><a href="<%=basePath %>AgeRange/AgeRange_FrontQueryAgeRange.action" target="OfficeMain">年龄范围</a></li> 
			<li><a href="<%=basePath %>Manager/Manager_FrontQueryManager.action" target="OfficeMain">普通管理员</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
