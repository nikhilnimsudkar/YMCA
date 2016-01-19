<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<input type="hidden" value="${reloadUrl}" id="reloadUrl" />
<script type="text/javascript">
$(document).ready(function() { 
        $.blockUI({ css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        } });
        //setTimeout($.unblockUI, 2000);
        var reloadUrl = "../home";
        if ($("#reloadUrl").val() != '') {
        	reloadUrl = $("#reloadUrl").val();
        }
        window.location.href =reloadUrl;
});
</script>