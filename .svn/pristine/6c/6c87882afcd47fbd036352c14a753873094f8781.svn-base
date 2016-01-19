<%@ include file="../../layouts/include_taglib.jsp" %>

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


<script>
$(document).ready(function(){ 
    
	
	$('.expandable').click(function () {
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
		
		var signupId = $("#signupId").val();
		//var draftCycleNumber = $("#draftCycleNumber").val(); 
		
		var dataMap = new Object();
		dataMap.signupId = signupId;
		//dataMap.draftCycleNumber = draftCycleNumber;
		
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
					//window.location.href = "viewProgramDetails?sId="+signupId+"&showSuccessMsg=true";
					window.location.href = "viewUpdPM?sId="+signupId;
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

function updateDraftCycleShow(signupId, draftCycle){
	
	var dc = draftCycle.split(",");
	
	if(dc.length == 2){
		
		$("#signupIdToUpdate").val(signupId); 
		$("#draftCycleSpan").html(draftCycle); 
		$("#draftCycleNumber").val(dc[1]); 
		
		$("#tabstrip4").hide();
		$("#UpdateDraftCycleDiv").show();
	}
}

function backToRecentDonations(){
	//$("#tabstrip4").show();
	//$("#UpdateDraftCycleDiv").hide();
	window.location.href = "donationHome";
}

function updatePaymentMethodShow(signupId){
	$("#signupIdToUpdate").val(signupId);
}

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

<div id="UpdateDraftCycleDiv" class="k-block" style="background-color: #FFFFFF; margin-left: -71px; padding-top: 20px; padding-left: 20px; width: 838px;">
	
	<div style="padding:20px 0px 10px 0px; margin-top:20px">
			<div style="margin-bottom: 25px;">
				<span class="head" style="float:left">Update Payment Method</span>
				<span style="float:right"><a href="viewPaymentInformation"> Add New Payment Method </a></span>
			</div>
			<input type="hidden" id="signupId" name="signupId" value="${signupId }">
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
				
					<tr style="background-color : #F0FFFF; padding-left: 4px; " class="expandable border-radius5px">
						<td><a class="add-child-lnk"><span>+</span></a></td>
						<td style="color: blue; ">&nbsp;${payer.payerType}</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;</td>
						<td style="color: blue; ">&nbsp;<%-- ${payer.payerAmount} --%></td>
						<!-- <td style="color: blue; ">&nbsp;P3</td> -->
						<td style="color: blue; ">&nbsp;
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
								</c:forEach>							\
								
								<%-- <option value="New">Add New Card</option>			 --%>
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
			<br>
			<div  align="center">
					<br><span id="saveSignup" class="k-button" >Save</span>
					<span class="k-button" id="backToRecentDonations" onclick="backToRecentDonations()" style="margin-left: 5px;">Back</span>
			</div>
			<br><br>
		</div>
</div>
