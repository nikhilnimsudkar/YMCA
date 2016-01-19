<%@ include file="../../layouts/include_taglib.jsp"%>
<div id="membershipChikin">
	<!-- <div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div> -->
	<%-- <c:choose>
	<c:when test="${accessDenied==null }"> --%>
	
	<div id="tabstrip3${usInfo.contactId}" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; margin-left: 15px; width: 708px; background-color: white; margin-top: 14px;">
	<h1 align="center">${usInfo.personFirstName } ${usInfo.personLastName }</h1>
	<c:choose>
	<c:when test="${signup!=null }">
	<h3 align="center">${signup.itemDetail.description }</h3>
	<h3 align="center">${signup.location.recordName }</h3>
	<br>
	</c:when>
	<c:otherwise>
	<h3 align="center">Member has no ACTIVE Signup.</h3>
	 
	</c:otherwise>
	</c:choose>
	<c:if test="${inactiveMembership!=null}">
	<div id="inactiveMembership"><h3 align="center">${inactiveMembership }</h3></div>
	</c:if>
	<div id="accGranted${usInfo.contactId}" style="display:none;"> <h3  align="center" >${accessGranted }</h3></div>
	<div id="accDeny${usInfo.contactId}" style="display:none;"><b  align="center" >${accessDenied }</b></div>
	<div id="sexOffResonDiv${usInfo.contactId}" style="display:none;">
	<select id="sexOffResonList${usInfo.contactId}" style="width:auto" onchange="selectReason('${usInfo.contactId}',this)">
         <option value="0">--Select Reason--</option>
         <option value="1">Executive director granted access</option>
         <option value="2">Expired Membership, Limited Access</option>
    </select></div>
	<div id ="accDenySexOff${usInfo.contactId}" style="display:none;"><b align="center">Please select a reason.</b></div>
	<div id="unAuthAccess${usInfo.contactId}" style="display:none;" align="center"> <%-- <span id="unAuthAccButton${usInfo.contactId}" class="k-button" style=" margin-left: 15px;" onclick="unAuthAccess('${usInfo.contactId}')" > --%>
		<%--
		<span id="unAuthAccButton${usInfo.contactId}" class="k-button" style=" margin-left: 15px;" onclick="displayReasonList('${usInfo.contactId}')" > Grant Access </span>
		 --%>
		 <span id="unAuthAccButton${usInfo.contactId}" class="k-button" style=" margin-left: 15px;" onclick="contactSystemDirector('${usInfo.contactId}')" >Contact System Director</span>
		 <br>
		 <span style="margin-top:5px; margin-bottom:10px;">(Once system director approve the member access, please check-in the member again)</span>
		 <br><br>
	</div>
	<!-- <img src="A:/YMCA-06-04-15/ymca/web/src/main/webapp/resources/img/portal_Images/defaultpic.jpg" alt="" ></img> -->
	
	<div id="totalChkdCountOld${usInfo.contactId}">
	<input type="text" id="totalChkdInCountHiden${usInfo.contactId}"  style="display:none;" value="${runnignCount }"/>
	Checked-In Count: ${runnignCount } </div>
	
	 <div  id="lastChkedInDate${usInfo.contactId}"> 
	 
	 Last Checked-In Date:<fmt:formatDate  pattern="MM/dd/yyyy" value="${ activity.createdDate}" /></div><br>
	<div>Average check-in/week : ${avgWeeklyCheckin } </div> <div> Average check-in/month : ${avgMonthlyCheckin }</div>
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
	
	<c:if test="${ userCount >= 1}">
	<c:forEach var="Secmember" items="${userS}" varStatus="count">
	<div class="k-block" id="Add_member_profile${Secmember.contactId}"  style="min-height: 0px; padding: 5px 5px 25px; background-color: white; width: 706px; margin-top: 3px; margin-left: 13px; display:none;">
	<div id="tabstrip3${Secmember.contactId}" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; background-color: rgb(255, 0, 0); margin-left: -3px; margin-top: -3px; width: 701px;">
	<h1 align="center">${Secmember.personFirstName } ${Secmember.personLastName }</h1><br>
	
	<h3 align="center"><span  id="signupMemShip${Secmember.contactId}" >${signup.itemDetail.description}</span></h3>
	<h3 align="center"><span  id="secMemLocation${Secmember.contactId}" ></span></h3>
	
	<div id="accGranted${Secmember.contactId}" style="display:none;"> <h3  align="center" >${accessGranted }</h3></div>
	<div id="accDeny${Secmember.contactId}" style="display:none;"><h3  align="center" >${accessDenied }</h3></div>
	<%-- <div id="sexOffResonDivSec${Secmember.contactId}" style="display:none;">
	<select id="sexOffResonListSecMem${Secmember.contactId}" style="width:auto" onchange="selectReason('${Secmember.contactId}',this)">
         <option value="0">--Select Reason--</option>
         <option value="1" >Executive director granted access</option>
    </select></div> --%>
    <div id="sexOffResonDiv${Secmember.contactId}" style="display:none;">
	<select id="sexOffResonList${Secmember.contactId}" style="width:inherit;" onchange="selectReason('${Secmember.contactId}',this)">
         <option value="0">--Select Reason--</option>
         <option value="1">Executive director granted access</option>
         <option value="2">Expired Membership, Limited Access</option>
    </select></div>
	<div id ="accDenySexOff${Secmember.contactId }" style="display:none;"><b align="center">Please select a reason.</b></div>
	<div id="unAuthAccess${Secmember.contactId}" style="display:none;" align="center"> <span id="unAuthAccButton${Secmember.contactId}" class="k-button" style=" margin-left: 15px;" onclick="displayReasonList('${Secmember.contactId}')" > Grant Access </span></div>
	<div id="totalChkdCountOld${Secmember.contactId}">
	
	<input type="text" id="totalChkdInCountHiden${Secmember.contactId}"  style="display:none;" value="${runnignCount }"/>
	 </div>
	<div > <span id="secMemChkdInCount${Secmember.contactId}" ></span></div>
	<div><span  id="secMemChkdInDate${Secmember.contactId}" ></span></div>
	<div  id="lastChkedInDate${Secmember.contactId}"> </div>
	<div id="avgCheckinWeek${Secmember.contactId}">Average check-in/week : ${avgWeeklyCheckin}</div> <div id="avgCheckinMonth${Secmember.contactId}"> Average check-in/month : ${avgMonthlyCheckin}</div>
	</div>
	</div>
	</c:forEach>
	</c:if>
	<div id="msgs" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; margin-left: 15px; width: 708px; background-color: white; margin-top: 14px;">
	<h4>Messages:</h4>
	<div id="unAuthMsg" style="display:none;">You are checking-in an unauthorized member.</div>
	<div id="unAuthMSgDenied" style="display:none;">You have already checked-in the unauthorized member 3 times.</div>
	</div>
	<div>
	<span id="CloseChekinWind" class="k-button"  style="margin-top: 4px; width: 113px; margin-left: 340px;" onclick="closeAllwindow1();" > DONE </span>
	</div>
	
	
	
	<script type="text/javascript">
	
	/* function primaryMbershipCheck(memberContactId){
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
							 $("#allowGuest").show();
							 $("#accGranted").show();
						 }else if(data=='DENY')
							 {
							 $("#checkPriMemship").hide();
							
							 document.getElementById("tabstrip3").style.backgroundColor = "red";
							 $("#accDeny").show();
							 $("#unAuthAccess").show();
							 $("#allowGuest").show();
							 }
						
					}
				});
		} 
	}
	 */
	
	/* function unAuthAccess(memberContactId){
		alert("Allow UnAuthorised access .");
		alert(memberContactId);
		//$("#checkPriMemship").hide();
		// document.getElementById("tabstrip3").style.backgroundColor = "red";
		// $("#accDeny").show();
		//$("#m_id").val(12);
		 if (memberContactId != '') {
			
			 $.ajax({
					url : "allowUnAuthorisedAccess?memberContactId=" + memberContactId,
					success : function(data) {
						alert(data);
						 if(data=='GRANTED'){
							 alert("granted");
							 $("#checkPriMemship").hide();
							 document.getElementById("tabstrip3").style.backgroundColor = "green";
							 $("#unAuthAccess").hide();
							 $("#accGranted").show();
							 $("#accDeny").hide();
							 $("#inactiveMembership").hide();
							 $("#unAuthMsg").show();
						 }else if(data=='DENY')
							 {
							 $("#unAuthAccess").hide();
							 $("#checkPriMemship").hide();
							 document.getElementById("tabstrip3").style.backgroundColor = "red";
							 $("#accDeny").show();
							 
							 $("#unAuthMSgDenied").show();
							 }
						
					}
				});
		} 
	} */
</script>



	