<%@ include file="../../layouts/include_taglib.jsp"%>

<% try{ %>

<div  class="k-block" style="border-color:#fcaf17; padding-left: 10px; padding-top: 15px; ">
	<div style="display: table;  width: 100%">
		<div style="display: table-row;  width: 100%">
			<span class="head" style="margin-left:3px;">MY PROFILE</span>
		</div>
		
		<div style="display: table-row; width: 100%">
			<div style="float: left;">
				<span class="profilepic" style="background: url('${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
			</div>
			<div style="float: left; padding-left: 30px; padding-top: 5px; min-width: 200px;">
				<span class="pname" style="font-weight: bold;">${usInfo.personFirstName } ${usInfo.personLastName }</span>
				<span style="font-weight: bold;"><c:if test="${ userCount >= 1}"><br>
					<c:forEach var="member" items="${userS}" varStatus="count">
						<br><span class="name">${member.personFirstName } ${member.personLastName }</span>
						</c:forEach>
					</c:if>
				</span>
			</div>
		</div> 
	</div>
</div>
	
<% 
	}catch(Exception e){
		e.printStackTrace();
	}
%>
