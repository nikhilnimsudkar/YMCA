<%@ include file="../../layouts/include_taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="programinfoPg">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF;margin-left:-52px;width:770px;min-height:0px;">
		<table cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
		<tr><span class="head" width="100%" style="margin-left:4px;">MY UPCOMING
					PROGRAMS</span></tr>

		

				
					<tr>
  <td style="padding-bottom:3px;padding-left:4px;">Date</td>
  <td style="padding-bottom:3px;">Time</td>
  <td style="padding-bottom:3px;">Class</td>
  <td style="padding-bottom:3px;">Location</td>
  <td style="padding-bottom:3px;">MemberName</td>
  
  
</tr> 

<!-- markup... -->

						<c:forEach var="program" items="${programList}" varStatus="status">

							
							 <tr style="outline: thin solid;padding-left:3px;">
           
       
								<td style="color: red;padding-left:3px;padding:5px;">
								<fmt:formatDate
										type="date" pattern="MM/dd/yyyy"
										value="${program.programStartDate}" /></td>
								<td style="color: red;"><fmt:formatDate
										type="time" pattern="hh:mm"
										value="${program.programStartDate}" /></td>
								<td style="color: red; padding-left:2px;" align="left"><a onclick="add_member();" href="#" id="addmemberLink">${program.item.productName}</a></td>
								<td style="color: red;">${program.location.branchName}</td>
								<td style="color: red;">${program.customer.name}</td>
								

							</tr>
						
<tr>
    <td>
        &nbsp;
        <!--you just need a space in a row-->
    </td>
</tr>

						</c:forEach>
						
			
				</table>
</div>

		
	