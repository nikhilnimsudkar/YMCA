<%@ include file="../../layouts/include_taglib.jsp"%>
<div id="programinfoPg">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF; padding:12px; width: 760px; margin:20px;">	
		<c:forEach var="program" items="${enrolledProgramDetailsArr}" varStatus="status">	
			<div id="productname"><h1>${program.programName }</h1></div>
			<div id="programdetails" style="padding: 0px 0px 22px 30px;" class="k-block k-shadow">
				<div id="productdesc"><span>${program.programDescription }</span></div>
				<!-- <div id="location"><span>Program Name:</span> </div>  -->
				<div>
					<span id="startdate">Program Start Date: 
						<span id="stDate"></span> 
						<span id="stTime"></span>
					</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="enddate">End Date: 
						<span id="endDate"></span> 
						<span id="endTime"></span>
					</span>
				</div>
				
				
				<div id="pStatus">
					<span>Program Status:</span> ${program.programStatus }
				</div>
				
				<div id="sStatus">
					<span>Signup Status:</span> ${program.programEnrollmentStatus }
				</div>
				
				<div id="signupDateDiv">
					<span>Signup / Start Date:</span> 
					<span id="signupDate"></span>
				</div>
				
				<div id="cName">
					<span>Contact Name:</span> ${program.contactName }
				</div>
				
				<c:choose>
					<c:when test="${program.noOfTickets> 0}">
						<div id="noOfTicketsOrPackages">
							<span id="noOfTicketsOrPackagesLabel">No of Tickets:</span> ${program.noOfTickets }
						</div>
					</c:when>
				</c:choose>
				<div>
					<span>Discount Amount:</span> 
					<span><fmt:formatNumber value="${program.discountAmount}" type="currency"/></span>
				</div>
				
				<div>
					<span>Final Amount:</span> 
					<span><fmt:formatNumber value="${program.finalAmount}" type="currency"/></span>
				</div>
			</div>
			
			<script>
				var pStDt =  jQuery.parseJSON('${program.programStartDate}');
				var pEndDt =  jQuery.parseJSON('${program.programEndDate}');
				var pStTime =  jQuery.parseJSON('${program.programStartTime}');
				var pEndTime =  jQuery.parseJSON('${program.programEndTime}');
				var signupDt =  jQuery.parseJSON('${program.programEnrollmentDate}');
				
				var stdt = convertJsonDate(pStDt);
				var enddt = convertJsonDate(pEndDt);
				$("#stDate").html(stdt);
				$("#endDate").html(enddt);
				
				var stTime = new Date(pStTime.time);
				var endTime = new Date(pEndTime.time);
				$("#stTime").html(formatTime(stTime));
				$("#endTime").html(formatTime(endTime));
				
				var signupDate = convertJsonDate(signupDt);
				$("#signupDate").html(signupDate);
			</script>
		</c:forEach>
		
		<div style="padding:20px; margin-top:20px">
			<div>
				<span class="head" style="float:left">Payment Details</span>
				<!-- 
					TODO: Show PayNow button based on open balance
				
				<span style="float:right"><span id="paynow" class="k-button" onclick="location.href='payNow'">Pay Now</span></span> -->
			</div><br><br>
			<table cellpadding="3" width="100%">
				<c:choose>
				<c:when test="${fn:length(paymentDetailsArr)==0}">
					<tr>
						<td>
							<div class="k-block k-error-colored">
							No Payment details associated with this program sign up!
							</div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="k-block">
						<td>Credit Card</td>
						<td>Amount</td>
						<td>Payment Date</td>
						<!-- 
						<td>Discount Amount</td>
						<td>Final Amount</td> -->
					</tr>
					<c:forEach var="payment" items="${paymentDetailsArr}" varStatus="status">		
						<tr>
							<td>
								<span style="font-weight:bold;">
									<c:choose>
									<c:when test="${empty payment.ccnumber}">
										Cash/ Cheque
									</c:when>
									<c:otherwise>
										${payment.ccnumber }
									</c:otherwise>
									</c:choose>
								</span>
							</td>
							<td><span><fmt:formatNumber value="${payment.amount}" type="currency"/></span></td>
							<td><span id="paymentDate_${payment.transactionId }"></span></td>
							<!-- <td><span><fmt:formatNumber value="${payment.discountAmount}" type="currency"/></span></td>
							<td><span><fmt:formatNumber value="${payment.finalAmount}" type="currency"/></span></td> -->
						</tr>
						
						<script>
							var paydate =  jQuery.parseJSON('${payment.paymentDate}');
							//alert(paydate);
							$("#paymentDate_${payment.transactionId }").html(convertJsonDate(paydate));
						</script>
					</c:forEach>
				</c:otherwise>
				</c:choose>
				
			</table>
		</div>
		
		<div style="padding:20px; margin-top:20px">
			<span class="head">Attendance</span><br>
			<div class="k-shadow" style="padding:30px; width:450px; margin-top:10px">
			<table cellpadding="4">
				<c:choose>
				<c:when test="${fn:length(interactionsBySignupArr)==0}">
					<tr>
						<td>
							<div class="k-block k-error-colored">
							No Attendance records associated with this program sign up!
							</div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="interaction" items="${interactionsBySignupArr}" varStatus="counter">	
						<tr>
							<td valign="top" width="120px"><span id="checkindate" style="color: #f78f1e; font-weight:bold;">09/01/2014</span></td>
							<td width="62%" valign="top">
								<div><span>Check-In Time: </span><span id="checkintime">10:00 AM</span></div><br>
								<div><span>Check-Out Time: </span><span id="checkouttime">01:00 PM</span></div>
							</td>
						</tr>
						
						<script>
							var checkinDateTime =  jQuery.parseJSON('${interaction.checkinDateTime}');
							var checkoutDateTime =  jQuery.parseJSON('${interaction.checkoutDateTime}');
							
							var checkindate = convertJsonDate(checkinDateTime);
							$("#checkindate").html(checkindate);
							
							var checkintime = new Date(checkinDateTime.time);
							var checkouttime = new Date(checkoutDateTime.time);
							$("#checkintime").html(formatTime(checkintime));
							$("#checkouttime").html(formatTime(checkouttime));
						</script>
					</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
			</div>
	</div>	 
</div>