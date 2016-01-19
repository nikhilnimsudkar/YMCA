
/* Common functions */

function convertJsonDate(dtObj){
	try {
		var dtStr = "";
		var sday = ("0" + (dtObj.date)).slice(-2);
		var smonth = ("0" + (dtObj.month + 1)).slice(-2);
		var syear = dtObj.year;
		var strYear = syear.toString();
		if(strYear.length==3)
			strYear = "20"+strYear.substring(1,strYear.length);
		else
			strYear = syear;
		dtStr = smonth + "/" + sday + "/" + strYear;
		return dtStr;
	} catch(e) {
		return "";
	}
}

function convertTimeToDate(dtObj){
	try {
		var date1 = new Date(dtObj);
		var dateStr =  date1.toString("MM/dd/yyyy");   
		//alert(dateStr);
		return dateStr ;
	} catch(e) {
		return "";
	}
}



function currencyFormat (num) {
    return "$" + num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
}

function formatTime(date) {
	var UTCtime = date.getTime();
	//console.log(UTCtime);
	var now = new Date();
	var timezoneOffset = now.getTimezoneOffset();
	//console.log(timezoneOffset);
	var timezoneInMillisecond = timezoneOffset * 60000;
	
	var date = new Date(UTCtime + timezoneInMillisecond);
	//console.log(date);
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return strTime;
}

function formatDate(inputFormat) {
	  function pad(s) { return (s < 10) ? '0' + s : s; }
	  var d = new Date(inputFormat);
	  return [pad(d.getMonth()+1), pad(d.getDate()), pad(d.getFullYear ())].join('/');
}

function isNumber(val){
   if (isNaN(val) || val == 'undefined' || val == null) 
	   return 0;
   else
	   return val;
}

function ArrNoDupe(a) {
    var temp = {};
    for (var i = 0; i < a.length; i++)
        temp[a[i]] = true;
    var r = [];
    for (var k in temp)
        r.push(k);
    return r;
}

function unique(list) {
    var result = [];
    $.each(list, function(i, e) {
        if ($.inArray(e, result) == -1) result.push(e);
    });
    return result;
}

function isValidDate(date) {
    var matches = /^(\d{2})[-\/](\d{2})[-\/](\d{4})$/.exec(date);
    if (matches == null) return false;
    var d = matches[2];
    var m = matches[1] - 1;
    var y = matches[3];
    var composedDate = new Date(y, m, d);
    return composedDate.getDate() == d &&
            composedDate.getMonth() == m &&
            composedDate.getFullYear() == y;
}