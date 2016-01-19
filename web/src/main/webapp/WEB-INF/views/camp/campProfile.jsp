<%@ include file="../../layouts/include_taglib.jsp"%>

<div id="tabstrip5" class="k-block" style="border-color:#fcaf17;">
	<span class="head" width="100%" style="margin-left:3px;">MY PROFILE</span>
	<div id="team1">
		<div id="team_info" style="float: left; padding: 20px;">
			<span class="pname">${usInfo.firstName } ${usInfo.lastName }</span>
			<c:if test="${ userCount >= 1}"><br>
				<c:forEach var="member" items="${userS}" varStatus="count">
					<br><span class="name">${member.firstName } ${member.lastName }</span>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<span class="profilepic" style="background: url('${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
</div>