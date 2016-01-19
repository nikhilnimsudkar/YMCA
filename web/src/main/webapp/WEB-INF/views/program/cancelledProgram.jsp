<%@ include file="../../layouts/include_taglib.jsp"%>

<table style="padding-left:4px;padding-top:4px;" id="cancelled">
	<!-- 
	<tr>
		<td colspan="4" align="left">
			<span class="head" style="margin-left:4px;">
				Cancelled Programs 
			</span>
		</td>
	</tr>	
	<tr><td>&nbsp;</td></tr>
	 -->
		
	
	<c:choose>
		<c:when test="${fn:length(cancelledProgramsArr)==0}">
			<tr>
				<td colspan="4" align="left">
					<div class="k-block k-error-colored">
						No Cancelled Programs
					</div>
				</td>
			</tr>	
		</c:when>
		<c:otherwise>		
			<tr>
				<th>Program Name</th>
				<th>Type</th>
				<th>Contact</th>
				<th>Program Start Date/ Time</th>
				<th>Program End Date/ Time</th>
				<!-- <th>Program Status</th> -->
				<th>Program enrollment status </th>
				<th>Signup / Start Date </th>
				<th>Days Signed Up</th>
			</tr> 
			
			<c:forEach var="program" items="${cancelledProgramsArr}" varStatus="status">					
				<tr class="troutline k-block">
		       		<td><a href="viewProgramDetails?sId=${program.signupId}">${program.programDescription }</a></td>
		       		<td>${program.itemTypeTxt}</td>
		       		<td>${program.contactName}</td>
		       		<td class="trred">
						<span id="pStartDate_${program.signupId }"></span>
						<span id="pStartTime_${program.signupId }"></span>
					</td>
					<td class="trred">
						<span id="pEndDate_${program.signupId }"></span>
						<span id="pEndTime_${program.signupId }"></span>
					</td>
					<%--<td class="trred">
						${program.programStatus}
					</td>	--%>
					<td class="trred">${program.programEnrollmentStatus}</td>	
					<td class="trred">
						<span id="sSignupDate_${program.signupId }"></span>
					</td>
					<%--<c:if test="${program.itemType=='CHILD CARE' && program.itemTypeTxt!='After School'}"> --%>
						<td class="trred">
							${program.signupDays}
						</td>	
					<%--</c:if> --%>
				</tr>
				
				<script>
					var pStDt =  jQuery.parseJSON('${program.programStartDate}');
					var pEndDt =  jQuery.parseJSON('${program.programEndDate}');
					var pStTime =  jQuery.parseJSON('${program.programStartTime}');
					var pEndTime =  jQuery.parseJSON('${program.programEndTime}');
					var sSignupDt =  jQuery.parseJSON('${program.signupDate}');
					
					var stdt = convertJsonDate(pStDt);
					var enddt = convertJsonDate(pEndDt);
					var signupdt = convertJsonDate(sSignupDt);
					$("#pStartDate_${program.signupId }").html(stdt);
					$("#pEndDate_${program.signupId }").html(enddt);
					$("#sSignupDate_${program.signupId }").html(signupdt);
					
					var stTime = new Date(pStTime.time);
					var endTime = new Date(pEndTime.time);
					$("#pStartTime_${program.signupId }").html(formatTime(stTime));
					$("#pEndTime_${program.signupId }").html(formatTime(endTime));
				</script>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<c:if test="${fn:length(cancelledProgramsArr)>0}">
	<script>
	$(document).ready(function(){ 
		 $("#cancelled").kendoGrid({
	         pageable: true,
	         dataSource: {
	             pageSize: 10
	         }
	     });
	});
	</script>
</c:if>




		
	