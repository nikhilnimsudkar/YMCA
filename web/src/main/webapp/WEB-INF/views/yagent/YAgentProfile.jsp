
<%@ include file="../../layouts/include_taglib.jsp"%>


<div>
	</br> <span class="head"
		style="width: 100px; padding-top: 50px; color: #eb8120; font-weight: bold;">WELCOME
		${param.agentUid}</span>
</div>

<div id="tabstrip5" class="k-block" style="border-color: #fcaf17;">
	<span class="head" width="100%" style="padding-left: 3px;">AGENT PROFILE</span></br>
	<div id="team1">
		<span class="head" style="padding-left: 5px;">AGENT NAME</span>
	</div>
	<%-- <span class="profilepic" style="background: url('<%=contextPath %>/${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span> --%>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br> </span>
</div>

<div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: 1px; width: 344px;">
<span class="head" style="margin-left:4px;" width="100%">MEMBERSHIP CHECK-IN :</span>
<br>
<span id="checkMemship" class="k-button" style="width: 100px; text-shadow: none; margin-left: 105px; margin-top: 13px;" onclick="location.href='yAgentSearchUser'">CHECK-IN</span>
</div>
<div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255);margin-left: 1px; width: 344px;">
<span class="head" style="margin-left:4px;" width="100%">KIDS CLUB CHECK-IN :</span>
<br>
<span id="checkMemship" class="k-button" style="width: 100px; text-shadow: none; margin-left: 105px; margin-top: 13px;" onclick="location.href='yAgentSearchKidsNcheckin'">CHECK-IN</span>
</div>
<div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: 1px; width: 344px;">
<span class="head" style="margin-left:4px;" width="100%">VIEW CURRENTLY CHECKED-IN KIDS :</span>
<br>
<span id="viewChkdInKids" class="k-button" style="margin-left: 70px; margin-top: 13px; width: 195px; margin-top: 13px;" onclick="location.href='kidsPagination'">VIEW CHECKED-IN KIDS</span>
</div>
</div>





