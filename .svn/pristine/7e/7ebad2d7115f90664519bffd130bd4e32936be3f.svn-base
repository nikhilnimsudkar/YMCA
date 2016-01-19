<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main">
	
	<div id="content">
		<div id="member">
			<%@ include file="../views/customer/member.jsp" %> 
		</div>
		
		<div id="profile">
			<%@ include file="../views/customer/Form.jsp" %> 
		</div>
		
		<div id="update">
			<%@ include file="../views/customer/RightSection.jsp" %>
		</div>
		
	</div>
	
</div>

<script>
	$(document).ready(function() {
		$("#tabstrip").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			}
		});
		
		$("#tabstrip1").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			}
		});
		$("#tabstrip2").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			}
		});
		
		
		$('.scroller').slimScroll({
			height: '110px',
			width: '160px',
			alwaysVisible: true,
			color: '#666666',
			distance: '4px',
			railVisible: true,
			allowPageScroll: true
		}).parent().css({
		  border: '1px solid #cccccc'
		});
		
	});
</script>

</body>