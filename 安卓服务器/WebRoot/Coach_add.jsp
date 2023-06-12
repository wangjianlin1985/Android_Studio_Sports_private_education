<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���˽��</TITLE> 
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
    var coachUserName = document.getElementById("coach.coachUserName").value;
    if(coachUserName=="") {
        alert('�������û���!');
        return false;
    }
    var password = document.getElementById("coach.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("coach.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("coach.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var telephone = document.getElementById("coach.telephone").value;
    if(telephone=="") {
        alert('�������ֻ���!');
        return false;
    }
    var coachDesc = document.getElementById("coach.coachDesc").value;
    if(coachDesc=="") {
        alert('������������!');
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
    <TD align="left" vAlign=top >
    <s:form action="Coach/Coach_AddCoach.action" method="post" id="coachAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�û���:</td>
    <td width=70%><input id="coach.coachUserName" name="coach.coachUserName" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="coach.password" name="coach.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="coach.name" name="coach.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="coach.sex" name="coach.sex" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���䷶Χ:</td>
    <td width=70%>
      <select name="coach.ageRangeObj.arId">
      <%
        for(AgeRange ageRange:ageRangeList) {
      %>
          <option value='<%=ageRange.getArId() %>'><%=ageRange.getShowText() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="coach.age" name="coach.age" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>�ֻ���:</td>
    <td width=70%><input id="coach.telephone" name="coach.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="coach.cityObj.cityNo">
      <%
        for(City city:cityList) {
      %>
          <option value='<%=city.getCityNo() %>'><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��״̬:</td>
    <td width=70%>
      <select name="coach.nowStateObj.stateId">
      <%
        for(NowState nowState:nowStateList) {
      %>
          <option value='<%=nowState.getStateId() %>'><%=nowState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�շѼ۸�����:</td>
    <td width=70%>
      <select name="coach.priceRangeObj.prId">
      <%
        for(PriceRange priceRange:priceRangeList) {
      %>
          <option value='<%=priceRange.getPrId() %>'><%=priceRange.getShowText() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�۸�(Ԫ/Сʱ):</td>
    <td width=70%><input id="coach.price" name="coach.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>������Ƭ:</td>
    <td width=70%><input id="coachPhotoFile" name="coachPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><textarea id="coach.coachDesc" name="coach.coachDesc" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><input id="coach.shzt" name="coach.shzt" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ע��ʱ��:</td>
    <td width=70%><input id="coach.regTime" name="coach.regTime" type="text" size="20" /></td>
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
