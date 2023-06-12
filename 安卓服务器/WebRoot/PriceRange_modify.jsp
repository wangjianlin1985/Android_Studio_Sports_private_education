<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.PriceRange" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    PriceRange priceRange = (PriceRange)request.getAttribute("priceRange");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改价格范围</TITLE>
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
    var showText = document.getElementById("priceRange.showText").value;
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
    <TD align="left" vAlign=top ><s:form action="PriceRange/PriceRange_ModifyPriceRange.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>价格范围id:</td>
    <td width=70%><input id="priceRange.prId" name="priceRange.prId" type="text" value="<%=priceRange.getPrId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>起始价:</td>
    <td width=70%><input id="priceRange.startPrice" name="priceRange.startPrice" type="text" size="8" value='<%=priceRange.getStartPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>结束价:</td>
    <td width=70%><input id="priceRange.endPrice" name="priceRange.endPrice" type="text" size="8" value='<%=priceRange.getEndPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>显示信息:</td>
    <td width=70%><input id="priceRange.showText" name="priceRange.showText" type="text" size="20" value='<%=priceRange.getShowText() %>'/></td>
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
