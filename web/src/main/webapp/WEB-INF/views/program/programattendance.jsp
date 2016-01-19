<%@ include file="../../layouts/include_taglib.jsp"%>

<%@ page import="com.ymca.web.util.Constants" %>
<%@ page import="net.sf.json.JSONArray" %>

<%

	System.out.println(" payerArr   ");	
	JSONArray payerArr = null;
	try{

		payerArr = (JSONArray)request.getAttribute("payerList");

	}catch(Exception e){}
%>

<style>
	.collapse{
		display:none;
	}
	
	.expand{
		display:block;
	}
	
	.sectionHead{
		background-color: #CFE1FB; border-radius: 6px; margin-bottom: 10px; margin-top: 10px; padding: 10px;
	}
	
	.showSection{
		background: url('resources/img/show_section.gif') transparent no-repeat;
	}
	
	.hideSection{
		background: url('resources/img/hide_section.gif') transparent no-repeat;
	}
	
	.viewall{
		cursor:pointer;
	}
	

	table {
	    border-collapse:separate;
	    border:solid #c5c5c5 1px;
	    border-radius:6px;
	    -moz-border-radius:6px;
	}
	
	td, th {
	    border-left:solid #c5c5c5 1px;
	    border-top:solid #c5c5c5 1px;
	}
	
	th {
	    background-color: #01A490;
	    border-top: none;
	    padding : 6px;
	    color : white !important;
	}
	td:first-child {
	     border-left: none;
	}	
</style>

	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF; padding:12px; width: 760px; margin:20px;">	
		<c:forEach var="program" items="${enrolledProgramDetailsArr}" varStatus="status">
			<input type="hidden" id="SignupId" name="SignupId" value="${program.signupId}">	
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
					<span>Contact Name:</span> ${program.contactName}
				</div>
				
				<c:choose>
					<c:when test="${program.noOfTickets> 0}">
						<div id="noOfTicketsOrPackages">
							<span id="noOfTicketsOrPackagesLabel">No of Tickets:</span> ${program.noOfTickets }
						</div>
					</c:when>
				</c:choose>
				<div>
					<span>Registration Fee:</span> 
					<span><fmt:formatNumber value="${program.registrationFee}" type="currency"/></span>
				</div>
				<div>
					<span>Join Fee:</span> 
					<span><fmt:formatNumber value="${program.joinFee}" type="currency"/></span>
				</div>
				<div>
					<span>Setup Fee:</span> 
					<span><fmt:formatNumber value="${program.setUpFee}" type="currency"/></span>
				</div>
				<div>
					<span>Deposit Amount:</span> 
					<span><fmt:formatNumber value="${program.deposit}" type="currency"/></span>
				</div>
				<div>
					<span>Signup Price:</span> 
					<span><fmt:formatNumber value="${program.signupPrice}" type="currency"/></span>
				</div>
				<div>
					<span>Discount Amount:</span> 
					<span><fmt:formatNumber value="${program.discountAmount}" type="currency"/></span>
				</div>
				<div>
					<span>FA Amount:</span> 
					<span><fmt:formatNumber value="${program.FAamount}" type="currency"/></span>
				</div>
				
				<div>
					<span>Final Amount:</span> 
					<span><fmt:formatNumber value="${program.finalAmount}" type="currency"/></span>
				</div>
				<c:if test="${program.isDraftCycle eq true}">
				<div>
					<span>Draft Cycle:</span>
					<span>${program.draftCycle} <span> <input type="text" class="k-textbox" id="draftCycleNumber" name="draftCycleNumber" value="${program.draftCycleNumber}" style="width: 50px" maxlength="2"> </span> <!-- <span><a href="#" onclick="changeDraftCycleNumberInput())"> Update </a> --></span></span>
				</div>
				</c:if>
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
		<div style="padding:20px 0px 10px 0px; margin-top:20px">
			<div style="margin-bottom: 25px;">
				<span class="head" style="float:left">Payment Details</span>
			</div>
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
		<c:if test="${! empty interactionsBySignupArr}">
		<div style="padding:0px 0px 0px 0px; margin-top:20px">
			<span class="head">Attendance</span><br>
			<div class="" style="margin-top:10px">
			<table cellpadding="4" style="width: 100%;">
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
		</c:if>
		<c:if test="${program.category eq 'Residence Camp'}">
			<div id="activityAndTransport">
					<%@ include file="activityAndTransport.jsp" %> 
			</div>	
		</c:if>
		<c:if test="${! empty relatedSignUps}">
			<div id="relatedSignUps" style="padding:0px 0px 0px 0px; margin-top:10px">
				<div style="margin-bottom: 10px;"><span class="head">Related SignUp</span></div>
				<table cellpadding="3" width="100%">
						<thead>	
							<tr class="k-block"><td>Name</td><td>Contact</td><td>Status</td><td>Action</td></tr>
						</thead>
						<tbody>
							<c:forEach var="relatedSignUp" items="${relatedSignUps}" varStatus="counter">	
									<tr>
										<td>${relatedSignUp.signUpName}</td>
										<td>${relatedSignUp.contactName}</td>
										<td>${relatedSignUp.status}</td>
										<td nowrap="nowrap"><a href="viewProgramDetails?sId=${relatedSignUp.signupId}">View</a> | <a href="cancelEvent?sId=${relatedSignUp.signupId}">Cancel</a></td>
									</tr>
							</c:forEach>
						</tbody>
				</table>
			</div>	
		</c:if>
		
		<div style="padding:20px 0px 10px 0px; margin-top:20px">
			<div style="margin-bottom: 25px;">
				<span class="head" style="float:left">Update Payment Method</span>
				<span style="float:right"><a href="viewPaymentInformation"> Add New Payment Method </a></span>
			</div>
			
			<table cellpadding="2" cellspacing="0" width="100%" >
			
				<c:if test="${fn:length(payerList) > 0 }">
					<tr>
						<th style="font-weight: bold; color: #006B6B; width : 1%; ">&nbsp;</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 12%; ">Payer Type</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 15%; ">Invoice Date</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 15%; ">Due Date</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 15%; ">Bill date</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 18%; ">Amount</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 25%; ">Payment Method</th>
						<!-- <th class="head" style="font-weight: bold; color: #006B6B; width : 25%; ">PayerC4</th> -->
					</tr>
				</c:if>
				<c:forEach var="payer" items="${payerList}" varStatus="status">
				
					<tr style="background-color : #F0FFFF; padding-left: 4px; " id="expandableID" class="expandable border-radius5px">
						<td><a class="add-child-lnk"><span>+</span></a></td>
						<td style="color: blue; ">&nbsp;${payer.payerType}</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;<%-- ${payer.payerAmount} --%></td>
						<!-- <td style="color: blue; ">&nbsp;P3</td> -->
						<td id="skipExpand" style="color: blue; ">&nbsp;
							<%-- <input type="text" id="payer_${payer.payerId }" name="payer_${payer.payerId }" > --%>
							<select id="paymentInfoRadioPayer_${payer.payerId }" name="paymentInfoRadio" style="width:200px;" onchange="changeInvoicePaymentMethod(${payer.payerId })">
								
								<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
									<c:choose>
									<c:when test="${ paymentInfo[4] == 'CREDIT' }">
										<c:choose>
											<c:when test="${ paymentInfo[8] == payer.payerPMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }" selected="selected">${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
											</c:when>
											<c:when test="${ paymentInfo[8] != payer.payerPMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }">${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
											</c:when>
										</c:choose>
									</c:when>
									<c:when test="${ paymentInfo[4] == 'ACH' }">
										<c:choose>
											<c:when test="${ paymentInfo[8] == payer.payerPMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }" selected="selected">${ paymentInfo[3] } ${paymentInfo[2] } </option>
											</c:when>
											<c:when test="${ paymentInfo[8] != payer.payerPMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }">${ paymentInfo[3] } ${paymentInfo[2] } </option>
											</c:when>
										</c:choose>
									</c:when>
									</c:choose>								
								</c:forEach>							
								
								<c:if test="${sessionScope.agentUid != null}">
									<c:choose>
										<c:when test="${ (payer.payerPM != null) && (payer.payerPM == 'Cash') }">
											<option value="Cash" selected="selected">Cash</option>
										</c:when>
										<c:otherwise>
											<option value="Cash">Cash</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (payer.payerPM != null) && (payer.payerPM == 'Check') }">
											<option value="Check" selected="selected">Check</option>
										</c:when>
										<c:otherwise>
											<option value="Check">Check</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (payer.payerPM != null) && (payer.payerPM == 'None') }">
											<option value="None" selected="selected">None</option>
										</c:when>
										<c:otherwise>
											<option value="None">None</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (payer.payerPM != null) && (payer.payerPM == 'Stock') }">
											<option value="Stock" selected="selected">Stock</option>
										</c:when>
										<c:otherwise>
											<option value="Stock">Stock</option>
										</c:otherwise>
									</c:choose>
						        </c:if>
							</select>
						</td>
					</tr>
				
					<c:forEach var="invoice" items="${payer.invoices}" varStatus="status1">
						<tr style="padding-left: 4px;border-radius: 4px;">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;${invoice.invoiceDate }</td>
							<td>&nbsp;${invoice.invoiceDueDate }</td>
							<td>&nbsp;${invoice.invoiceBillDate }</td>
							<td>&nbsp;${invoice.invoiceAmount }</td>
							<td>&nbsp;
							<%-- 					<input type="text" id="invoice_${payer.payerId }_${invoice.invoiceId }" name="invoice_${payer.payerId }_${invoice.invoiceId }" > --%>
								<select id="paymentInfoRadioInvoice_${payer.payerId }_${invoice.invoiceId }" name="paymentInfoRadio" style="width:200px;">
			
								<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
									<c:choose>
									<c:when test="${ paymentInfo[4] == 'CREDIT' }">
										<c:choose>
											<c:when test="${ paymentInfo[8] == invoice.invoicePMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }" selected="selected">${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
											</c:when>
											<c:when test="${ paymentInfo[8] != invoice.invoicePMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }">${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
											</c:when>
										</c:choose>
										<%-- <option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"
										
										<c:if test="${ paymentInfo[8] eq invoice.invoicePMId } ">
											selected="selected"
										</c:if>
										
										> ${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option> --%>
									</c:when>
									<c:when test="${ paymentInfo[4] == 'ACH' }">
										<%-- <option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"> ${ paymentInfo[3] }, ${paymentInfo[2] } </option> --%>
										<c:choose>
											<c:when test="${ paymentInfo[8] == invoice.invoicePMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }" selected="selected">${ paymentInfo[3] } ${paymentInfo[2] } </option>
											</c:when>
											<c:when test="${ paymentInfo[8] != invoice.invoicePMId }">
												<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }">${ paymentInfo[3] } ${paymentInfo[2] } </option>
											</c:when>
										</c:choose>
									</c:when>
									</c:choose>								
								</c:forEach>							
								<%-- <option value="New">Add New Card</option>	 --%>		
								<c:if test="${sessionScope.agentUid != null}">
							      
							         <c:choose>
										<c:when test="${ (invoice.invoicePM != null) && (invoice.invoicePM == 'Cash') }">
											<option value="Cash" selected="selected">Cash</option>
										</c:when>
										<c:otherwise>
											<option value="Cash">Cash</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (invoice.invoicePM != null) && (invoice.invoicePM == 'Check') }">
											<option value="Check" selected="selected">Check</option>
										</c:when>
										<c:otherwise>
											<option value="Check">Check</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (invoice.invoicePM != null) && (invoice.invoicePM == 'None') }">
											<option value="None" selected="selected">None</option>
										</c:when>
										<c:otherwise>
											<option value="None">None</option>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${ (invoice.invoicePM != null) && (invoice.invoicePM == 'Stock') }">
											<option value="Stock" selected="selected">Stock</option>
										</c:when>
										<c:otherwise>
											<option value="Stock">Stock</option>
										</c:otherwise>
									</c:choose>
						        </c:if>			
							</select>
							
							</td>
						</tr>
					</c:forEach>
				
				</c:forEach>
			</table>
			
		</div>
		
		
		<c:if test="${ isSaveSignup }">
			<br><br>
			<div style="text-align: center;">
				<span id="saveSignup" class="k-button" >Save</span>
			</div>
			<br>
			<c:choose>
			<c:when test="${ isShowSuccessMsg }">
			<div style="text-align: center; display: block;" id="success_msg_div" class="k-block k-success-colored">
				Sign up details updated successfully.
			</div>
			</c:when>
			<c:otherwise>
			<div style="text-align: center; display: none;" id="success_msg_div" class="k-block k-success-colored">
				Sign up details updated successfully.
			</div>
			</c:otherwise>
			</c:choose>
			<div style="text-align: center; display: none;" id="error_msg_div" class="k-block k-error-colored">
				Error occured while updating data. Please try again later.
			</div>
		</c:if>
</div>
<script>
	$(document).ready(function(){
		
		setTimeout(function(){ $("#success_msg_div").hide(); }, 3000);
		
		$("#expandableID").not($("#skipExpand")).click(function () {
			console.log("  exp ");
	        $('.expandable').not(this).nextAll('tr').not('.expandable').hide();
	        ////$('.expandable').not(this).find('input[type="button"]').val("+");
	        $('.expandable').not(this).find('a span').text("+");
	        
	        //$(this).nextAll('tr').toggle(350);
	        $(this).nextAll('tr').each(function () {
		        if (!($(this).is('.expandable'))){
		        	$(this).toggle(350);	         
		        }else if (($(this).is('.expandable'))){
		        	return false;
		        }
		    });
	        
	        if( $(this).find('a span').text() == "+")
	        {
	        	$(this).find('a span').text("-");
	            //$(this).find('input[type="button"]').val("-");
	        }
	        else
	        {
	            $(this).find('a span').text("+");
	            $('.expandable').nextAll('tr').each(function () {
	    	        if (!($(this).is('.expandable'))) 
	    	        {$(this).hide();
	    	         
	    	        }
	    	    });
	        }
	        
	    });
	    $('.expandable').nextAll('tr').each(function () {
	        if (!($(this).is('.expandable'))) 
	        {$(this).hide();
	         
	        }
	    });
		
		$( "#saveSignup" ).click(function(){
			
			console.log("  save signup ");
			
			var signupId = $("#SignupId").val();
			var draftCycleNumber = $("#draftCycleNumber").val(); 
			
			var dataMap = new Object();
			dataMap.signupId = signupId;
			dataMap.draftCycleNumber = draftCycleNumber;
			
			var payerInvoiceList = '';
			
			var payerDDList = $('[id^="paymentInfoRadioPayer_"]');
			for(i=0; i < payerDDList.length; i++){
				var payerDD = payerDDList[i];
				if(!$(payerDD).is("select")) {
					//	Do nothing
				}else{
				    //var dropdownlist = $("#"+payerDD.id).data("kendoDropDownList");
				    //dropdownlist.value(pmStrOnPayer);
				    
				    console.log("  payerDD.id "+payerDD.id);
				    
				    var payerDDId = payerDD.id;
				    var payerDDIdA = payerDDId.split("_");
				    
				    if(payerDDIdA != null && payerDDIdA.length == 2){
				    	var payerId = payerDDIdA[1];
				    	var payerValueStr = $("#"+payerDD.id).val();
					    console.log("  payerValueStr "+payerValueStr);
					    
					    var payerValueStrA = payerValueStr.split("__");
					    var payerPMId;
					    if(payerValueStrA != null && payerValueStrA.length == 3){
					    	payerPMId = payerValueStrA[2];
					    }else{
					    	payerPMId = payerValueStr;
					    }
				    	
				    	console.log("  payerId   "+payerId+",  payerPMId   "+payerPMId);
				    	payerInvoiceList += "p__"+payerId+"&&"+payerPMId;
						var invoiceDDList = $('[id^="paymentInfoRadioInvoice_'+payerId+'"]');
						for(j=0; j < invoiceDDList.length; j++){
							var invoiceDD = invoiceDDList[j];
							if(!$(invoiceDD).is("select")) {
								//	Do nothing
							}else{
							    console.log("  invoiceDD.id    ::    "+invoiceDD.id);
							    var invoiceDDId = invoiceDD.id;
							    var invoiceDDIdA = invoiceDDId.split("_");
							    if(invoiceDDIdA != null && invoiceDDIdA.length == 3){
							    	var invoiceId = invoiceDDIdA[2];
							    	var selectedInvoiceValueStr = $("#"+invoiceDD.id).val();
									console.log("  selectedInvoiceValueStr    ::    "+selectedInvoiceValueStr);
									var selectedInvoiceValueStrA = selectedInvoiceValueStr.split("__");
									
									var invoicePMid;
									if(selectedInvoiceValueStrA != null && selectedInvoiceValueStrA.length == 3){
										invoicePMid = selectedInvoiceValueStrA[2];
									}else{
										invoicePMid = selectedInvoiceValueStr;
									}
									console.log("  invoiceId   "+invoiceId+",   invoicePMid   "+invoicePMid);
									payerInvoiceList += "i__"+invoiceId+"&&"+invoicePMid;
							    }
							}
						}
					}
				}
			}
			
			dataMap.payerInvoiceList = payerInvoiceList;
			
			$.ajax({
				type: "POST",
				url: "updateSignup",
				async:false,
				data: { dataMap: JSON.stringify(dataMap) },
				success: function( data ) {
					if(data){
						window.location.href = "viewProgramDetails?sId="+signupId+"&showSuccessMsg=true";
					}
				},
				error: function( xhr,status,error ){
					console.log(" Error 242 ");
					$("#error_msg_div").show();
				}
			});
			
		});
		
		
		$('[id^="paymentInfoRadio"]').kendoDropDownList();
		
	});
	
	function changeInvoicePaymentMethod(payerId){
		var pmStrOnPayer = $("#paymentInfoRadioPayer_"+payerId).val();
		var pmStrOnPayerA =  pmStrOnPayer.split("__");
		var paymentMethodOnPayer = pmStrOnPayerA[2];
		var invoiceDDList = $('[id^="paymentInfoRadioInvoice_'+payerId+'_"]');
		for(i=0; i < invoiceDDList.length; i++){
			var invoiceDD = invoiceDDList[i];
			if(!$(invoiceDD).is("select")) {
				//	Do nothing
			}else{
			    var dropdownlist = $("#"+invoiceDD.id).data("kendoDropDownList");
			    dropdownlist.value(pmStrOnPayer);
			}
		}
	}
	
</script>