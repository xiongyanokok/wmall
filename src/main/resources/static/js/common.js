//全局的ajax访问，处理ajax清求时session超时
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		// 通过XMLHttpRequest取得响应头，sessionstatus，
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		if (sessionstatus == "timeout") {
			// 如果超时就处理 ，指定要跳转的页面(比如登陆页)
			top.location.replace("/");
		}
	}
});

// 只能输入纯数字
function pureNumber(obj) {
	obj.value = obj.value.replace(/[^\d]/g, "");
}

// 只能输入数字和一位小数点
function numberPoint(obj) {
	//清除“数字”和“.”以外的字符
	obj.value = obj.value.replace(/[^\d.]/g, "");
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	//只能输入一位小数  
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3');
	//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
	if (obj.value.indexOf(".") < 0 && obj.value != "") {
		obj.value = parseFloat(obj.value);
	} 
}

// datatable分页
var page_size = 10;
var height = $(window).height();
if (height > 700) {
	page_size = 15;
}