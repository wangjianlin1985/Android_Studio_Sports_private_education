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
    //��ȡ���е�AgeRange��Ϣ
    List<AgeRange> ageRangeList = (List<AgeRange>)request.getAttribute("ageRangeList");
    //��ȡ���е�City��Ϣ
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    //��ȡ���е�NowState��Ϣ
    List<NowState> nowStateList = (List<NowState>)request.getAttribute("nowStateList");
    //��ȡ���е�PriceRange��Ϣ
    List<PriceRange> priceRangeList = (List<PriceRange>)request.getAttribute("priceRangeList");
    Coach coach = (Coach)request.getAttribute("coach");

%>
<HTML><HEAD><TITLE>�鿴˽��</TITLE>
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
    <td width=30%>�û���:</td>
    <td width=70%><%=coach.getCoachUserName() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=coach.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=coach.getName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=coach.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>���䷶Χ:</td>
    <td width=70%>
      <%=coach.getAgeRangeObj().getShowText() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=coach.getAge() %></td>
  </tr>

  <tr>
    <td width=30%>�ֻ���:</td>
    <td width=70%><%=coach.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <%=coach.getCityObj().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��״̬:</td>
    <td width=70%>
      <%=coach.getNowStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�շѼ۸�����:</td>
    <td width=70%>
      <%=coach.getPriceRangeObj().getShowText() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�۸�(Ԫ/Сʱ):</td>
    <td width=70%><%=coach.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>������Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=coach.getCoachPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=coach.getCoachDesc() %></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><%=coach.getShzt() %></td>
  </tr>

  <tr>
    <td width=30%>ע��ʱ��:</td>
    <td width=70%><%=coach.getRegTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
