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
				<div id="team_info" style="float:left; width:100px; padding-top:50px;">
					<span class="name">${usInfo.personFirstName } ${usInfo.personLastName }</span>
				</div>
				<div style="float:right;padding-top:5px;border:0px solid; width:160px;">
					<span style="font-size:11px;">I am interested in</span>
					<div>&nbsp;</div>
					<div id="team_section" class="scroller" style="font-size:11px">
						<c:forEach var="vactivity" items="${volunteerActivity}" varStatus="count">
							<input type="checkbox" id="volunteerActivity" name="volunteerActivity_${usInfo.contactId}" value="${vactivity.id }" /> ${vactivity.activity } <br />
						</c:forEach>									
					</div>
				</div>
			</div>
			<span class="profilepic" style="background: url('${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
			<div>
				<div class="moreinfo">
					Family Contact and Program <br />Information
					<span class="viewmore" onclick="view_member('P','${usInfo.contactId}')" style="margin-left : 95px;">View</span>
					<span style="">
			 			<a id="addmemberLink" href="#" style="float:right;" onclick="add_member();">Add Family Contact</a>
			 		</span>
				</div>				
		 	</div>
		</div>
	</div>
</div>

<c:if test="${ userCount >= 1}">
<div id="member_profile_2">
	<div id="tabstrip1" class="k-block">
		<ul>
			<li class="k-state-active">
				<span class="head">ADDITIONAL FAMILY CONTACT(S)</span>
			</li>
		</ul>
		 <div>
		 	
		 	<c:forEach var="member" items="${userS}" varStatus="count">
			  	<div id="team">
					<div id="team_info" style="float:left; width:100px; padding-top:50px;">
						<span class="name">${member.personFirstName } ${member.personLastName }</span>
					</div>
					<div style="float:right;padding-top:5px;border:0px solid; width:160px;">
						<span style="font-size:11px;">I am interested in</span>
						<div>&nbsp;</div>
						<div id="team_section${count.count}" class="scroller" style="font-size:11px;">
							<c:forEach var="vactivity" items="${volunteerActivity}" varStatus="count">
								<input type="checkbox" name="volunteerActivity_${member.contactId}" value="${vactivity.activity }" /> ${vactivity.activity } <br />
							</c:forEach>	
							<!-- 
							<input type="checkbox" /> Youth Sports <br />
							<input type="checkbox" /> Youth Learning <br />
							<input type="checkbox" /> Child Care <br />
							<input type="checkbox" /> Swim Class <br />
							<input type="checkbox" /> Abs Class <br />
							<input type="checkbox" /> Theme Park <br />
							<input type="checkbox" /> Riding Class <br />
							<input type="checkbox" /> Dancing <br />	 -->	
						</div>
					</div>
				</div>
				<span class="profilepic" style="background: url('${member.profileImage}'); transparent no-repeat 0 0;">&nbsp;</span>
				<div class="moreinfoenrolled">
				<span class="viewmore" onclick="enrolled_programs('${member.contactId}')">Enrolled Programs</span>&nbsp;&nbsp;&nbsp;
					<span class="viewmore" onclick="view_member('A','${member.contactId}', '${member.isKid}')">View</span>
					<span style="display:none;" class="contactSignupId" >${member.signupId}</span>
					<span style="display:none;" class="isContactKid" >${member.isKid}</span>
					<span style="display:none;" class="isContactAdult" >${member.isAdult}</span>
					<span style="display:none;" class="contactIdSpan" >${member.contactId}</span>					
					<span id="removemember" class="viewmore removeMemberCls" style="padding-left: 20px;" >Remove</span>
					<%-- onclick="remove_member('${member.contactId}')" --%>
				</div>
				<c:if test="${!count.last}"><hr></c:if>
			</c:forEach>
			
			<!-- 
			<div id="team">
				<div id="team_info" style="float:left; width:100px; padding-top:50px;">
					<span class="name">Peter Winslet</span>
				</div>
				<div style="float:right;padding-top:5px;border:0px solid; width:160px;">
					<span style="font-size:11px;">I am interested in</span>
					<div>&nbsp;</div>
					<div id="team_section1" class="scroller">
						<input type="checkbox" /> Youth Sports <br />
						<input type="checkbox" /> Youth Learning <br />
						<input type="checkbox" /> Child Care <br />
						<input type="checkbox" /> Swim Class <br />
						<input type="checkbox" /> Abs Class <br />
						<input type="checkbox" /> Theme Park <br />
						<input type="checkbox" /> Riding Class <br />
						<input type="checkbox" /> Dancing <br />		
					</div>
				</div>
			</div>
			<span class="profilepic" style="background: url('resources/img/portal_Images/aboutus1.jpg'); ">&nbsp;</span>
			<div class="moreinfoenrolled">Enrolled Programs&nbsp;&nbsp;&nbsp;<span class="viewmore">View</span></div>
			<hr>
			<div id="team">
				<div id="team_info" style="float:left; width:100px; padding-top:50px;">
					<span class="name">Jessica Thompson</span>
				</div>
				<div style="float:right;padding-top:5px;border:0px solid; width:160px;">
					<span style="font-size:11px;">I am interested in</span>
					<div>&nbsp;</div>
					<div id="team_section2" class="scroller">
						<input type="checkbox" /> Youth Sports <br />
						<input type="checkbox" /> Youth Learning <br />
						<input type="checkbox" /> Child Care <br />
						<input type="checkbox" /> Swim Class <br />
						<input type="checkbox" /> Abs Class <br />
						<input type="checkbox" /> Theme Park <br />
						<input type="checkbox" /> Riding Class <br />
						<input type="checkbox" /> Dancing <br />		
					</div>
				</div>
			</div>
			<span class="profilepic" style="background: url('resources/img/portal_Images/contactus.jpg'); ">&nbsp;</span>
			<div class="moreinfoenrolled">Enrolled Programs&nbsp;&nbsp;&nbsp;<span class="viewmore">View</span></div>
			 -->
		</div>
	</div>
</div>
</c:if>