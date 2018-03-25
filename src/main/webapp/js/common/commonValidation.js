/**
 * 手机验证
 * 
 * @param {}
 *            num
 * @return {Boolean}
 */
var mobileNumberCheck = function(num) {
	if (/^1[3|5|8][0-9]\d{4,8}$/.test(num)) {
		return true;
	} else {
		return false;
	}
}
var isNotNull = function(value) {
	  if (value==null||value==undefined||value==""){
		  return false;
	  }
	  return true;
}

/**
 * 数字验证
 * 
 * @param {}
 *            num
 * @return {Boolean}
 */
var numberCheck = function(num) {
	if (/^[0-9]*$/.test(num)) {
		return true;
	} else {
		return false;
	}
}

var doubleNumCheck=function (num){
	if (/^[0-9]+\.[0-9]{1,2}$/.test(num)) {
		return true;
	} else {
		return false;
	}
}
/**
 * EMail地址验证
 * 
 * @param {}
 *            address
 * @return {Boolean}
 */
var emailCheck = function(address) {
	if (/^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/.test(address)) {
		return true;
	} else {
		return false;
	}
}

// 月份比较
var datamonthcompare = function(begindata, enddata) {
	// begindata,enddata 日期格式 2013-01-01 ；
	var aDate, oDate1, oDate2;
	aDate = begindata.split("-");
	oDate1 = new Date(aDate[0], aDate[1] - 1, '01');
	aDate = enddata.split("-");
	oDate2 = new Date(aDate[0], aDate[1] - 1, '01');
	if(oDate1.getTime()>oDate2.getTime()){
		return false;
	}

	return true;

}

// 日期比较
var datacompare = function(begindata, enddata) {
	// begindata,enddata 日期格式 2013-01-01 ；
	var aDate, oDate1, oDate2;
	aDate = begindata.split("-");
	oDate1 = new Date(aDate[0], aDate[1] - 1, aDate[2]);
	aDate = enddata.split("-");
	oDate2 = new Date(aDate[0], aDate[1] - 1, aDate[2]);
	if(oDate1.getTime()>oDate2.getTime()){
	  return false;
	}
    
	return true;

}
// 带时间的日期比较
var timecompare = function(begindata, enddata) {
	// begindata,enddata 日期格式 2013-01-01 12:20:01 ；
	var aDate, oDate1, oDate2;
	begindata= $.trim(begindata);
	enddata= $.trim(enddata);
	begindata = begindata.replace(' ', "-");
	begindata = begindata.replace('  ', "-");
	begindata = begindata.replace(/:/g, "-");
	enddata = enddata.replace(' ', "-");
	enddata = enddata.replace(/:/g, "-");
	enddata = enddata.replace('  ', "-");  
	aDate = begindata.split("-");
	oDate1 = new Date(aDate[0], aDate[1] - 1, aDate[2], aDate[3], aDate[4],
			aDate[5]);
	aDate = enddata.split("-");
	oDate2 = new Date(aDate[0], aDate[1] - 1, aDate[2], aDate[3], aDate[4],
			aDate[5]);
	if (oDate1.getTime() > oDate2.getTime()) {
		return false;
	} else {
		return true;
	}

}

/**
 * 验证是否为中中，为中文返回false，否则true
 * 
 * @param {}
 *            str
 * @return {Boolean}
 */
var checkIsChinese = function(str) {
	var re = /[^\u4e00-\u9fa5]/;
	return re.test(str);
}
