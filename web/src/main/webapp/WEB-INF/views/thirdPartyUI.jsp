<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Third Party UI</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script>
<script src="https://malsup.github.com/jquery.blockUI.js"></script>
<script type="text/javascript"> 

function getParameters() {
    var searchString = window.location.search.substring(1),
      params = searchString.split("&"),
      hash = {};

    if (searchString == "") return {};
    for (var i = 0; i < params.length; i++) {
      var val = params[i].split("=");
      hash[unescape(val[0])] = unescape(val[1]);
    }

    return hash;
  }
  
$(document).ready(function() { 

    	 $.blockUI({ message: "<h1>Invoice generation in progress...</h1>" }); 
         
    	 var param = getParameters();
    	 
         var signUpId = param.signUpId;
         var payerId = param.payerId;
         $.ajax({
			  type: "GET",
			  url:"thirdPartyResult?signUpId="+signUpId+"&payerId="+payerId,
			  success: function( data ) {
				  data_s = data.split("__");
					 if(data_s[0]=="SUCCESS"){
						 invoiceId = data_s[1];
						 $.blockUI({ message: "<h1>Invoice generated with ID:" + invoiceId +  "Please close the window or tab. </h1>"});  					   
				  	  }else if(data_s[0]=="EXISTS"){
				  		 $.blockUI({ message: "<h1>Invoice already exists. Please close the window or tab.</h1> " });
				  	  }else if(data_s[0]=="INSUFFICIENT"){
				  		 $.blockUI({ message: "<h1>Insufficient Data. Either the payer or signup does not exist. Please close the window or tab.</h1> " });
				  	  }else{
				  		 $.blockUI({ message: "<h1>Invoice generation FAILED. Please close the window or tab.</h1> " });
				  	  }			  	 
			  	}
		});     
       
    }); 
</script> 
</head>
<body>

</body>
</html>