
<%@ include file="../../layouts/include_taglib.jsp"%>


<div>
</br>
					<span class="head" style="width:100px;padding-top:50px;color:#eb8120;font-weight:bold;">WELCOME ${usInfo.username}!</span>
					
					</div>
	
	<div id="tabstrip5" class="k-block" style="border-color:#fcaf17;">
		
				<span class="head" width="100%" style="padding-left:3px;">MY PROFILE</span></br>
		
			<div id="team1">
				
			
					<span class="head" style="padding-left:5px;">PROFILE INFORMATION</span>
					</br><span style="font-size: .7em;padding-left:5px;">Manage/Add Contacts & Communications</span></br>
					<span class="k-button" id="updProfile" onclick="location.href='view_membership'" style="margin-left:5px;">VIEW/EDIT</span>	</br>	</br>				
					
					<%-- <c:choose>
      <c:when test="${memberShip==null}">
      </br></br>
	 	<span class="k-button" id="updProfile" onclick="location.href='becomeMember'"style="margin-left:5px;">Become a Member</span></br></br>
      </c:when>

      <c:otherwise> <span class="head" style="width:100px; padding-top:5px;padding-left:5px;">CURRENT MEMBERSHIPS</span></br>	
					<span style="font-size: .7em;padding-left:5px;">View details of your current Memberships</span></br>	
					<span class="k-button" id="updProfile" onclick="location.href='currentmembership'"style="margin-left:5px;">VIEW/EDIT</span></br></br>
      </c:otherwise>
</c:choose> --%>
<c:choose>
	<c:when test="${signup != null }">
		<span style="font-size: .7em;padding-left:5px;">View details of your current Memberships</span></br>	
		<span class="k-button" id="updProfile" onclick="location.href='currentmembership'"style="margin-left:5px;">VIEW/EDIT</span></br></br>
	</c:when>
	<c:otherwise>
		<span id="regNow" onclick="location.href='becomeMember'" class="k-button" style="margin-top:20px; width: 165px; text-shadow: none;">Become a Member</span>
	</c:otherwise>
</c:choose>
			
				</div>
			
	<span class="profilepic" style="background: url('${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
			</br></br></br></br></br></br>
</span>
		</div>

	




