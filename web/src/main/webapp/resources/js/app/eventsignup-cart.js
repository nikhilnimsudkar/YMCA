var signup = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
		read:  {
		  async: false,
		  url: location.protocol+"//"+location.host+"/ymca-web/getSignupByType?type=EVENT",
		  dataType: "json" // "jsonp" is required for cross-domain requests; use "json" for same-domain requests
		}
		
    }
});