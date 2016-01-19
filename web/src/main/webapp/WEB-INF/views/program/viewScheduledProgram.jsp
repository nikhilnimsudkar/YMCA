<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    <script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
<body>

<div id="main">
	
	<div id="content">
		<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
				
		<div id="searchprogram" class="k-block">
			<%@ include file="searchallprogram.jsp" %> 
		</div>
		
		<div id="programs">
			<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF;margin-left:-52px;width:826px;min-height:0px; padding: 5px 5px 25px;">
				<div id="scheduledProgram">
					<%@ include file="scheduledProgram.jsp" %> 
				</div>
			</div>
		
		</div>
		
		<div style="clear: both;"></div> 
		<div id="statusBlock">
			<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
			<span class="k-block k-error-colored" id="tcErrorSpan"></span>
		</div>

	</div>
</div>

</body>

<style>
	table tr td {
	    padding-left: 5px;
	}
</style>