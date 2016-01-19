<%@ include file="../../layouts/include_taglib.jsp"%>
<style type="text/css">
.k-grid  .k-grid-content
{        
        min-height: 100px;
    	max-height: 150px;
}
</style>
<div id="tabstrip3" class="k-block"
	style="background-color: #FFFFFF; padding: 12px; width: 100%">

	<!-- <h2>
				Request for Cancellation
			</h2> -->
<div>
	<table width="100%" style="margin-top: 30px;">				
		<tr>
			<td>
				<span style=""><strong><span id="SelectedCurrentDueAmountLabel" style="font-weight: bold;">Selected Payable Amount:</span></strong></span>	
			</td>
			<td><strong><span id="SelectedPayableAmountVal" style="font-weight: bold;"></span></strong></td>
			<td align="left"><span class="k-button" id="payCurrentDue" style="width: 220px;" onclick="paySelectedCurrentDueFn();">Pay Selected Invoice(s) </span></td>
		</tr>
	</table>		
</div>
<span id="x" style="display:none" >${requiredInvoiceList}</span>
<span id="y" style="display:none" >${requiredPastInvoiceList}</span>
	<div id="formBlock">
		

		<div id="cc_list">
			<span class="head">List Of Current Due Invoice Records</span><br></br>		
			<div>
				<table id="childcare_list"
					style="table-layout: fixed; display: none;">
					<thead>
						<tr>
							<th><input id="selectAllTxtInput" type="checkbox"></th>
							<th>Due Date</th>
							<th>Balance</th>
							<th>Program</th>
							<th>Contact</th>
							<!-- <th>Invoice Id</th> -->

						</tr>
					</thead>
					<tbody>
						<c:forEach var="requiredInvoiceList"
							items="${requiredInvoiceList}" varStatus="status">
							<tr>
								<td>&nbsp;</td>
								<td><fmt:formatDate type="date" pattern="MM/dd/yyyy"
										value="${requiredInvoiceList.dueDate}" /></td>									
                                     <td class="currentDueBalanceCls">${requiredInvoiceList.balance}</td>
														
								<c:choose>
									<c:when test="${(requiredInvoiceList.signup.paymentFrequency!='one-time') && (requiredInvoiceList.payer.type=='self') }">
										<td>${requiredInvoiceList.signup.itemDetail.recordName} ${requiredInvoiceList.signup.contact.firstName} ${requiredInvoiceList.signup.contact.lastName}(AUTO)</td>
									</c:when>

									<c:otherwise>
										<td>${requiredInvoiceList.signup.itemDetail.recordName} ${requiredInvoiceList.signup.contact.firstName} ${requiredInvoiceList.signup.contact.lastName}</td>
									</c:otherwise>
								</c:choose>
								
								<td>${requiredInvoiceList.signup.contact.firstName} ${requiredInvoiceList.signup.contact.lastName}</td>
								<td>${requiredInvoiceList.invoiceId}</td>
								<td>&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br />
		<div>
			<table width="100%" style="">
				<tr>
					<td><strong><span id="currentPastDueAmount" style="font-weight: bold;">CurrentDueAmount:</span></strong></td>
					<td><strong><span id="currentPastDueAmount" class="currentDueAmtClass" style="font-weight: bold;">${dueAmount}</span></strong></td>
					<td align="left"><span class="k-button" id="updProfile" onclick="location.href='payCurrentPastDue?currentDueAmount=${dueAmount}&pastDueAmount=${pastDueAmount}'" style="width: 220px;">Pay all Current and Past Due</span><span class="k-button" id="" style="width: 220px; margin-left : 10px;" onclick="paySelectedCurrentDueFn();">Pay Selected Invoice(s) </span></td>
				</tr>				
			</table>		
		</div>		
		<br /><br />
	</div>

	<div id="formBlock2">

		<div id="cc_list2">

			<span class="head">List Of Past Due Invoice Records</span><br></br>
			<div>
				<table id="childcare_list2"
					style="table-layout: fixed; display: none;">
					<thead>
						<tr>
							<th><input id="selectAllPastDueTxtInput" type="checkbox"></th>
							<th>Due Date</th>
							<th>Balance</th>
							<th>Program</th>
							<th>Contact</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="requiredPastInvoiceList"
							items="${requiredPastInvoiceList}" varStatus="status">
							<tr>
								<td>&nbsp;</td>
								<td><fmt:formatDate type="date" pattern="MM/dd/yyyy"
										value="${requiredPastInvoiceList.dueDate}" /></td>
								<td>${requiredPastInvoiceList.balance}</td>
								
								<td>${requiredPastInvoiceList.signup.contact.firstName} ${requiredPastInvoiceList.signup.contact.lastName}</td>

								<%-- <td>${requiredPastInvoiceList.signup.contactName}</td> --%>
								<td>${requiredPastInvoiceList.signup.contact.firstName} ${requiredPastInvoiceList.signup.contact.lastName}</td>
								<td>${requiredPastInvoiceList.invoiceId}</td>
								

							</tr>
						</c:forEach>
					</tbody>					
				</table>
			</div>
		</div>
		</br>
		<div>
			<table width="100%" style="">				
				<tr>
					<td>
						<span style=""><strong><span id="pastDuePayment" style="font-weight: bold;">Past Due Amount :</span></strong></span>	
					</td>
					<td><strong><span id="pastDueAmount" style="font-weight: bold;">${pastDueAmount}</span></strong></td>
					<td align="left"><span class="k-button" id="updProfile" style="width: 220px;" onclick="location.href='payPastDue?pastDueAmount=${pastDueAmount}'">Pay Past Due</span><span class="k-button" id="" style="width: 220px; margin-left : 10px;" onclick="paySelectedCurrentDueFn();">Pay Selected Invoice(s) </span></td>
				</tr>
			</table>		
		</div>
		<%-- <strong><span id="pastDuePayment">PastDueAmount:</span></strong>
							<strong><span id="pastDueAmount">${pastDueAmount}</span></strong>
		<span class="k-button" id="updProfile" onclick="location.href='payPastDue?pastDueAmount=${pastDueAmount}'" style="margin-left:990px;">Pay Past Due</span></br></br> --%>
	</div>
	
	<div id="FutureDue_list_div">
			<span class="head">List Of Future Due Invoice Records</span><br></br>		
			<div>
				<table id="FutureDue_list"
					style="table-layout: fixed; display: none;">
					<thead>
						<tr>
							<th><input id="selectAllFutureDueTxtInput" type="checkbox"></th>
							<th>Due Date</th>
							<th>Balance</th>
							<th>Program</th>
							<th>Contact</th>
							<!-- <th>Invoice Id</th> -->

						</tr>
					</thead>
					<tbody>
						<c:forEach var="futureDueInvoice"
							items="${futureDueInvoiceList}" varStatus="status">
							<tr>
								<td>&nbsp;</td>
								<td><fmt:formatDate type="date" pattern="MM/dd/yyyy"
										value="${futureDueInvoice.dueDate}" /></td>									
                                     <td class="currentDueBalanceCls">${futureDueInvoice.balance}</td>
														
								<c:choose>
									<c:when test="${(futureDueInvoice.signup.paymentFrequency!='one-time') && (futureDueInvoice.payer.type=='self') }">
										<td>${futureDueInvoice.signup.itemDetail.recordName} ${futureDueInvoice.signup.contact.firstName} ${futureDueInvoice.signup.contact.lastName}(AUTO)</td>
									</c:when>

									<c:otherwise>
										<td>${futureDueInvoice.signup.itemDetail.recordName} ${futureDueInvoice.signup.contact.firstName} ${futureDueInvoice.signup.contact.lastName}</td>
									</c:otherwise>
								</c:choose>
								
								<td>${futureDueInvoice.signup.contact.firstName} ${futureDueInvoice.signup.contact.lastName}</td>
								<td>${futureDueInvoice.invoiceId}</td>
								<td>&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br />
		<div>
			<table width="100%" style="">				
				<tr>
					<td>
						<span style=""><strong><span id="SelectedPastAmountLabel" style="font-weight: bold;">Future Due Amount:</span></strong></span>	
					</td>
					<td><strong><span id="SelectedPastAmountVal" style="font-weight: bold;">${futureDueAmount }</span></strong></td>
					<td align="left"><span class="k-button" id="payPastDue" style="width: 220px;" onclick="paySelectedCurrentDueFn();">Pay Future Due</span><span class="k-button" id="" style="width: 220px; margin-left : 10px;" onclick="paySelectedCurrentDueFn();">Pay Selected Invoice(s) </span></td>
				</tr>
			</table>		
		</div>
</div>

<script>
var payCurrentDue = $("#payCurrentDue").kendoButton({            
}).data("kendoButton");
var x=$("#x").text().length;
var y=$("#y").text().length;
if((x==null || x==2) &&(y==null || y==2))
{
	
	$("#formBlock").html("");
	$("#formBlock2").html("");
		
	$("#formBlock").html("THERE IS NO CURRENT OR PAST DUE AGAINST TO YOU, THANKS");
}
else if(x==null || x==2)
{
	$("#formBlock").html("");
}

else if(y==null || y==2)
{
	$("#formBlock2").html("");
	$("#updProfile").html("Pay Current Due");
	//$("#currentPastDueAmount").html("currentDueAmount");
	
}

$("#tabstrip3").hide();

	$(document).ready(function() {
	//alert("here "+requiredPastInvoiceList)
	 //payCurrentDue.enable(false);
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','');
		$("#top_programregistration").attr('class','');
		$("#top_childcare").attr('class','');
		
		//$("#page_name").html("PAYMENT INFORMATION");
		$("#location").kendoDropDownList();
		$("#type").kendoDropDownList();
		
		$('#selectAllTxtInput').change(function() {			
			if($(this).is(':checked')){
				$(document).find('.currentDueInvoiceCheckBoxCls').each(function(i, obj) {
					if(!$(this).is(':checked')){
						$(this).prop('checked', true);
					}
	           	});				
			}else{
				$(document).find('.currentDueInvoiceCheckBoxCls').each(function(i, obj) {
					if($(this).is(':checked')){
						$(this).prop('checked', false);
					}
	           	});
			}
			calculateInvAmt();        
	    });
		
		$('#selectAllPastDueTxtInput').change(function() {			
			if($(this).is(':checked')){
				$(document).find('.pastDueInvoiceCheckBoxCls').each(function(i, obj) {
					if(!$(this).is(':checked')){
						$(this).prop('checked', true);
					}
	           	});				
			}else{
				$(document).find('.pastDueInvoiceCheckBoxCls').each(function(i, obj) {
					if($(this).is(':checked')){
						$(this).prop('checked', false);
					}
	           	});
			}
			calculatePastDueInvAmt();        
	    });
		$('#selectAllFutureDueTxtInput').change(function() {			
			if($(this).is(':checked')){
				$(document).find('.futureDueInvoiceCheckBoxCls').each(function(i, obj) {
					if(!$(this).is(':checked')){
						$(this).prop('checked', true);
					}
	           	});				
			}else{
				$(document).find('.futureDueInvoiceCheckBoxCls').each(function(i, obj) {
					if($(this).is(':checked')){
						$(this).prop('checked', false);
					}
	           	});
			}
			calculateFutureDueInvAmt();        
	    });
		
		/* $(document).find('#calCurrentDue').on('click',function(e){	
			var totalBalance = 0;			
			$(document).find('.currentDueInvoiceCheckBoxCls').each(function(i, obj) {	
				if($(this).is(':checked')){					
					var rowTr = $(this).parent().parent();					
					var balance = $(rowTr).find("td:nth-child(3)").html();
					var invoiceId = $(rowTr).find("td:nth-child(6)").html();
					if(invoiceId){
						invoiceArr.push(invoiceId);
					}					
					totalBalance = totalBalance + parseFloat(balance);	
				}					
			});
			$(document).find('#SelectedPayableAmountVal').html(totalBalance);			
		}); */
		
		
		$("#childcare_list").kendoGrid({
			dataSource: {
				schema: {
	                model: {
	                    fields: {
	                    	Selected: { type: "bool" },
	                    	DueDate: { type: "string" },
	                    	Balance: { type: "string" },
	                    	SignUpName: { type: "string" },
	                    	UserName: { type: "string" },
	                    	InvoiceId: {type: "string"}
	                        
	                    }
	                }
	            },
            	//pageSize: 4
			},
			columns: [
	            {	                
	                field: "Selected",
	                field: "Selected",
	                template: '<input type="checkbox" onclick="calculateInvAmt();" class="selectInvoice currentDueInvoiceCheckBoxCls" />'
	            },
	            {
	                field: "DueDate",
	                title: "Due Date",
	                footerTemplate: "Payment Total"
	            },
	            {
	                field: "Balance",
	                title: "Balance",	                
	                footerTemplate: "<span style='display : none; font-size: 14px; font-weight: bold;' id='totalPaybleAmt'></span> "
	            },
	            {
	                field: "SignUpName",
	                title: "Program"
	            },
	            {
	                field: "UserName",
	                title: "Contact"
	            },
	            {
	            	field: "InvoiceId",
	            	title: "InvoiceId",
	            	hidden: true
	            }
	        ],
            //pageable: true,
            selectable: "row",
            scrollable: true
            //height: 150
		});
		
		$("#childcare_list2").kendoGrid({
			dataSource: {
				schema: {
	                model: {
	                    fields: {
	                    	Selected: { type: "bool" },
	                    	DueDate: { type: "string" },
	                    	Balance: { type: "string" },
	                    	SignUpName: { type: "string" },
	                    	UserName: { type: "string" },
	                    	InvoiceId: {type: "string"}
	                        
	                    }
	                }
	            },
			},
			columns: [
			            {	                
			                field: "Selected",
			                field: "Selected",
			                template: '<input type="checkbox" onclick="calculatePastDueInvAmt();" class="selectInvoice pastDueInvoiceCheckBoxCls" />'
			            },
			            {
			                field: "DueDate",
			                title: "Due Date",
			                footerTemplate: "Payment Total"
			            },
			            {
			                field: "Balance",
			                title: "Balance",	                
			                footerTemplate: "<span style='display : none; font-size: 14px; font-weight: bold;' id='totalPastDuePaybleAmt'></span> "
			            },
			            {
			                field: "SignUpName",
			                title: "Program"
			            },
			            {
			                field: "UserName",
			                title: "Contact"
			            },
			            {
			            	field: "InvoiceId",
			            	title: "InvoiceId",
			            	hidden: true
			            }
			        ],
            //pageable: true,
            selectable: "row",
            scrollable: true
            //height: 150
		});
		
		//Future Due Kendo Grid configuration
		$("#FutureDue_list").kendoGrid({
			dataSource: {
				schema: {
	                model: {
	                    fields: {
	                    	Selected: { type: "bool" },
	                    	DueDate: { type: "string" },
	                    	Balance: { type: "string" },
	                    	SignUpName: { type: "string" },
	                    	UserName: { type: "string" },
	                    	InvoiceId: {type: "string"}	                        
	                    }
	                }
	            },
			},
			columns: [
			            {	                
			                field: "Selected",
			                field: "Selected",
			                template: '<input type="checkbox" onclick="calculateFutureDueInvAmt();" class="selectInvoice futureDueInvoiceCheckBoxCls" />'
			            },
			            {
			                field: "DueDate",
			                title: "Due Date",
			                footerTemplate: "Payment Total"
			            },
			            {
			                field: "Balance",
			                title: "Balance",	                
			                footerTemplate: "<span style='display : none; font-size: 14px; font-weight: bold;' id='totalFutureDuePaybleAmt'></span> "
			            },
			            {
			                field: "SignUpName",
			                title: "Program"
			            },
			            {
			                field: "UserName",
			                title: "Contact"
			            },
			            {
			            	field: "InvoiceId",
			            	title: "InvoiceId",
			            	hidden: true
			            }
			        ],
            //pageable: true,
            selectable: "row",
            scrollable: true
            //height: 150
		});
		$("#tabstrip3").show();
        $("#childcare_list").show();
		$("#childcare_list2").show();
		$("#FutureDue_list").show();
		
		/* if(requiredPastInvoiceList==null)
		{
			$("#childcare_list2").hide();
		} */	 
		
	});
	
	function paySelectedCurrentDueFn(){
		var currentDue = $("#SelectedPayableAmountVal").html();
		if(currentDue && currentDue != '0'){
			var invoiceArr = [];
			$(document).find('.selectInvoice').each(function(i, obj) {	
				if($(this).is(':checked')){					
					var rowTr = $(this).parent().parent();						
					var invoiceId = $(rowTr).find("td:nth-child(6)").html();
					if(invoiceId){
						invoiceArr.push(invoiceId);
					}
				}					
			});
			location.href='payCurrentPastDue?currentDueAmount='+ $(".currentDueAmtClass").text()+'&pastDueAmount=0&selectCurrentDue='+currentDue+'&invoiceArr='+invoiceArr;
		}
	}
	function calculateInvAmt(){		
		var totalBalance = 0;			
		$(document).find('.currentDueInvoiceCheckBoxCls').each(function(i, obj) {	
			if($(this).is(':checked')){					
				var rowTr = $(this).parent().parent();					
				var balance = $(rowTr).find("td:nth-child(3)").html();								
				totalBalance = totalBalance + parseFloat(balance);	
				/* var invoiceId = $(rowTr).find("td:nth-child(6)").html();
				if(invoiceId){
					invoiceArr.push(invoiceId);
				} */
			}					
		});
		if(totalBalance && totalBalance >= 0){
			//payCurrentDue.enable(true);
			$(document).find('#totalPaybleAmt').css("display", "");
		}else{
			//payCurrentDue.enable(false);
			$(document).find('#totalPaybleAmt').css("display", "none");
		}		
		$(document).find('#totalPaybleAmt').html(totalBalance);
		updateTotalPayableAmt();
	}
	
	function calculatePastDueInvAmt(){		
		var totalBalance = 0;			
		$(document).find('.pastDueInvoiceCheckBoxCls').each(function(i, obj) {	
			if($(this).is(':checked')){					
				var rowTr = $(this).parent().parent();					
				var balance = $(rowTr).find("td:nth-child(3)").html();								
				totalBalance = totalBalance + parseFloat(balance);	
				/* var invoiceId = $(rowTr).find("td:nth-child(6)").html();
				if(invoiceId){
					invoiceArr.push(invoiceId);
				} */
			}					
		});		
		if(totalBalance && totalBalance >= 0){
			//payCurrentDue.enable(true);
			$(document).find('#totalPastDuePaybleAmt').css("display", "");
		}else{
			//payCurrentDue.enable(false);
			$(document).find('#totalPastDuePaybleAmt').css("display", "none");
		}
		$(document).find('#totalPastDuePaybleAmt').html(totalBalance);
		updateTotalPayableAmt();
	}
	
	function calculateFutureDueInvAmt(){		
		var totalBalance = 0;			
		$(document).find('.futureDueInvoiceCheckBoxCls').each(function(i, obj) {	
			if($(this).is(':checked')){					
				var rowTr = $(this).parent().parent();					
				var balance = $(rowTr).find("td:nth-child(3)").html();								
				totalBalance = totalBalance + parseFloat(balance);	
				/* var invoiceId = $(rowTr).find("td:nth-child(6)").html();
				if(invoiceId){
					invoiceArr.push(invoiceId);
				} */
			}					
		});		
		if(totalBalance && totalBalance >= 0){
			//payCurrentDue.enable(true);
			$(document).find('#totalFutureDuePaybleAmt').css("display", "");
		}else{
			//payCurrentDue.enable(false);
			$(document).find('#totalFutureDuePaybleAmt').css("display", "none");
		}
		$(document).find('#totalFutureDuePaybleAmt').html(totalBalance);
		updateTotalPayableAmt();
	}
	
	function updateTotalPayableAmt(){
		var totalBalance = 0;
		var pastDue = $(document).find('#totalPastDuePaybleAmt').html();
		var currentDue = $(document).find('#totalPaybleAmt').html();
		var futureDue = $(document).find('#totalFutureDuePaybleAmt').html();
		if(pastDue && parseFloat(pastDue) > 0){
			totalBalance = totalBalance + parseFloat(pastDue);
		}
		if(currentDue && parseFloat(currentDue) > 0){
			totalBalance = totalBalance + parseFloat(currentDue);
		}
		if(futureDue && parseFloat(futureDue) > 0){
			totalBalance = totalBalance + parseFloat(futureDue);
		}
		if(totalBalance && totalBalance >= 0){
			payCurrentDue.enable(true);			
		}else{
			payCurrentDue.enable(false);
		}
		$(document).find('#SelectedPayableAmountVal').html(totalBalance);		
	}
</script>

<style>
.k-grid  .k-grid-header  .k-header  .k-link {
	height: auto;
}

.k-grid  .k-grid-header  .k-header {
	white-space: normal;
}
</style>