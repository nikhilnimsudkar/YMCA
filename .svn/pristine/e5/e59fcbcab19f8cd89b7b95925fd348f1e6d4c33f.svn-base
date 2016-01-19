<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String contextPath = request.getContextPath();
%>
<script src="<%=contextPath %>/resources/js/app/common-util.js"></script>
<script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
<script src="<%=contextPath %>/resources/js/program-cart-new.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<!-- <script src="<%=contextPath %>/resources/js/app/childcare-cart.js"></script> -->
<body>

<!-- hidden fields starts here  -->
<input type="hidden" id="familyContactPageType">
<!-- hidden fields ends here  -->
<div id="main">
	<div id="content">
		<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
	
		<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF; padding:12px; width:100%">	
		<!-- <form id="ccSignupFrm" method="post" action="childcaresignup">	 -->
			<input type="hidden" id="pageType" value="CHILD CARE" />
			<input type="hidden" id="urlItemContactPromo" value="">
			<div id="searchchildcare" style="display:none;">
				<%@ include file="searchchildcare.jsp" %>
			</div>
			<div id="childcare_details">
				<div id="childcare">
					<div id="childcare_session">
					
					</div>
					
					<div id="inservice_session">
					
					</div>
					
					<div id="afterschool_session">
					
					</div>
					
					<div align="center" style="margin: 10px;">
						<span id="continueToSignup" class="k-button" style="display: none;">CONTINUE TO SIGN UP</span>
					</div>
				</div>
				
				<div id="inserviceDaysselect" style="display:none;">
					<%@ include file="inserviceDays.jsp" %>
				</div>
				
				<div id="familymembers" style="display:none;">
					<%@ include file="../common/familymembers.jsp" %>
				</div>
				
				<div style="clear: both;"></div> 
				<div id="prgcart">
					<%@ include file="../programs_cart.jsp" %> 
				</div>
				
				<div id="tandc" style="display:none;">
					<div id="termsDiv" class="formatdiv k-shadow" style="margin-left: 20px; padding: 15px; width: 735px; overflow-y: scroll; height:300px;">     
						${terms }
	            	</div>
	            	<br><br>
				   	<div class="formatdiv" style="margin-left:20px;">
				   		<input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions
				   	</div> 
				   	<br><br>
					<div class="formatdiv" style="margin-left:20px;">
						<span id="confirmsignup" class="k-button">Confirm</span>
					</div>
				</div>
				<div class="k-window1" id="popup_add_common" style="display:none;"></div>
				<div id="contactHealthHistoryDiv" style="display:none;">
					<%@ include file="../common/contactHealthHistory.jsp" %>
				</div>
				
				<div id="emergencyContact" style="display:none;">
					<%@ include file="../common/emergencyContact.jsp" %>
				</div>
				
				<div id="authorisedContact" style="display:none;">
					<%@ include file="../common/authorisedContact.jsp" %>
				</div>
			
				<div id="purchase" style="display:none;">
					<%@ include file="../payment/payment_cart.jsp" %> 
				</div>
				
				<div id="contactActivityDiv" style="display:none;">
					<jsp:include page="../signup/itemActivity.jsp" ></jsp:include>
				</div>
				<div id="contactTransportDiv" style="display:none;">
					<jsp:include page="../signup/itemTransport.jsp" ></jsp:include>
				</div>
			
				<div style="clear: both;"></div> 
				<div id="statusBlock">
					<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
					<span class="k-block k-error-colored" id="tcErrorSpan"></span>
				</div>
			</div>
		<!-- </form>  -->
		</div>
	</div>
</div>
</body>

<script src="<%=contextPath %>/resources/js/childcare.js"></script>
<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style="position:absolute; bottom:-10px; left:-10px;"></iframe>
<style>
	.k-grid  .k-grid-header  .k-header {
	    white-space: normal;
	    word-wrap: break-word;
	}
</style>

<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<script>
	$(document).ready(function() {
		
		var urlPromoItemDetailId = '${urlPromoItemDetailId}';
		var urlPromoContactId = '${urlPromoContactId}';
		var urlPromoCode = '${urlPromoCode}';
		
		/* console.log("  urlPromoItemDetailId  ::  "+urlPromoItemDetailId);
		console.log("  urlPromoContactId  ::  "+urlPromoContactId);
		console.log("  urlPromoCode  ::  "+urlPromoCode); */
		
		if(window.location.pathname != ''){
			
			var pathname = window.location.pathname;
			var paths = pathname.split("/");
			//console.log("  paths[paths.length-1] >> "+paths[paths.length-1]);
			
			if(paths[paths.length-1] != 'childcare'){
				$("#searchchildcare").hide();
			}
		}
		$("#tandc").hide();
		$("input[type='checkbox']").uniform();
		
		var isUserLoggedin = '${pageContext.request.userPrincipal.isAuthenticated()}';
		if(!isUserLoggedin){
			$("#cart-header").hide();
		}
		
		$("#confirmsignup").click(function(){
			
			if(!$("input[name='chkTermsAndCond']:checked").val()){
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
		        		
				return false;
	 		}else{
	 			fnCheckout();
	 		}
		});
		
		$( "#continueToSignup" ).click(function(){
			console.log("  continueToSignup  ");

			addChildcareProductToCart();
		});
		
		var signupId = "${signupId}";
		if(typeof signupId!='undefined' && signupId!=""){
			//alert(signupId);
			$("#searchchildcare").hide();
			 getChildcareGrid(signupId);
		}
		
		var showcart = getParameterByName('showcart');
		
		if(typeof showcart!='undefined' && showcart=="Y"){
			$("#searchchildcare").hide();
			setTimeout(function(){
				showCartInfo();
			}, 500);
			
			//getChildcareGrid(signupId);
		}else{
			$("#searchchildcare").show();
		}
		
		var gotocontactage = "${gotocontact}";
		//console.log("  gotocontactage  "+gotocontactage);
		if(typeof gotocontactage!='undefined' && gotocontactage=='true'){
			
			var selectItemContact = '';
			 if(urlPromoItemDetailId != undefined && urlPromoItemDetailId != null && urlPromoItemDetailId != ''){
				 if(urlPromoContactId != undefined && urlPromoContactId != null && urlPromoContactId != ''){
					 selectItemContact = urlPromoItemDetailId+"_"+urlPromoContactId+"_"+urlPromoCode;
				 }else{
					 selectItemContact = urlPromoItemDetailId+"_0_"+urlPromoCode; 
				 }
				 $("#urlItemContactPromo").val(selectItemContact);
				 //console.log("if selectItemContact "+selectItemContact);
			 }else{
				 selectItemContact =  $.sessionStorage.getItem('urlItemContactPromo');
				 //console.log("from session selectItemContact "+selectItemContact);
				 $("#urlItemContactPromo").val(selectItemContact);
			 }
			
			
			getFamilymembers(selectItemContact);
			
			$("#childcare").hide();
			$("#searchchildcare").hide();
		}
		
		if(urlPromoItemDetailId != null && urlPromoItemDetailId != undefined && urlPromoItemDetailId != '')
			addChildcareProductToCart();
		
	});
	
	function addChildcareProductToCart(){
		
		//console.log("   addChildcareProductToCart   ");
		
		var urlPromoItemDetailId = '${urlPromoItemDetailId}';
		var urlPromoContactId = '${urlPromoContactId}';
		var urlPromoCode = '${urlPromoCode}';
		
		/* console.log("  urlPromoItemDetailId 11 ::  "+urlPromoItemDetailId);
		console.log("  urlPromoContactId 11 ::  "+urlPromoContactId);
		console.log("  urlPromoCode 11 ::  "+urlPromoCode); */
		
		var item_Detail_Ids = [];
		var ccWaitlisted = [];
		
		if(urlPromoItemDetailId != undefined && urlPromoItemDetailId != ''){
			item_Detail_Ids.push(urlPromoItemDetailId);
		}
		
		$('#childcare_list').find('input[name="days_slot"]').each(function(){
			if($("#slot_"+this.value).is(':checked')){
				var itemdayId = this.value;
				var itemDetailsId = itemdayId.split("_")[1];
				item_Detail_Ids.push(itemDetailsId);
				
				//alert(itemdayId);
				//alert($("#capacity"+itemdayId).val());
				if($("#capacity"+itemdayId).val()<=0){
					//alert(itemDetailsId+"_"+daysArr[dId]);
					ccWaitlisted.push("\n"+$("#ItemDetailsType_"+itemDetailsId).val()+":- "+daysArr[itemdayId.split("_")[0]]);
				}
			}
		});
		//alert(ccWaitlisted);
		
		var inServiceItemDetailId =  $("input[name=inServiceRadio]:checked").val();
		if(inServiceItemDetailId && inServiceItemDetailId > 0){
			item_Detail_Ids.push(inServiceItemDetailId);
		}
		
		var afterSchoolItemDetailId =  $("input[name=afterSchoolRadio]:checked").val();
		if(afterSchoolItemDetailId && afterSchoolItemDetailId > 0){
			item_Detail_Ids.push(afterSchoolItemDetailId);
			
			if($("#asCapacity_"+afterSchoolItemDetailId).val()<=0){
				//alert(itemDetailsId+"_"+daysArr[dId]);
				ccWaitlisted.push("\n"+$("#asItemDetailsType_"+afterSchoolItemDetailId).val());
			}
		}
		
		
		item_Detail_Ids = ArrNoDupe(item_Detail_Ids);
	
		if(item_Detail_Ids==""){
			$("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "block");		
			$( "#tcErrorSpan" ).html("Please select atleast one child care program");
			return;
		}
		//alert(item_Detail_Ids);
		if(ccWaitlisted.length>0){
			if(confirm("You will be waitlisted for the following Session:"+ccWaitlisted.join(", "))){}
			else
				return;
		}
		
		$("#tcSuccessSpan").css("display", "none");
		$("#tcSuccessSpan" ).html("");
		$("#tcErrorSpan").css("display", "none");
		$( "#tcErrorSpan" ).html("");
		
		//alert(item_Detail_Ids);
		$.sessionStorage.setItem('itemDetailsId', item_Detail_Ids.join(','));
		
		//alert(inServiceItemDetailId);
		/*
		var inServiceSignupType =  $("input[name=inServiceSignupType]:checked").val();
		if(typeof inServiceItemDetailId!='undefined' && inServiceSignupType=='DAYS'){
			//alert(); 
			showInserviceIndividualDays(inServiceItemDetailId);
			$("#inserviceDaysselect").show();
		}
		else */
		
		var isUserLoggedin = '${pageContext.request.userPrincipal.isAuthenticated()}';
		
		if(isUserLoggedin){
			
			var selectItemContact = '';
			
			if(urlPromoItemDetailId != undefined && urlPromoItemDetailId != null && urlPromoItemDetailId != ''){
				 if(urlPromoContactId != undefined && urlPromoContactId != null && urlPromoContactId != ''){
					 selectItemContact = urlPromoItemDetailId+"_"+urlPromoContactId+"_"+urlPromoCode;
				 }else{
					 selectItemContact = urlPromoItemDetailId+"_0_"+urlPromoCode; 
				 }
					
				//console.log("if selectItemContact "+selectItemContact);
				$.sessionStorage.setItem('urlItemContactPromo', selectItemContact);
				$("#urlItemContactPromo").val(selectItemContact);
			 }else{
				 selectItemContact =  $.sessionStorage.getItem('urlItemContactPromo');
				 //console.log("from session selectItemContact "+selectItemContact);
				 $("#urlItemContactPromo").val(selectItemContact);
			 }
			
			
			getFamilymembers(selectItemContact);
		
			$("#childcare").hide();
			$("#searchchildcare").hide();
		}
		else{
			
			var selectItemContact = '';
			if(urlPromoItemDetailId != undefined && urlPromoItemDetailId != null && urlPromoItemDetailId != ''){
				if(urlPromoContactId != undefined && urlPromoContactId != null && urlPromoContactId != ''){
					 selectItemContact = urlPromoItemDetailId+"_"+urlPromoContactId+"_"+urlPromoCode;
				 }else{
					 selectItemContact = urlPromoItemDetailId+"_0_"+urlPromoCode; 
				 }
				 //console.log("else selectItemContact "+selectItemContact);
				 $.sessionStorage.setItem('urlItemContactPromo', selectItemContact);
				 $("#urlItemContactPromo").val(selectItemContact);
			 }
			
			var window = $("#popup_add");
			window.clone().kendoWindow({
				title : "Login",
				content : "loginPopup?dispatchTo=ChildCare",
				modal : true,
				width : "500px",
				height : "550px",
				data: { dispatchTo: 'program' },
				close : onClose
				
			}).data("kendoWindow").center().open();
			
			var onClose = function() {
		    }
			$('Div[data-role="draggable"]').css('top', '50px');
		}
	
	}
	
</script>