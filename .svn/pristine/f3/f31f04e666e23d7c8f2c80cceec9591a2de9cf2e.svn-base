<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp" %>
    
    <%
    	String contextPath = request.getContextPath();
    %>
    <link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>
	<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
	<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
	
	<link rel="stylesheet" href="<%=contextPath %>/resources/css/jquery-ui.css" type="text/css" media="all" />  
	<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
	<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
	<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
	<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
	
	<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>
    
<body>
<div class="k-window1" id="popup_add" style="display:none;"></div>
<div class="k-loading-mask"
		style="width: 100%; height: 100%; position: absolute; top: 150px; left: 0px; display: none; z-index: 9999">
	<span class="k-loading-text">Donation cancelled Successfully.</span>
	<div class="k-loading-image">
		<div class="k-loading-color"></div>
	</div>
</div>
<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
<div id="main">
	<div id="content">			
		<div id="myprofile">
			<%@ include file="donationProfile.jsp" %> 
			<!-- <div id=""  class="k-block" style="width: 255px; margin-left: 45px; background: white; ">
				<ul>
					<li><span>If you want to pay off balance, please contact Y agent prior to cancellation.<br /></span></li>
			  		
			  	</ul>
			</div> -->
		</div>
		<div id="donationDetailDiv">
			<div id="donationRightSection" style="  margin-left: 35px;">
				<%@ include file="donationUpdatePaymentMethod.jsp" %> 
			</div>		
						 
		</div>
	</div>
</div>
</c:if>
</body>
<script id="delete-confirmation" type="text/x-kendo-template">
    <p class="delete-message">Are you Sure you want to cancel your donation?</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Yes</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">No</span></div>
</script>

<script>
	$(document).ready(function() {		
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','');
		$("#top_donation").attr('class','current');
		
		$("#page_name").html("DONATION INFORMATION");
		$("#page_name").css("font-size", "26px");
		
		var isUserLoggedin = '${pageContext.request.userPrincipal.isAuthenticated()}';
		
		if(isUserLoggedin){
			//getFamilymembers();
			$("#cart-header").show();
		}
		else{
			//location.href = 'addprogramtocart';
			//window.open('addprogramtocart');
			var window = $("#popup_add");
			window.clone().kendoWindow({
				title : "Login",
				content : "loginPopup?dispatchTo=Donation",
				modal : true,
				width : "500px",
				height : "550px",
				data: { dispatchTo: 'program' },
				close : onClose
				
				//deactivate : function(e) {
				//	this.destroy();
				//}
			}).data("kendoWindow").center().open();
			
			var onClose = function() {
				 //$("#add_member").show();
			}
			
			$('Div[data-role="draggable"]').css('top', '50px');
		}
		
		$('body').on( "click", "span.cancelDonationCls", function() { 	
			var donationId =  $(this).parent().find(".donationIdHidSpan").html();	
			var kendoWindow = $("<div />").kendoWindow({
	            title: "Confirm",
	            resizable: false,
	            modal: true,
	            width:400
	        });
			
			kendoWindow.data("kendoWindow")
	        .content($("#delete-confirmation").html())
	        .center().open();
			
			 kendoWindow
		        .find(".delete-confirm,.delete-cancel")
		            .click(function() {
		                if ($(this).hasClass("delete-confirm")) {
		                	if(donationId) {
		        				$(".k-loading-mask").show();
		        				$(".k-loading-text").html("Please wait");
		        				$("#tcSuccessSpan").css("display", "none");		
		        				$("#tcSuccessSpan" ).html("");	
		        				$("#tcErrorSpan").css("display", "none");		
		        				$( "#tcErrorSpan" ).html("");
		        				$.ajax({
		        					  type: "POST",
		        					  url: "cancelDonation",	
		        					  data: { donationId: donationId},
		        					  success: function( data ) {				 
		        						  if(data != null && data == "SUCCESS"){
		        								//$("#tcSuccessSpan").css("display", "block");		
		        								//$("#tcSuccessSpan" ).html("Donation cancelled Successfully.");	
		        								//$("#tcErrorSpan").css("display", "none");		
		        								//$( "#tcErrorSpan" ).html("");		
		        								//$(".k-loading-mask").show();
		        								//$(".k-loading-text").html("Donation cancelled Successfully.");
		        								//setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
		        						  		setTimeout(function(){$(".k-loading-mask").show();$(".k-loading-text").html("Donation cancelled Successfully.");$(".k-loading-text").fadeIn("slow");}, 300);
		        					  	  		//setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 500);
		        								setTimeout(function(){location.href='donationHome';}, 4000);
		        						  }else {
		        								//$("#tcSuccessSpan").css("display", "none");		
		        								//$("#tcSuccessSpan" ).html("");	
		        								//$("#tcErrorSpan").css("display", "block");		
		        								//$( "#tcErrorSpan" ).html("Error Occured while cancelling the Donation.");
		        							  	setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
		        							  	setTimeout(function(){$(".k-loading-mask").show();$(".k-loading-text").html("Error Occured while cancelling the Donation. Please try again later.");$(".k-loading-text").fadeIn("slow");}, 300);
		        					  	  		setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 4000);
		        						  }
		        						  $(".k-loading-mask").hide();
		        					  },
		        					  error: function( xhr,status,error ){
		        								//$("#tcSuccessSpan").css("display", "none");		
		        								//$("#tcSuccessSpan" ).html("");	
		        								//$("#tcErrorSpan").css("display", "block");		
		        								//$( "#tcErrorSpan" ).html("Error Occured while cancelling the Donation.");
		        								setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
		        							  	setTimeout(function(){$(".k-loading-mask").show();$(".k-loading-text").html("Error Occured while cancelling the Donation. Please try again later.");$(".k-loading-text").fadeIn("slow");}, 300);
		        					  	  		setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 4000);
		        								$(".k-loading-mask").hide();					  		 
		        					  }
		        				});
		        			}else{
		        				alert("There was some error. Please try again later");
		        				$(".k-loading-mask").hide();	
		        			}
		                }
		                
		                kendoWindow.data("kendoWindow").close();
		            })
		            .end()			
		});
		
		
	});
</script>