<%
	String contextPath = request.getContextPath();
%>
<script src="<%=contextPath %>/resources/js/app/common.js"></script>
<div id="main">
	
	<div id="content">
		<div id="searchprogram" class="k-block">
			<%@ include file="searchallprogram.jsp" %> 
		</div>
		
		<div id="program" style="width:68%">
			<%@ include file="programattendance.jsp" %> 
		</div>
		
		
		
	</div>
</div>