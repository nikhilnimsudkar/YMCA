<%@ include file="../../layouts/include_taglib.jsp" %>

<input type="hidden" name="childCareCounter" id="childCareCounter" value="${fn:length(childcareObj)}">
<c:if test="${fn:length(childcareObj)!=0}">

<div id="cc_list">
	<div style="height: 32px;">
		<span class="head">List (Select slots for pricing to show up) NOTE: if # of available slots = 0, you cannot select that day</span>
		<!-- 
		<span id="cancelsignup" class="k-button">CANCEL SIGNUP</span>
		<span id="changesignup" class="k-button">CHANGE SIGNUP</span> -->
	</div>

	<div>
		<table id="childcare_list"  style="table-layout: fixed; display:none">
			<thead>
				<tr>
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
 				<c:forEach var="itemdetail" items="${childcareObj}" varStatus="counter">
  				<tr>
					<td>
						<input type="hidden" name="ItemDetailsType" id="ItemDetailsType_${itemdetail[1][0]}" value="${itemdetail[1][1]}">
						<input type="hidden" name="ItemDetailsId" id="ItemDetailsId_${itemdetail[1][0]}" value="${itemdetail[1][0]}">
						<input type="hidden" name="tier" id="tier_${itemdetail[1][0]}" value="${itemdetail[0][1]}">
						<input type="hidden" name="noofday" id="noofday_${itemdetail[1][0]}" value="${itemdetail[0][2]}">
						<input type="hidden" name="noofmonth" id="noofmonth_${itemdetail[1][0]}" value="${itemdetail[0][3]}">
						<input type="hidden" name="location" id="location_${itemdetail[1][0]}" value="${itemdetail[0][4]}">
						${itemdetail[1][1]}
					</td>
					<td>${itemdetail[1][2]}</td>
					<td><fmt:formatDate type="time" pattern="hh:mm a"  value="${itemdetail[1][3]}" /></td>
					<td><fmt:formatDate type="time" pattern="hh:mm a"  value="${itemdetail[1][4]}" /></td>
					<td><fmt:formatDate type="date" pattern="MM/dd/YYYY"  value="${itemdetail[1][5]}" /></td>
					<td><fmt:formatDate type="date" pattern="MM/dd/YYYY"  value="${itemdetail[1][6]}" /></td>
					<td>${itemdetail[1][7]}</td>
					<td>${itemdetail[1][8]}</td>
					
					<c:forEach var="daysId" begin="1" end="5">
						<c:set var="initCount" value="${daysId + 9 }"/>
						<td>
							<input type="hidden" name="capacity${daysId }" id="capacity${daysId }_${itemdetail[1][0]}" value="${ itemdetail[1][initCount] }">
							<c:choose>
							<c:when test="${ itemdetail[1][initCount]=='-10'}">
								<input type="checkbox" name="days_slot" class="slot_${daysId }_${itemdetail[0][4]}" id="slot_${daysId }_${itemdetail[1][0]}" value="${daysId }_${itemdetail[1][0]}" style="display:none;">
								<div class="greyBox">&nbsp;</div>
							</c:when>
							
							<%-- <c:when test="${ itemdetail[1][initCount]=='0'}">
								<input type="checkbox" disabled="disabled" name="days_slot" class="slot_${daysId }_${itemdetail[0][4]}" id="slot_${daysId }_${itemdetail[1][0]}" value="${daysId }_${itemdetail[1][0]}">&nbsp;${ itemdetail[1][initCount]}
							</c:when> --%>
							
							<c:otherwise>
								<input onclick="calcPrice('${itemdetail[1][0]}','${itemdetail[0][4]}')" ${itemdetail[1][9]} type="checkbox" name="days_slot" class="slot_${daysId }_${itemdetail[0][4]}" id="slot_${daysId }_${itemdetail[1][0]}" value="${daysId }_${itemdetail[1][0]}">&nbsp;${ itemdetail[1][initCount]}
							</c:otherwise>
							</c:choose>
						</td>
					</c:forEach>
					<!-- 
					<td>
						<c:choose>
						<c:when test="${ itemdetail[8]=='-9'}">
							<div class="greyBox">&nbsp;</div>
						</c:when>
						<c:when test="${ itemdetail[8]=='0'}">
							<input type="checkbox" disabled="disabled">&nbsp;${ itemdetail[8]}
						</c:when>
						<c:otherwise>
							<input onclick="calcPrice(2,${itemdetail[0]})" type="checkbox" name="days_slot" class="slot_2" id="slot_2_${itemdetail[0]}" value="2_${itemdetail[0]}">&nbsp;${ itemdetail[8]}
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
						<c:when test="${ itemdetail[9]=='-9'}">
							<div class="greyBox">&nbsp;</div>
						</c:when>
						<c:when test="${ itemdetail[9]=='0'}">
							<input type="checkbox" disabled="disabled">&nbsp;${ itemdetail[9]}
						</c:when>
						<c:otherwise>
							<input onclick="calcPrice(3,${itemdetail[0]})" type="checkbox" name="days_slot" class="slot_3" id="slot_3_${itemdetail[0]}" value="3_${itemdetail[0]}">&nbsp;${ itemdetail[9]}
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
						<c:when test="${ itemdetail[10]=='-9'}">
							<div class="greyBox">&nbsp;</div>
						</c:when>
						<c:when test="${ itemdetail[10]=='0'}">
							<input type="checkbox" disabled="disabled">&nbsp;${ itemdetail[10]}
						</c:when>
						<c:otherwise>
							<input onclick="calcPrice(4,${itemdetail[0]})" type="checkbox" name="days_slot" class="slot_4" id="slot_4_${itemdetail[0]}" value="4_${itemdetail[0]}">&nbsp;${ itemdetail[10]}
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
						<c:when test="${ itemdetail[11]=='-9'}">
							<div class="greyBox">&nbsp;</div>
						</c:when>
						<c:when test="${ itemdetail[11]=='0'}">
							<input type="checkbox" disabled="disabled">&nbsp;${ itemdetail[11]}
						</c:when>
						<c:otherwise>
							<input onclick="calcPrice(5,${itemdetail[0]})" type="checkbox" name="days_slot" class="slot_5" id="slot_5_${itemdetail[0]}" value="5_${itemdetail[0]}">&nbsp;${ itemdetail[11]}
						</c:otherwise>
						</c:choose>
					</td>
					 -->
  				</tr>
  				</c:forEach>
 			</tbody>
 		</table>
	</div>
	<div>Total Monthly Price: <span id="totalchildcareprice">$ 0.00</span>
	</div>
</div>
    		
<div align="center" style="margin: 10px;">
	<!-- <span id="ccsignup" class="k-button" style="text-shadow:none;">CONTINUE TO SIGN UP</span> -->
	<!-- <span id="requestforwl" class="k-button" style="text-shadow:none;">REQUEST FOR WAITLIST</span>  -->
</div>

<script>
	$(document).ready(function() {
	
		$( "#ccsignup" ).click(function(){

			var item_Detail_Ids = [];
			$('#childcare_list').find('input[name="days_slot"]').each(function(){
				if($("#slot_"+this.value).is(':checked')){
					var itemdayId = this.value;
					var itemDetailsId = itemdayId.split("_")[1];
					item_Detail_Ids.push(itemDetailsId);
				}
			});
			
			item_Detail_Ids = ArrNoDupe(item_Detail_Ids);
			if(item_Detail_Ids==""){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please select atleast one child care program");
				  return;
			}
			
			$.sessionStorage.setItem('itemDetailsId', item_Detail_Ids.join(','));
			getFamilymembers('Child Care');
			$("#childcare").hide();
			$("#searchchildcare").hide();
		});
		
		
	});
</script>

</c:if>