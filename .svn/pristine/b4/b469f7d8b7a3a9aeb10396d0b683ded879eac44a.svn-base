<%@ include file="../../layouts/include_taglib.jsp"%>
<div id="membershipChikin">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<%-- <c:choose>
	<c:when test="${accessDenied==null }"> --%>
	
	<div id="tabstrip3" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; margin-left: 15px; width: 708px; background-color: white; margin-top: 14px;">
	<h1 align="center">${usInfo.personFirstName } ${usInfo.personLastName }</h1>
	<h3 align="center">${signup.itemDetail.description }</h3>
	<br>
	<span id="checkPriMemship" class="k-button" style="align-self: center; font-size: 22px; height: 38px; width: 159px; margin-left: 274px;" onclick="primaryMbershipCheck('${usInfo.contactId}')">CHECK-IN</span>
	<div id="accGranted" style="display:none;"> <h3  align="center" >${accessGranted }</h3></div>
	<div id="accDeny" style="display:none;"><h3  align="center" >${accessDenied }</h3></div>
	
	<!-- <img src="A:/YMCA-06-04-15/ymca/web/src/main/webapp/resources/img/portal_Images/defaultpic.jpg" alt="" ></img> -->
	</div>
	<%-- </c:when>
	<c:otherwise>
	<div id="tabstrip3" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; margin-left: 15px; width: 708px; background-color: red; margin-top: 14px;">
	<h1 align="center">${usInfo.personFirstName } ${usInfo.personLastName }</h1>
	<h3 align="center">${signup.itemDetail.description }</h3>
	<br>
	<h3 align="center">${accessDenied }</h3>
	<h3 align="center">No Active Membership..!!</h3>
	</div>
	</c:otherwise>
	</c:choose> --%>
	
	</div>
	
	
	<script type="text/javascript">
	
	function primaryMbershipCheck(memberContactId){
		alert("Membershipid contact id primary meber");
		alert(memberContactId);
		//$("#checkPriMemship").hide();
		// document.getElementById("tabstrip3").style.backgroundColor = "red";
		// $("#accDeny").show();
		//$("#m_id").val(12);
		 if (memberContactId != '') {
			
			$.ajax({
				url : "checkIn_PrimaryMember?memberContactId=" + memberContactId,
				success : function(data) {
					alert(data);
					 if(data=='GRANTED'){
						 alert("granted");
						 $("#checkPriMemship").hide();
						 document.getElementById("tabstrip3").style.backgroundColor = "green";
						 $("#accGranted").show();
					 }else if(data=='DENY')
						 {
						 $("#checkPriMemship").hide();
						 document.getElementById("tabstrip3").style.backgroundColor = "red";
						 $("#accDeny").show();
						 }
					
				}
			});
		} 
	}
</script>
	