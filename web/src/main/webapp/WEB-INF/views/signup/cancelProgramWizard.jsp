<%@ include file="../../layouts/include_taglib.jsp"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    <script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
<body>

<div id="main">
	
	<div id="content">
		<div id="searchprogram" class="k-block">
			<%@ include file="searchallprogram.jsp" %> 
		</div>
		
		<div id="programs">
			<div id="programinfoPg">
				<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:350px; left:0px;display:none; z-index:9999">
					<span class="k-loading-text">Loading... Please wait</span>
					<div class="k-loading-image" style="top:300px;">
						<div class="k-loading-color"></div>
					</div>
				</div>
				<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF;margin-left:-52px;width:825px;min-height:300px; padding: 5px 5px 25px;">
					
					<c:choose>
						<c:when test="${fn:length(enrolledProgramDetailsArr)==0}">
							<br>
							<div class="k-block k-error-colored">Your cancellation period has expired, Please call Y agent to cancel the program.</div>
						</c:when>
						<c:otherwise>
							<c:forEach var="program" items="${enrolledProgramDetailsArr}" varStatus="status">
								<div id="productname"><h1>${program.programName }</h1></div>	
								<div id="programdetails" style="padding: 0px 0px 22px 30px; margin:12px;" class="k-block k-shadow">
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
									
									<c:choose>
										<c:when test="${program.noOfTickets> 0}">
											<div id="noOfTicketsOrPackages">
												<span id="noOfTicketsOrPackagesLabel">No of Tickets:</span> ${program.noOfTickets }
											</div>
										</c:when>
									</c:choose>
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
							
								<div style="margin:20px;">
									<span class="head">Cancellation Summary</span>
								</div>
								
								<div style="margin-left:30px;">
									<c:if test="${program.reimbursedAmount>0}">
										<div class="formatdiv">
											<span>Reimbursed amount :</span>
											<span><fmt:formatNumber value="${program.reimbursedAmount }" type="currency"/></span>
										</div>
									</c:if>
									
									<div class="formatdiv">
										<c:choose>
										<c:when test="${program.noOfTickets > 0}">
											<span>Cancellation charges :</span>
										</c:when>
										<c:otherwise>
											<span>Included Cancellation charges :</span>
										</c:otherwise>
										</c:choose>
										<span><fmt:formatNumber value="${program.cancellationPrice }" type="currency"/></span>
									</div>
								</div>
								<br><br>
								
								<div id="termsDiv" class="formatdiv k-shadow" style="margin-left: 20px; padding: 15px; width: 735px; overflow-y: scroll; height:300px;">     
									${terms }
				            	</div> 
				            	<br><br>
				            	<div class="formatdiv" style="margin-left:20px;">
				            		<input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions1
				            	</div> 
				            	<br><br>
								<div class="formatdiv" style="margin-left:20px;">
									<span id="confirmcancel" class="k-button">Confirm Cancel</span>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
					<br>
					<div id="statusBlock">
						<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
						<span class="k-block k-error-colored" id="tcErrorSpan"></span>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>

</body>

<style>
	table tr td {
	    padding-left: 5px;
	}
</style>

<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<script>
	$(document).ready(function(){ 
		$( "#confirmcancel" ).click(function(){
			 var sId = getParameterByName('sId');
			 $("#confirmcancel").hide();
			 //alert(sId);
			 $(".k-loading-mask").show();
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
			        		
		 			$(".k-loading-mask").hide();
		 			$("#confirmcancel").show();
					return false;
		 	}
			 
			 $.ajax({
				  type: "GET",
				  url:"cancelsignup?sId="+sId,
				  success: function( data ) {
					 //console.log(data);
				  	 //console.log(data.length);
				  	 $(".k-loading-mask").hide();
				  	 if(data=='SUCCESS'){
				  		 $("#tcErrorSpan").hide();
				  		 $("#tcSuccessSpan").show();
				  		 $("#tcSuccessSpan").html("If you are eligible for a refund, Your Refund will be processed within the next 5 to 7 business days");
				  		 $("#confirmcancel").hide();
				  	 } else{
				  		 $("#tcSuccessSpan").hide();
				  		 $("#tcErrorSpan").show();
				  		 $("#tcErrorSpan").html("There is some error, please try again after some time!");
				  		 $("#confirmcancel").show();
				  	 }
				  }
			});
		});
	});
</script>