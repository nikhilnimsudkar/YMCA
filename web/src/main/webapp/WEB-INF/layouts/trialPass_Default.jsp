<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@include file="include_taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@include file="include_taglib.jsp"%>
<title></title>
</head>
<body class="tundra spring">
	<div id="wrapper">
		<tiles:insertAttribute name="header" ignore="true" />
		<div id="main" style="clear: both;">
			<tiles:insertAttribute name="body" />
		</div>
		<tiles:insertAttribute name="footer" ignore="true" />
	</div>
</body>
</html>