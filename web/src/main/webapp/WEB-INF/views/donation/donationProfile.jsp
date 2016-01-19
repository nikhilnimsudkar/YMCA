
<%@ include file="../../layouts/include_taglib.jsp"%>

<div id="tabstrip5" class="k-block" style="width: 255px;">
	<span class="head" width="100%" style="margin-left: 3px;">MY PROFILE</span></br>
	<div id="team">
		<span class="head" style="margin-left: 30px;">${usInfo.firstName} ${usInfo.lastName }</span>
	</div>
	<span class="profilepic" style="background: url('<%=contextPath %>/${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
</div>
