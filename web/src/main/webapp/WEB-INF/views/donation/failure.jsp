<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<script type="text/javascript">
$(document).ready(function(){
	var viewName = "Failure";
	var jsonData = {
		view : viewName		
	};
	parent.postMessage(jsonData, '*'); 	
 });  

</script>
<h1>failure</h1>
</html>