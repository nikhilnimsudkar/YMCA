<%@ include file="../../layouts/include_taglib.jsp" %>
        	
<div id="formBlock">
	<form:form commandName="ItemDetailsSession" name="signupFrm" id="signupFrm" action="signupProgram" method="post">
	<div id="cc_search">
		<span class="head">Search</span>
   			
		<div style="margin:10px;">
   			<div class="srchtext"><span>Location (School Name):</span></div>
			<select name="location" id="location" style="width:200px;">
				<c:forEach var="location" items="${locations}" varStatus="count">
					<c:choose>
						<c:when test="${ location.recordName == 'All Branches' }">
							<option value="${location.id }" selected="selected">${location.recordName }</option>
						</c:when>
						<c:otherwise>
					    </c:otherwise>
					</c:choose>
				</c:forEach>
				<c:forEach var="location" items="${locations}" varStatus="count">
					<c:choose>
						<c:when test="${ location.recordName == 'All Branches' }">
						</c:when>
						<c:otherwise>
					      	<option value="${location.id }">${location.recordName }</option>
					    </c:otherwise>
				    </c:choose>
				</c:forEach>
			</select>
		</div>
		<div style="margin:10px; height: 30px;">
		   		<div class="srchtext"><span>Age range:</span></div>
		   		<div id="agerangeslider" class="searchFields" style=" margin: 5px 0 0 7px; width: 170px;">
				<span><input class="k-textbox" type="text" id="min_age" style="width:90px;"></span>
				<span><input class="k-textbox" type="text" id="max_age" style="width:90px;"></span>
			</div>
		</div>
<!-- 
		<div style="margin:10px;">
		   				<div class="srchtext"><span>Type:</span></div>
			<select name="type" id="type" style="width:200px;">
				<option value="Morning Care">Morning Care</option>
				<option value="Afternoon Care">Afternoon Care</option>
				<option value="Kinder Care">Kinder Care</option>
				<option value="Pre School">Pre School</option>
			</select>
		</div> 
-->
		<div style="margin:10px;">
		   	<div class="srchtext"><span>Keyword:</span></div>
			<input id="keyword" class="k-textbox" type="text" style="width:200px;">
		</div>
		<div style="margin:10px;">
		   	<span class="k-button" id="SearchBtn">Go >></span>
		</div>
   	</div>	
   	</form:form>
</div>

<style>
.k-grid  .k-grid-header  .k-header  .k-link {
    height: auto;
}
  
.k-grid  .k-grid-header  .k-header {
    white-space: normal;
}
</style>