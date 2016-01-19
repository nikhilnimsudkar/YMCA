<%@ include file="../../layouts/include_taglib.jsp" %>
<div id="member_profile_1">
	<div id="tabstrip" class="k-block" style="border-color:#fcaf17;">
		<ul>
			<li class="k-state-active">
				<span class="head">PRIMARY FAMILY CONTACT INFORMATION</span>
			</li>
		</ul>
		 <div>
			<div id="team">
				<div id="team_info" style="float: left; padding-top: 41px; margin-left: 4px; width: 119px;">
					<span class="name" style="margin-left:1px;">${usInfo.personFirstName } ${usInfo.personLastName }</span>
					<br>
					<span id="agecal" onload="calculateage('${usInfo.dateOfBirth}')">Age:  ${usInfo.age}      </span>
					<%-- <span id="primContId" name="primContId" style="display:none;" value="${usInfo.contactId}"></span> --%>
					<input type="text" id="primContId"  style="display:none;" value="${usInfo.contactId}"/>
					<input type="text" id="sexOffenderRed${usInfo.contactId}"  style="display:none;" value="${usInfo.sexOffender }"/>
					<br>
					<p id="unAuthAccCountOld${usInfo.contactId}" style="font-size: 11px;">Unauthorized Access Count:${usInfo.unauthorizedAccessCount}</p>
					<input type="text" id="unAuthAccCountHidden${usInfo.contactId}"  style="display:none;" value="${usInfo.unauthorizedAccessCount}"/>
					<%-- <c:choose>
   					 <c:when test="${usInfo.sexOffender.equals('Yes')}">
   					 <p style="font-size: 11px; background-color: red;">Sex Offender:${usInfo.sexOffender }</p>
   					 </c:when>
   					
   					 <c:otherwise>
      					<p style="font-size: 11px; ">Sex Offender:${usInfo.sexOffender }</p>
  					  </c:otherwise>
					</c:choose> --%>
					
					<div style="margin-left: 175px; width: 119px; margin-top: -18px;">Guest count:</p><span id="guestCount" style="margin-left: 33px;"> ${ guestCountCheckIn} </span></div>
				</div>
				<%-- <div style="float:right;padding-top:5px;border:0px solid; width:160px;">
					<span style="font-size:11px;">I am interested in</span>
					<div>&nbsp;</div>
					<div id="team_section" class="scroller" style="font-size:11px">
						<c:forEach var="vactivity" items="${volunteerActivity}" varStatus="count">
							<input type="checkbox" id="volunteerActivity" name="volunteerActivity_${usInfo.contactId}" value="${vactivity.id }" /> ${vactivity.activity } <br />
						</c:forEach>									
					</div>
				</div> --%>
				<div>
				 <span id="checkinMember${usInfo.contactId}" class="k-button" style="text-shadow: none; width: 90px; margin-top: 36px; margin-left: 52px; font-size:10px; " onclick="primaryMbershipCheck('${usInfo.contactId}')">CHECK-IN</span>
				 <span id="allowGuest" class="k-button" style="text-shadow: none; width: 90px; margin-top: 7px; margin-left: 52px; font-size:9px; height: 23px;" onclick="add_Guest()">ALLOW GUEST</span>
				
				</div>
			</div>
			<span class="profilepic" style="background: url('<%=contextPath %>/${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
		<!-- 	<img src="A:/YMCA-06-04-15/ymca/web/src/main/webapp/resources/img/portal_Images/defaultpic.jpg" alt="" ></img> -->
			
			<%-- <div>
				<div class="moreinfo">
					Family Contact and Program <br />Information
					<span class="viewmore" onclick="view_member('P','${usInfo.contactId}')" style="margin-left : 95px;">View</span>
					<span style="">
			 			<a id="addmemberLink" href="#" style="float:right;" onclick="add_member();">Add Family Contact</a>
			 		</span>
				</div>				
		 	</div> --%>
		 	 
		</div>
	</div>
</div>
<c:if test="${ userCount >= 1}">
<div  id="member_profile_2">
	<div id="tabstrip1" class="k-block">
		<ul>
			<li class="k-state-active">
				<span class="head">ADDITIONAL MEMBER(S)</span>
			</li>
		</ul>
		 <div>
		 	<span style="float: right; width: 100%; right: 0px; top: -30px; position: relative;">
		 		<!-- <a id="addmemberLink" href="#" style="float:right;" onclick="add_member();">Add Member</a> -->
		 	</span>
		 	<c:forEach var="member" items="${userS}" varStatus="count">
			  	<div id="team">
			<%--  <span id="checkinMember" class="k-button" onclick="membercheckin(''${member.contactId}'')" style="text-shadow: none; width: 110px; margin-top: 114px; margin-left: 35px;">CHECK-IN</span> --%>
			  <span id="checkinMember${member.contactId}" class="k-button" style="text-shadow: none; width: 90px; margin-top: 55px; margin-left: 55px; color: rgb(255, 255, 255); font-weight: bold; background-color: rgb(235, 129, 32);" onclick="membercheckin('${member.contactId}')">CHECK-IN</span>
			  	<span id="chkdmem${member.contactId}" style="font-size: 15px; border: 0px none; background-color: green;display:none;">Member Checked</span>
					<div id="team_info" style="padding-top: 50px; float: left; width: 116px; margin-left: 4px;">
						<span class="name">${member.personFirstName } ${member.personLastName }</span><br>
						<br>
					<span >Age:  ${member.age}       </span>
					<p id="unAuthAccCountOld${member.contactId}" style="font-size: 11px;">Unauthorized Access Count:${member.unauthorizedAccessCount}</p>
					<input type="text" id="unAuthAccCountHidden${member.contactId}"  style="display:none;" value="${member.unauthorizedAccessCount}"/>
					<input type="text" id="sexOffenderRed${member.contactId}"  style="display:none;" value="${member.sexOffender }"/>
					<%-- <c:choose>
   					 <c:when test="${member.sexOffender.equals('Yes')}">
   					 <p style="font-size: 11px; background-color: red;">Sex Offender:${member.sexOffender }</p>
   					 </c:when>
   					
   					 <c:otherwise>
      					<span style="font-size: 11px;">Sex Offender :${member.sexOffender }</span>
  					  </c:otherwise>
					</c:choose> --%>
						
					</div>
					</div>
					<%-- <span class="profilepic" style="background: url('${.profileImage}'); transparent no-repeat 0 0;height: 113px;">&nbsp;</span> --%>
					<span class="profilepic" style="background: url('<%=contextPath %>/${member.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
				<c:if test="${!count.last}"><hr></c:if>
			</c:forEach>
			
		
		</div>
	</div>
</div>
</c:if>
<script id="NoActiveMembership" type="text/x-kendo-template">
    <p class="delete-message">You have No Active Membership.</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">OK</button>&nbsp;&nbsp;&nbsp;
   </div>
</script>
<script  type="text/javascript">
/* $("#tabstrip1").kendoTabStrip({
	animation : {
		open : {
			effects : "fadeIn"
		}
	}
});
$("#tabstrip2").kendoTabStrip({
	animation : {
		open : {
			effects : "fadeIn"
		}
	}
});

$('.scroller').slimScroll({
	height : '110px',
	width : '160px',
	alwaysVisible : true,
	color : '#666666',
	distance : '4px',
	railVisible : true,
	allowPageScroll : true
}).parent().css({
	border : '1px solid #cccccc'
}); */



/* function membercheckin(memberContactId){
	alert("Membershipid contact id");
	alert(memberContactId);
	//$("#m_id").val(12);
	if (memberContactId != '') {
		
		$.ajax({
			url : "checkIn_member?memberContactId=" + memberContactId,
			success : function(data) {
				 if(data=='SUCCESS'){
					 $("#checkinMember"+memberContactId).hide();
					 $("#chkdmem"+memberContactId).show();
					 
					 
				 }else if(data=="Error"){
					 var kendoWindow = $("<div />").kendoWindow({
							title : "Check",
							resizable : false,
							modal : true,
							width : 400
						});

						kendoWindow.data("kendoWindow").content(
								$("#NoActiveMembership").html()).center().open();

						kendoWindow.find(".delete-confirm").click(
								function() {
									if ($(this).hasClass("delete-confirm")) {
										kendoWindow.data("kendoWindow").close();
									}

									
								}).end()

				 }
				 
				
			}
		});
	}
}


function calculateage(dob){
	alert(dob);
	   var today = new Date();
	    var birthDate = new Date(dob);
	     var age = today.getFullYear() - birthDate.getFullYear();
	     alert(age);
	    var m = today.getMonth() - birthDate.getMonth();
	   if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
	        age--;
	    }
	    alert(age);
	   // return age; 
}
 */


</script>