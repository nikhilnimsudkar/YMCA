<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>

<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF;margin-left:-5px;width:800px;min-height:0px;">
		<table cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
		<tr><span class="head" width="100%" style="margin-left:4px;">CHEDKED-IN KIDS DETAILS:</span></tr>
<tr>
  <td style="padding-bottom:3px;padding-left:4px;"> </td>
  <td style="padding-bottom:3px;color: red;">KID NAME</td>
  <td style="padding-bottom:3px;color: red;">CHECKED-IN TIME</td>
  <td style="padding-bottom:3px;color: red;">CHECKED-IN PERSON NAME</td>
  <td style="padding-bottom:3px;color: red;">CHECKED-IN LOCATION</td> 
</tr> 

<c:if test="${ checkedInKidsCount >= 1}">
<c:forEach var="kids" items="${alreadyCheckedInKidsList }" varStatus="count">
<c:choose>
    <c:when test="${kids.checkedInhrs >=2 }">
	<tr style="outline: thin solid;padding-left:3px;color: red;">
	</c:when>    
 <c:otherwise>
<tr style="outline: thin solid;padding-left:3px;">
</c:otherwise>
</c:choose>
<td> <span class="profilepic" style="background: url('<%=contextPath %>/${kids.contact.profileImage}') transparent no-repeat 0 0;">&nbsp;</span></td>
<td><span class="name">${kids.contact.personFirstName } ${kids.contact.personLastName }</span></td>
<td><span><fmt:setTimeZone value="America/Los_Angeles"/><fmt:formatDate  pattern="MM/dd/yy hh:mm:ss a" value="${kids.createdDate }" timeZone="America/Los_Angeles" /></td>
<td><div style="margin-left: -5px; margin-top: -47px;"><span class="name" style="margin-left:1px;">${kids.checkInPerson.personFirstName } ${kids.checkInPerson.personLastName }</span><br>
Email-Id:${kids.checkInPerson.emailAddress} <br>
Phone No:${kids.checkInPerson.formattedPhoneNumber}</div></td>
<td><span class="name" style="margin-left:1px;">${kids.location.recordName }</span></td>
</tr>
<%-- <c:if test="${!count.last}"><hr></c:if> --%>
</c:forEach>
</table>
</c:if>


