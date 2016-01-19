<%@ include file="../../layouts/include_taglib.jsp" %>

<c:if test="${fn:length(inserviceObj)!=0}">
<div id="is_list">
	<div style="height: 32px;">
		<span class="head">In-service/ Vacation</span>
		<!-- <span id="iscancelsignup" class="k-button">CANCEL SIGNUP</span> -->
	</div>
	
	<div>
		<table id="inservice_list"  style="table-layout: fixed; display:none">
			<c:set var="lastCount" value="${fn:length(gridfields)}"/>
			<c:set var="lastCount" value="${lastCount+1}"/>
			<colgroup>
				
				<c:forEach var="fields" begin="1" end="${lastCount }" varStatus="counter">
				<c:choose>
					<c:when test="${counter.count==1}">
						<col style='width: 36px' />
					</c:when>
					<%--
					<c:when test="${counter.count==lastCount}">
						<col style='width: 300px' />
					</c:when>
					 --%>
					<c:otherwise>
						<col/>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</colgroup>
			<thead>
				<tr>
					<th data-field='randomFieldName'>&nbsp;</th>
					<c:forEach var="fields" items="${gridfields}" varStatus="counter">
						<th data-field=${fn:replace(fn:replace(fn:replace(fields, ' ', ''),'#',''),'/','')}>${fields}</th>
					</c:forEach>
					<!-- 
  						<th>Type</th>
  						<th>Location</th>
  						<th>Start Time</th>
  						<th>End Time</th>
  						<th>Age Range</th>
  						<th>Gender</th>
  						<th>M / # of available slots</th>
  						<th>Tu / # of available slots</th>
  						<th>W / # of available slots</th>
  						<th>Th / # of available slots</th>
  						<th>F / # of available slots</th>
  					 -->
  				</tr>
 			</thead>
 				
 			<tbody>
  				<c:forEach var="itemdetail" items="${inserviceObj}" varStatus="counter">
  				<tr>
  					<td>
  						<input type="hidden" name="ItemDetailsType" id="ItemDetailsType_${itemdetail[0]}" value="${itemdetail[1]}">
  						<input type="radio" ${itemdetail[7]} name="inServiceRadio" id="inServiceRadio${itemdetail[0]}" value="${itemdetail[0]}">
  					</td>
					<td>${itemdetail[1]}</td>
					<td><fmt:formatDate type="time" pattern="hh:mm a"  value="${itemdetail[2]}" /></td>
					<td><fmt:formatDate type="time" pattern="hh:mm a"  value="${itemdetail[3]}" /></td>
					<td><fmt:formatDate type="date" pattern="MM/dd/YYYY"  value="${itemdetail[4]}" /></td>
					<td><fmt:formatDate type="date" pattern="MM/dd/YYYY"  value="${itemdetail[5]}" /></td>
					<td>${itemdetail[6]}</td>
					<%-- 
					<td>
						<div>
							<input type="radio" name="inServiceSignupType" id="inService_days_${itemdetail[0]}" value="DAYS" checked="checked">&nbsp;Individual Days
							<c:choose>
								<c:when test="${itemdetail[6]}">
									<input type="radio" name="inServiceSignupType" id="inService_package_${itemdetail[0]}" value="PACKAGE" disabled="disabled">&nbsp;Package
								</c:when>
								<c:otherwise>
									<input type="radio" name="inServiceSignupType" id="inService_package_${itemdetail[0]}" value="PACKAGE">&nbsp;Package
								</c:otherwise>
							</c:choose>
						</div>
					</td>
					--%>
				</tr>
				</c:forEach>
 			</tbody>
 		</table>
	</div>
</div>
    		
<div align="left" style="margin: 10px;">
	<span id="clearInServiceRadioSelection" class="k-button" style="text-shadow:none;">Clear</span>
	<!-- <span id="issignup" class="k-button" style="text-shadow:none;">SIGN UP FOR PACKAGE</span>
	<span id="signupindays" class="k-button" style="text-shadow:none;">SIGN UP FOR INDIVIDUAL DAYS</span> -->
</div>		
</c:if>

<script>
	$(document).ready(function() {
		$( "#clearInServiceRadioSelection" ).click(function(){
			$('input[name="inServiceRadio"]').prop('checked', false);
		});
	});
</script>