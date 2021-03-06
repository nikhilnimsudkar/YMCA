<%@ include file="../../layouts/include_taglib.jsp"%>
<script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>
<script>
$(document).ready(function(){
	//$("#updatemeberFrm").hide();
	$(".termsAndConditionContent").html($("#commonTAncHidden").html());
	$("#tranfermemberFrm").hide();
	$("#location").kendoDropDownList();		
	var monthsArr = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];		
	var startMonth = $("#holdStartMonthSpan").html();
	var startMonthInt = parseInt(startMonth);
	var monthOptionHtml = "";
	for(i=startMonthInt; i<=12; i++){
		monthOptionHtml += '<option value="'+ i +'">'+monthsArr[i] +'</option>';
	}		
	$("#holdMembershipSelctMonth").html(monthOptionHtml);
	var holdMembershipSelctMonth = $("#holdMembershipSelctMonth").kendoDropDownList(); 
	holdMembershipSelctMonth.select(0);
	//holdMembershipSelctMonth.dataSource.data([{text: "i1", value: "1"}, {text: "i2", value: "2"}, {text: "i3", value: "3"}])
	
	var holdMembershipNoOfMonth = $("#holdMembershipNoOfMonth").kendoDropDownList();
	holdMembershipNoOfMonth.select(0);
	
	
	$("#updateDraftCycle1").hide();
	
	var updateDraftCycle1 = $("#updateDraftCycle1"),
	linkToDraftCycleUpdate = $("#linkToDraftCycleUpdate")
            .bind("click", function() {
            	//updateDraftCycle.data("kendoWindow").open();
            	linkToDraftCycleUpdate.hide();
            	updateDraftCycle1.show('fold');
            });

	/* var onClose = function() {
		linkToDraftCycleUpdate.show();
	} */
	
	/* if (!updateDraftCycle.data("kendoWindow")) {
		updateDraftCycle.kendoWindow({
	        width: "200px",
	        title: "Update",
	        visible: false,
	        actions: [
	            "Close"
	        ],
	        close: onClose
	    });
	} */
	
	$("#cancelDraftCycleUpdate").click( function (e) {
		//alert("close it ");
		// updateDraftCycle.data("kendoWindow").close();
		updateDraftCycle1.hide('fold');
		linkToDraftCycleUpdate.show();
    });
	
	$("#saveDraftCycle").click( function (e) {
		
		var signupId = $("#SignupId").val();
		var draftCycleNumber = $("#draftCycleNumber").val(); 
		
		var dataMap = new Object();
		dataMap.signupId = signupId;
		dataMap.draftCycleNumber = draftCycleNumber;
		
		$.ajax({
			type: "POST",
			url: "updateSignup",
			async:false,
			data: { dataMap: JSON.stringify(dataMap) },
			success: function( data ) {
				if(data){
					//window.location.href = "viewProgramDetails?sId="+signupId+"&showSuccessMsg=true";
					// updateDraftCycle.data("kendoWindow").close();
					updateDraftCycle1.hide();
					linkToDraftCycleUpdate.show();
					window.location.reload();
				}
			},
			error: function( xhr,status,error ){
				console.log(" Error 242 ");
				$("#error_msg_div").show();
			}
		});
		
		
    });

	$( ".upmembershipBtnCls" ).click(function(){
		$("#head").html("UPDATE MEMBERSHIP");
		$("#upmembershipBtn").hide();
		$("#mem_detail").html("");
		//$("#mem_detail").html($("#updatemeberFrm").html());
		$("#updatemeberFrm").show();
	});
	
	/* $( "#transfermembershipBtn" ).click(function(){
		//alert();
		//$("#tranfermemberFrm").css("margin-top","10px;")
		$("#changemembershipFrm").hide();
		$("#tranfermemberFrm").show();
		$("#transferfrom_pick").kendoDropDownList();
		$("#transferto_pick").kendoDropDownList();
	});
	//$("#transfermember_pick").kendoDropDownList();
	
	$( "#changemembershipBtn" ).click(function(){
		//location.href='signup';
		//$("#tranfermemberFrm").hide();
		//$("#changemembershipFrm").show();			
		
		<c:if test="${signup.signUpPricingOption  == 'Monthly'}">
			$("#changeMembershipErrSpan").css("display", "none");		
			$( "#changeMembershipErrSpan" ).html("");
			location.href='changeMembershipShowWizard';
		</c:if>
		<c:if test="${signup.signUpPricingOption  != 'Monthly'}">
			$("#changeMembershipErrSpan").css("display", "block");		
			$( "#changeMembershipErrSpan" ).html("In order to change Annual Membership, Please cancel existing membership and resignup");
		</c:if>
		
	});
	
	$( "#cancelMembershipBtn" ).click(function(){
		//location.href='signup';
		//$("#tranfermemberFrm").hide();
		//$("#changemembershipFrm").show();			
		location.href='requestcancellation';
		
	});
	
	$( "#holdMembershipRadio" ).click(function(){	
		$("#head").html("HOLD MEMBERSHIP");
		$("#upmembershipBtn").hide();
		$("#mem_detail").html("");			
		$("#updatemeberFrm").hide();			
		$("#changemembershipFrm").hide();
		$("#tranfermemberFrm").hide();
		$("#membershipHoldDiv").show();			
	});	
	 */

	$( "#selectUpdateMem" ).click(function(){	
		var selectedOpt = $( "input:radio[name=updatemembership]:checked" ).attr("id");
		if(selectedOpt){
			$("#changeMembershipErrSpan").css("display", "");		
			$( "#changeMembershipErrSpan" ).html("");
			if(selectedOpt == "transfermembershipBtn"){					
				$("#changemembershipFrm").hide();
				$("#tranfermemberFrm").show();
				$("#transferfrom_pick").kendoDropDownList();
				$("#transferto_pick").kendoDropDownList();
			}else if(selectedOpt == "changemembershipBtn"){
				<c:if test="${signup.signUpPricingOption  == 'Monthly'}">
					$("#changeMembershipErrSpan").css("display", "none");		
					$( "#changeMembershipErrSpan" ).html("");
					location.href='changeMembershipShowWizard';
				</c:if>
				<c:if test="${signup.signUpPricingOption  != 'Monthly'}">
					$("#changeMembershipErrSpan").css("display", "block");		
					$( "#changeMembershipErrSpan" ).html("In order to change Annual Membership, Please cancel existing membership and resignup");
				</c:if>					
			}else if(selectedOpt == "cancelMembershipBtn"){
				location.href='requestcancellation';
			}else if(selectedOpt == "holdMembershipRadio"){
				$("#head").html("HOLD MEMBERSHIP");
				$("#upmembershipBtn").hide();
				$("#mem_detail").html("");			
				$("#updatemeberFrm").hide();			
				$("#changemembershipFrm").hide();
				$("#tranfermemberFrm").hide();
				$("#membershipHoldDiv").show();
			}				
		}else {
			$("#changeMembershipErrSpan").css("display", "block");		
			$( "#changeMembershipErrSpan" ).html("Please select update option above");
		}
	});
	
	
	
	$( "#holdMembershipBtn" ).click(function(){
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
		var signupFreq = $("#membershipSignupFreq").html();
		var membershipHoldStartDate = $("#membershipHoldStartDate").html();
		var membershipHoldEndDate = $("#membershipHoldEndDate").html();
		var currentDate =  new Date();
		var currentYear = currentDate.getFullYear();
		if(!$("input[name='chkTermsAndCondition']:checked").val() && !isLoggedInUserAgent()){ 
			var kendoWindow = $("<div />").kendoWindow({
				title: "Error",
				resizable: false,
				modal: true,
				width:400
			});

			kendoWindow.data("kendoWindow")
			 .content($("#terms-conditions-ErrorBox").html())
			 .center().open();
			
			kendoWindow
			.find(".confirm-terms-conditions")
			.click(function() {
				if ($(this).hasClass("confirm-terms-conditions")) {         		
					kendoWindow.data("kendoWindow").close();
				}
			})
			.end();
			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});
			$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');			
			return false;
		}else if(signupFreq == 'Annual'){
			$("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "block");		
			$( "#tcErrorSpan" ).html("You have enrolled in an annual membership Plan. Therefore you can not put the membership on hold");
		} else if($("#holdMembershipNoOfMonth").kendoDropDownList().val() == 0){
			$("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "block");		
			$( "#tcErrorSpan" ).html("Please Select Number Of Month(s)");
		} else{
			$("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "none");		
			$( "#tcErrorSpan" ).html("");
			
			$(".k-loading-mask").show();
			$(".k-loading-text").html("Please wait");					
			$.ajax({
				  type: "POST",
				  url: "holdMembershipInfo",	
				  data: {"startMonth" : $("#holdMembershipSelctMonth").kendoDropDownList().val(), "numberOfMonths" : $("#holdMembershipNoOfMonth").kendoDropDownList().val()},
				  success: function( data ) {						  
					  var jsonDataArr = jQuery.parseJSON(data);
					  if(jsonDataArr != null && jsonDataArr.length > 0){
	    				  for(var i = 0; i<jsonDataArr.length; i++){		    					 
	    					  if(jsonDataArr[i].holdStartDate != null && jsonDataArr[i].holdEndDate != null){
	    						    $("#tcSuccessSpan").css("display", "block");		
	    							$("#tcSuccessSpan" ).html("Your membership will be put on hold from the "+ jsonDataArr[i].holdStartDate+" to "+jsonDataArr[i].holdEndDate+ ". Hold fee of $"+jsonDataArr[i].holdFee+ " will be applied during the Hold period.");	
	    							$("#tcErrorSpan").css("display", "none");		
	    							$( "#tcErrorSpan" ).html("");
	    					  }else{
	    						  $("#tcSuccessSpan").css("display", "none");		
	    							$("#tcSuccessSpan" ).html("");	
	    							$("#tcErrorSpan").css("display", "block");		
	    							$( "#tcErrorSpan" ).html(jsonDataArr[i].Error);
	    					  }
	    				  }			
	    				  
					  }else {			  
						  $("#tcSuccessSpan").css("display", "none");		
							$("#tcSuccessSpan" ).html("");	
							$("#tcErrorSpan").css("display", "block");		
							$( "#tcErrorSpan" ).html("No Membership Information found.");
					  }
					  $(".k-loading-mask").hide();
				  },
				  error: function( xhr,status,error ){	
					    $("#tcSuccessSpan").css("display", "none");		
						$("#tcSuccessSpan" ).html("");	
						$("#tcErrorSpan").css("display", "block");		
						$( "#tcErrorSpan" ).html("There was some error. Please try again later");	
						$(".k-loading-mask").hide();
				  }
			});				
		}
		
	});
	
	$( "#signUpNewMembershipBtnn" ).click(function(){
		//location.href='signup';
		//$("#tranfermemberFrm").hide();
		//$("#changemembershipFrm").show();			
		location.href='becomeMember';
		
	});		
	
	$( "#nextBtn" ).click(function(){
		$("#head").html("PAYMENT METHOD");
		$("#updatemeberFrm").hide();
		$("#cart").show();
	});
	
	$( "#gotoUpdate" ).click(function(){
		$("#head").html("UPDATE MEMBERSHIP");
		$("#updatemeberFrm").show();
		$("#cart").hide();
	});
	
	$( "#gototandc" ).click(function(){
		$("#head").html("Terms And Conditions");
		$("#tandc").show();
		$("#cart").hide();
	});
	
	$( "#gotocart" ).click(function(){
		$("#head").html("PAYMENT METHOD");
		$("#tandc").hide();
		$("#cart").show();
	});
	
	$( "#gotosummary" ).click(function(){
		$("#head").html("SUMMARY");
		$("#tandc").hide();
		$("#summary").show();
	});
	
	$( "#gototandcp" ).click(function(){
		$("#head").html("Terms And Conditions");
		$("#tandc").show();
		$("#summary").hide();
	});
	
	$( "#gotoconfirmation" ).click(function(){
		$("#head").html("TRANSFER CONFIRMATION");
		$("#summary").hide();
		$("#confirmation").show();
	});
	
	$( "#gotochangecart" ).click(function(){
		$("#head").html("PAYMENT METHOD");
		$("#updatemeberFrm").hide();
		$("#changemembershipFrm").hide();
		$("#cart").show();
		
		$("#movefromcart").hide();
		$("#movefromcart1").show();
	});
	
	$( "#gotochangemembership" ).click(function(){
		$("#updatemeberFrm").show();
		$("#changemembershipFrm").show();
		$("#cart").hide();

		$("#movefromcart").show();
		$("#movefromcart1").hide();
	});
	
	$( "#gotochangetandc" ).click(function(){
		$("#head").html("Terms And Conditions");
		$("#tandc").show();
		$("#cart").hide();
		
		$("#movefromtandc").hide();
		$("#movefromtandc1").show();
	});
	
	$( "#gotochangecartp" ).click(function(){
		$("#head").html("PAYMENT METHOD");
		$("#tandc").hide();
		$("#cart").show();
		
		$("#movefromcart").hide();
		$("#movefromcart1").show();
	});
	
	$( "#gotochangetandcp" ).click(function(){
		$("#head").html("Terms And Conditions");
		$("#tandc").show();
		$("#newmembership_summary").hide();
		
		$("#movefromtandc").hide();
		$("#movefromtandc1").show();
	});
	
	$( "#gotonewsummary" ).click(function(){
		$("#updatemeberFrm").hide();
		$("#tandc").hide();
		$("#newmembership_summary").show();
		
		$("#movefromtandc").show();
		$("#movefromtandc1").hide();
	});
	 
	$( "#gotochangeconfirmation" ).click(function(){
		$("#head").html("CHANGE CONFIRMATION");
		$("#changeconfirmation").show();
		$("#newmembership_summary").hide();
	});
	
	
	 $("#membershipData").kendoGrid({                
            columns: [
				{ field: "field0", title: "" },
                { field: "field1", title: "ID" },
                { field: "field2", title: "Product Name" },
                { field: "field3", title: "Product Description" },                
                { field: "field6", title: "Product Price" }
            ]
        });  
});

function view_membershipf(){
	alert("Membershipid val");
	//$("#m_id").val(12).html;
	//var y=document.getElementById("#m_id");
    //y.innerHTML=154;
	//var myTable = document.getElementById('myTable');
	//myTable.rows[1].cells[3].innerHTML = '154';
	// document.getElementById('#m_id').textContent = '156';
	$("#m_id").hide();
	$("#m_id1").show();
}

function showUpdateDraftCycle(){
	$("#linkToDraftCycleUpdate").hide();
	$("#updateDraftCycle").show();
}

function showUpdatePaymentMethod(){
	var signupId = $("#SignupId").val();
	console.log("  signupId   "+signupId);
	window.location.href = "viewUpdMembershipPM?sId="+signupId;
}

</script>
<style>
	      #example
	      {
	          min-height:200px;
	      }
</style>
<div id="currentMemberInfo">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	
	<c:choose>
<c:when test="${NoMembersHip==null }">

	<div id="tabstrip3" class="k-block" style="background-color:#FFFFFF; padding:9px;margin-left:7px; width: 700px;">
		<span id="head" class="head" width="100%" style="margin-left: 4px;">MEMBERSHIP DETAILS</span>
		&nbsp;&nbsp;
		<c:if test="${signup.status =='Active'}">
			<span id="upmembershipBtn" class="k-button upmembershipBtnCls">Update Membership</span>
		</c:if>
		<c:if test="${signup.status !='Active'}">
			<a id="signUpNewMembershipBtnn" class="k-button" href="#">ACTIVATE</a>
		</c:if>
		
		<br><br><br>
		
		<div id="mem_frm"  style="background-color: #fffff9; padding:10px; width:90%; margin-left:20px; min-height:250px;">
			<div id="updatemeberFrm" style="display:none;">
				<input type="radio" name="updatemembership" id="transfermembershipBtn">&nbsp;Transfer Membership
				<br /> <br />
				<input type="radio" name="updatemembership" id="changemembershipBtn">&nbsp;Change Membership
				<br /> <br />
				<input type="radio" name="updatemembership" id="cancelMembershipBtn">&nbsp;Cancel Membership
				<br /> <br />
				<input type="radio" name="updatemembership" id="holdMembershipRadio">&nbsp;Hold Membership
				
				<div align="center">
					<span class="k-button" id="selectUpdateMem" style="">Select</span>
				</div>
				
				<div id="statusBlock-payment" style="margin-top: 25px;display:block;">						
					<span class="k-block k-error-colored" id="changeMembershipErrSpan" style="display:none;"></span>
				</div>
				<div id="tranfermemberFrm" style="margin-top:15px; padding:10px;">
					
					<div style="float:left;">
						Transfer From Member:<br><br>
						<select name="transferfrom_pick" id="transferfrom_pick" style="width:220px;">
							<option value="Amy">Amy</option>
						</select>
					</div>
					
					<div class="transfer">&nbsp;</div>
					
					<div style="float:left;">
						Transfer To Member:<br><br>
						<select name="transferto_pick" id="transferto_pick" style="width:220px;">
							<option value="Test">Test</option>
						</select>
					</div>
					
					<div style="width:100%; position:absolute; top:535px; left:-195px;" align="right">
						<span id="nextBtn" class="k-button">Next >></span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
			</div>
			
			<div id="cart" style="display:none;">
				<%@ include file="transfercart.jsp" %> 
			</div>
			
			<div id="tandc" style="display:none;">
      			<section>
        			<div class="terms">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
        			<br><br>
        			<input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions
				</section>
				
				<div id="movefromtandc" style="width:100%; position:absolute; top:535px; left:-195px;" align="right"> 
					<span id="gotocart" class="k-button"><< Previous</span>
					<span id="gotosummary" class="k-button">Next >></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				
				<div id="movefromtandc1" style="width:100%; position:absolute; top:535px; left:-195px; display:none;" align="right">
					<span id="gotochangecartp" class="k-button"><< Previous</span>
					<span id="gotonewsummary" class="k-button">Next >></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>
			
			<div id="summary" style="display:none;">
				<div>
					<div>
						<strong>Membership transferred from: </strong><br>
						<span style="font-weight: normal">Kate winslet</span><br>
						<span><strong>Membership ID:</strong></span>&nbsp;IND007
					</div>
					
					<div style="margin-top:30px;">
						<strong>Membership transferred to: </strong><br>
						<span style="font-weight: normal">Amy Wilson</span><br>
						<span><strong>New Membership ID:</strong></span>&nbsp;IND007
					</div>
					
					<div style=" margin-top:20px;">
						<strong>Membership Details: </strong><br>
						<div style="background-color: #fffff9;">
						<table cellpadding="0" cellspacing="10" width="100%" rules="none" style="">
							<tr>
								<td width="230px"><span>${labelVal.get("membershipNo") } </span></td>
								<td width="20px"><strong>:</strong></td>
								<td><span>${member.membershipNumber}</span></td>
							</tr>
							<!-- <tr><td ><hr></td></tr> -->
							<tr>
								<td><span>${labelVal.get("membershipType") }</span></td>
								<td><strong>:</strong></td>
								<td><span>${member.membershipType}</span></td>
							</tr>
							<!-- <tr><td ><hr></td></tr> -->
							<tr>
								<td><span>${labelVal.get("membershipStartDate") }</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programStartDate}" /></span></td>
							</tr>
							<c:if test="${signup.programEndDate != null}">
								<tr>
									<td><span>${labelVal.get("membershipEndDate") }</span></td>
									<td><strong>:</strong></td>
									<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programEndDate}" /></span></td>
								</tr>
							</c:if>
						</table>
						</div>
					</div>
				</div>
				
				<div style="width:100%; position:absolute; top:535px; left:-195px;" align="right"> 
					<span id="gototandcp" class="k-button"><< Previous</span>
					<span id="gotoconfirmation" class="k-button">Confirm Transfer</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>
			
			<div id="confirmation" style="display:none;">
				The Membership has been transferred
			</div>
			
			<!-- change membership -->
			<div id="changemembershipFrm" style="display:none;">
				<div>
					<div style=" margin-top:20px;">
						<strong>Your Membership Details: </strong><br>
						<div style="background-color: #fffff9;">
						<table cellpadding="0" cellspacing="10" width="100%" rules="none" style="">
							<tr>
								<td width="230px"><span>${labelVal.get("membershipNo") }</span></td>
								<td width="20px"><strong>:</strong></td>
								<td><span>${member.membershipNumber}</span></td>
							</tr>
							<!-- <tr><td ><hr></td></tr> -->
							<tr>
								<td><span>${labelVal.get("membershipType") }</span></td>
								<td><strong>:</strong></td>
								<td><span>${member.membershipType}</span></td>
							</tr>
							<!-- <tr><td ><hr></td></tr> -->
							<tr>
								<td><span>${labelVal.get("membershipStartDate") }</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programStartDate}" /></span></td>
							</tr>
							<c:if test="${signup.programEndDate != null}">
								<tr>
									<td><span>${labelVal.get("membershipEndDate") }</span></td>
									<td><strong>:</strong></td>
									<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programEndDate}" /></span></td>
								</tr>
							</c:if>
						</table>
						</div>
					</div>
					
					<div style=" margin-top:20px;">
						<strong>Select New Membership Option: </strong><br><br>
						
						<div style="margin-bottom:20px;">
							Select Location:
							<select name="location" id="location" style="width:250px;">
								<%-- <c:forEach var="location" items="${locations}" varStatus="count">
									<option value="${location.locationId }">${location.branchName }</option>
								</c:forEach> --%>
								<c:forEach var="location" items="${locations}" varStatus="count">
									<c:if test="${ location.branch != 'All Branches' && location.branch != 'Bay Area' }">
										<option value="${location.id }">${location.branch }</option>
									</c:if>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="adult" id="adult">
							Adult Membership
						</div>
						
						<div class="k-block" style="background-color:#FFFFFF;">
						<table cellpadding="0" id="memberlst" width="100%">
							<thead>
								<tr class="lsthead">
									<th>&nbsp;</th>
									<th width="60px;">ID</th>
									<th width="180px;">Product Name</th>
									<th width="240px;">Product Description</th>			
									<th width="140px;">Product Price</th>			
									<th width="60px">&nbsp;</th>
								</tr>
							</thead>
							<tbody id="salesAccData" style="min-height: 200px;">
								
								<c:forEach var="product" items="${products }">
						            <tr>
						            	<td>
											<input type="radio" id="tandc${product.productId }" name="termsAndCondition" class="termsAndCondition" />
										  	<input class="tcSpan" style="display:none;" value="<c:out value='${product.waiversAndTC.tcDescription}'/>"></input>	
										  	<span class="memProductIdSpan"	style="display:none;"><c:out value="${product.productId}"/></span>  
										  	<span class="memProductNameSpan"	style="display:none;"><c:out value="${product.productName}"/></span>  
										  	<span class="memProductDescSpan"	style="display:none;"><c:out value="${product.description}"/></span>  
										  	<span class="memProductTypeSpan"	style="display:none;"><c:out value="${product.productType}"/></span>  
										  	<span class="memProductDurationSpan"	style="display:none;"><c:out value="${product.duration}"/></span>  
										  	<span class="memPricingRuleIdSpan"	style="display:none;"><c:out value="${product.pricingRule.pricingRuleId}"/></span>
										  	<span class="memTierPriceSpan"	style="display:none;"><c:out value="${product.pricingRule.tierPrice}"/></span>						
										</td>
						            	<td width="60px" align="left">${product.productId }</td>
										<td width="180px" align="left">${product.productName }</td>
										<td width="240px" align="left">${product.description }</td>				
										<td width="140px" align="left">$ ${product.pricingRule.tierPrice}</td>
										<td width="60px" align="left">&nbsp;</td>
										<%-- <td>${product.pricingRule.tierPrice }</td> --%>
										
										
						            </tr>
						        </c:forEach>	
							</tbody>
						</table>  
						</div>
					</div>
				</div>
				
				<div style="width:100%; position:relative; margin-top:10px;" align="right"> 
					<span id="gotochangecart" class="k-button">Next >></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>			
			
			<div id="newmembership_summary" style="display:none;">
				<div style=" margin-top:20px;">
					<strong>New Membership Summary: </strong><br>
					<div style="background-color: #fffff9;">
					<table cellpadding="0" cellspacing="10" width="100%" rules="none" style="">
				<tr>
							<td width="230px"><span>${labelVal.get("membershipNo") }</span></td>
							<td width="20px"><strong>:</strong></td>
							<td><span>${member.membershipNumber}</span></td>
						</tr>
						
						<tr>
							<td><span>${labelVal.get("membershipType") }</span></td>
							<td><strong>:</strong></td>
							<td><span>${member.membershipType}</span></td>
						</tr>
						
						<tr>
								<td><span>${labelVal.get("membershipStartDate") }</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programStartDate}" /></span></td>
							</tr>
							<c:if test="${signup.programEndDate != null}">
								<tr>
									<td><span>${labelVal.get("membershipEndDate") }</span></td>
									<td><strong>:</strong></td>
									<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programEndDate}" /></span></td>
								</tr>
							</c:if>
						<tr>
							<td><span>${labelVal.get("membershipStatus") }</span></td>
							<td><strong>:</strong></td>
							<td><span>${signup.status }</span></td>
						</tr>
						<!-- <tr><td ><hr></td></tr> -->
						<tr>
							<td><span>${labelVal.get("membershipFee") }</span></td>
							<td><strong>:</strong></td>
							<td><span>$ ${member.membershipPrice}</span></td>
						</tr>
						<!-- <tr><td ><hr></td></tr> -->
						<tr>
							<td><span>${labelVal.get("membershipFeeFreq") }</span></td>
							<td><strong>:</strong></td>
							<td><span>${signup.paymentFrequency}</span></td>
						</tr>
						<!-- <tr><td ><hr></td></tr> -->
						<c:if test="${signup.programEndDate == null}">
						<tr>
							<td><span>${labelVal.get("membersshipFeeNextBillingDate") }</span></td>
							<td><strong>:</strong></td>
							<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.membersshipFeeNextBillingDate }" /></span></td>
						</tr>
						</c:if>
						<c:if test="${signup.holdStartDate != null}">
							<tr>
								<td><span>Hold Start Date</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdStartDate }" /></span></td>
							</tr>
						</c:if>
						<c:if test="${signup.holdEndDate != null}">
							<tr>
								<td><span>Hold End Date</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdEndDate }" /></span></td>
							</tr>
						</c:if>
					</table>
					</div>
				</div>
				
				<div style="width:100%; position:absolute; top:535px; left:-195px;" align="right"> 
					<span id="gotochangetandcp" class="k-button"><< Previous</span>
					<span id="gotochangeconfirmation" class="k-button">Confirm</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>
			
			<div id="changeconfirmation" style="display:none;">
				The Membership has been changed
			</div>
			
			<div id="membershipHoldDiv" style="display : none;">
				<c:if test="${signup.signUpPricingOption  == 'Monthly'}">
					<div>
						<select id="holdMembershipSelctMonth" name="holdMembershipSelctMonth" style="width:150px;">	
							<option value="0" >--Select Month--</option>											
						</select>
						<span style="margin-left : 10px;">
						<select id="holdMembershipNoOfMonth" name="holdMembershipNoOfMonth" style="width:230px;">	
							<option value="0" >--Select NO of Month(s)--</option>	
							<option value="1">1</option>	
							<option value="2">2</option>									
						</select>
						</span>		
						<span style="margin-left : 30px;">
							<span id="holdMembershipBtn" class="k-button">Hold Membership</span>
						</span>
					</div><br />
					<c:if test="${empty agentUid}">
					<div id="TAndCHoldMembershipDiv" class="termsAndConditionParentDiv">
						<div class="termsAndConditionContent">
							
						</div>									
					</div>
					<input type="checkbox" name="chkTermsAndCondition" style="margin-top: 10px;">&nbsp;I accept terms and conditions
					</c:if>
					<div id="statusBlock" style="margin-top: 45px;">
						<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
						<span class="k-block k-error-colored" id="tcErrorSpan"></span>
					</div>
					<div style="display : none;">
						<span id="holdStartMonthSpan"><fmt:formatDate type="date" pattern="MM" value="${signup.membersshipFeeNextBillingDate }" /></span>
						<span id="membershipSignupFreq">${signup.signUpPricingOption }</span>	
						<span id="membershipHoldStartDate"><fmt:formatDate type="date" pattern="yyyy" value="${signup.holdStartDate }" /></span>	
						<span id="membershipHoldEndDate"><fmt:formatDate type="date" pattern="yyyy" value="${signup.holdEndDate }" /></span>	
					</div>		
					<span style="display : none;" id="commonTAncHidden">${waiversTAndC.tcDescription }</span>	
				</c:if>
				<c:if test="${signup.signUpPricingOption  != 'Monthly'}">
					<div id="statusBlock" style="margin-top: 45px;display:block;">
						<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
						<span class="k-block k-error-colored" id="tcErrorSpan" style="display:block;">You have enrolled in an annual membership Plan. Therefore you can not put the membership on hold</span>
					</div>
				</c:if>
			</div>
			
			<div id="mem_detail">
				<table cellpadding="0" cellspacing="10" width="100%" rules="none" style="" id="myTable">
				<!-- <tr><td><strong>Active Membership : </strong></td>
				<td></td>
				</tr> -->
					<tr>
						<td width="230px"><span>${labelVal.get("membershipNo") }  </span></td>
						<td width="20px"><strong>:</strong></td>
						<%-- <td id="m_id"><span>${signup.contact.facilityAccessNo}</span></td> --%>
						<td id="m_id"><span>${signup.signupId}</span><input type="hidden" id="SignupId" value="${signup.signupId}" ></td>						
						<td id="m_id1" style="display:none;" ><span></span></td>
					</tr>
					<!-- <tr><td ><hr></td></tr> -->
					<tr>
						<td><span>${labelVal.get("membershipType") }  </span></td>
						<td><strong>:</strong></td>
						<td><span>${signup.itemDetail.description }</span></td>
					</tr>
					<!-- <tr><td ><hr></td></tr> -->
					<tr>
								<td><span>${labelVal.get("membershipStartDate") }</span></td>
								<td><strong>:</strong></td>
								<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programStartDate}" /></span></td>
							</tr>
							<c:if test="${signup.programEndDate != null}">
								<tr>
									<td><span>${labelVal.get("membershipEndDate") }</span></td>
									<td><strong>:</strong></td>
									<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.programEndDate}" /></span></td>
								</tr>
							</c:if>
					<!-- <tr><td ><hr></td></tr> -->
					<!-- 
					<tr>
						<td><span>MembershipEndDate</span></td>
						<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy"
								value="${member.membershipEndDate}" /></span></td>
					</tr> -->
					<tr>
 						<td><span>${labelVal.get("membershipStatus") }</span></td>
						<td><strong>:</strong></td>
						<td><span>${signup.status }</span></td>
					</tr>
					<!-- <tr><td ><hr></td></tr> -->
					<tr>
						<td><span>${labelVal.get("membershipFee") }</span></td>
						<td><strong>:</strong></td>
						<td><span>$ ${signup.signupPrice}</span></td>
					</tr>
					
					<tr>
						<td><span>${labelVal.get("membershipFeeFreq") }</span></td>
						<td><strong>:</strong></td>
						<td><span>${signup.paymentFrequency}</span></td>
					</tr>
					<c:if test="${signup.programEndDate == null}">
						<tr>
							<td><span>${labelVal.get("membersshipFeeNextBillingDate") }</span></td>
							<td><strong>:</strong></td>
							<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.membersshipFeeNextBillingDate }" /></span></td>
						</tr>
					</c:if>					
					<c:if test="${signup.holdStartDate != null}">
						<tr>
							<td><span>Hold Start Date</span></td>
							<td><strong>:</strong></td>
							<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdStartDate }" /></span></td>
						</tr>
					</c:if>
					<c:if test="${signup.holdEndDate != null}">
						<tr>
							<td><span>Hold End Date</span></td>
							<td><strong>:</strong></td>
							<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdEndDate }" /></span></td>
						</tr>
					</c:if>
					
					<tr>
						<td><span>${labelVal.get("paymentMethod") }</span></td>
						<td><strong>:</strong></td>
						<c:if test="${selfPayer != null}">
						<%-- <td><span>${paymentInfoLst[0].cardNumber} ${paymentInfoLst[0].expirationMonth}/${paymentInfoLst[0].expirationYear} ${paymentInfoLst[0].paymentType}</span></td> --%>
						<td><span>${selfPayer.paymentMethod.cardNumber} ${selfPayer.paymentMethod.expirationMonth}/${selfPayer.paymentMethod.expirationYear} <%-- ${signup.paymentMethod.paymentType} --%></span></td>
						</c:if>
					</tr>
					<c:if test="${signup.status =='Hold'}">
					<tr>
					<td><span>Hold Start Date</span></td>
					<td><strong>:</strong></td>
					<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdStartDate }" /></span></td>
					</tr>
					
					<tr>
					<td><span>Hold End Date</span></td>
					<td><strong>:</strong></td>
					<td><span><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${signup.holdEndDate }" /></span></td>
					</tr></c:if>

					<c:if test="${signup.draftCycle != null}">
					<tr>
					<td><span>Draft Cycle</span></td>
					<td><strong>:</strong></td>
					<td>
						<table>
							<tr>
								<td>
									<span>${ signup.draftCycle } <span id="linkToDraftCycleUpdate">	<a href="#" onclick="showUpdateDraftCycle()" >Click here to Update Draft Cycle</a> </span>	</span>
								</td>
								<td>
									<div id="updateDraftCycle1" class="k-block" style="width: 200px;">
						                <table style="width: 100%">
						                	<tr align="center">
						                		<td colspan="2">${ signup.draftCycle }
						                			<input type="text" class="k-textbox" id="draftCycleNumber" name="draftCycleNumber" value="${signup.draftCycleNumber}" style="width: 50px" maxlength="2">
						                		</td>
						                	</tr>
							                <tr style="vertical-align: top;" align="center">
							                	<td><div style="text-align: center;">
												<br><span id="saveDraftCycle" class="k-button" >Save</span>
												</div>
												</td>
												<td><div style="text-align: center;">
												<br><span id="cancelDraftCycleUpdate" class="k-button">Cancel</span>
												</div>
												</td>
											</tr>
						                </table>
						            </div>
								</td>
							</tr>
						</table>
					</span></td>
					</tr>
					</c:if>
					<c:if test="${fn:length(paymentDetailsArr)!=0}">
					<tr>
					<td>&nbsp;
					</td>
					<td>&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<span><span id="linkToPMUpdate">	<a href="#" onclick="showUpdatePaymentMethod()" >Click here to Update Payment Method</a> </span>	</span>
								</td>
							</tr>
						</table>
					</td>
					</tr>
					</c:if>
					
					<!-- <tr>
						<td colspan="2"></td>
						<td><span id="upmembershipBtn" class="k-button upmembershipBtnCls">Update Membership</span></td>
					</tr> -->
					<!-- <tr><td ><hr></td></tr> -->
					<!-- 
					<tr>
								<td><span>ProductId</span></td>
							
								<td><span>${member.productId}</span></td>
							</tr> 
							
					
					<tr>
						<td><span>PricingRuleId</span></td>
						<td><span>${member.pricingRuleId}</span></td>
						</tr>
						
					<tr>
						<td><span>ContactName</span></td>
						<td><span>${member.contactName}</span></td>
					</tr>
					
					<tr>
						<td><span>ContactPartyId</span></td>
						<td><span>${member.contactPartyId}</span></td>
					</tr>
					
					<tr>
						<td><span>CustomerPartyId</span></td>
						<td><span>${member.customerPartyId}</span></td>
					</tr>-->
				</table>
				<%-- <table cellpadding="0" cellspacing="10" width="100%" rules="none" style="">
				<tr><td><strong>Hold Membership : </strong></td>
				<td></td>
				</tr>
				<tr>
						<td width="230px"><span>${labelVal.get("membershipNo") }  </span></td>
						<td width="20px"><strong>:</strong></td>
						<td><span>124</span></td>
					</tr>
					<tr>
						<td><span>${labelVal.get("membershipType") }  </span></td>
						<td><strong>:</strong></td>
						<td><span>one Youth No kids</span></td>
					</tr>
					<tr>
 						<td><span>${labelVal.get("membershipStatus") }</span></td>
						<td><strong>:</strong></td>
						<td><span>Hold</span></td>
					</tr>
					</table> --%>
					
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
							//alert(convertJsonDate(paydate));
							$("#paymentDate_${payment.transactionId }").html(convertJsonDate(paydate));
						</script>
					</c:forEach>
				</c:otherwise>
				</c:choose>
				
			</table>
		</div>
					
			</div>
	</div>
</div>
</c:when>
<%-- </c:choose> --%>

<c:otherwise>
<div id="noMemberShip" class="k-block" style="background-color:#FFFFFF; padding:9px;margin-left:7px;width:120%;height:1000000%;">
	<center><font color="	#eb8120" ><c><b> ${NoMembersHip }</b></c></font><br></center>
	<br>
	<!-- <center>Your membership is <b>inactive</b>. Please activate your membership..!</center> -->
	<center>Please activate your membership..!</center>
	
	<center><span id="regNow" onclick="location.href='becomeMember'" class="k-button" style="margin-top:20px; width: 150px; text-shadow: none;">ACTIVATE</span></center>
	
	
	
	</div>
</c:otherwise>
	
</c:choose>
</div>
