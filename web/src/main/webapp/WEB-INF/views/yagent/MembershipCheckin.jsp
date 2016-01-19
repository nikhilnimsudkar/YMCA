a<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    <link rel="stylesheet" href="<%=contextPath %>/resources/css/jquery-ui.css" type="text/css" media="all" />  
<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />


 <script src="<%=contextPath %>/resources/js/jquery.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery-ui.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>

<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.html5storage.min.js"></script>
</head>
<body>

<div id="main">
<%@ include file="../../layouts/include_taglib.jsp"%>
	<div class="k-loading-mask"
			style="width: 100%; height: 100%; position: absolute; top: 114px; left: 0px; display: none; z-index: 9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
	<div id="content">
		<div id="myprofile">
			<%@ include file="MembershipCheckinAccessleft.jsp" %> 
		</div>
		
		<div id="programs">
			<%@ include file="MembershipCheckinAccessRight.jsp" %> 
		</div>
		
		
		
	</div>
</div>



</body>